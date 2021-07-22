package io.github.yarnshop.demo.ui.views.yarnlist;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import io.github.yarnshop.demo.backend.entity.BoxInStudio;
import io.github.yarnshop.demo.ui.views.yarnalternative.YarnAlternativeView;
import io.github.yarnshop.demo.ui.views.yarnproduct.YarnProductView;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.entity.BoxInStore;
import io.github.yarnshop.demo.backend.entity.Yarn;
import io.github.yarnshop.demo.backend.service.StoreBoxService;
import io.github.yarnshop.demo.backend.service.StudioBoxService;
import io.github.yarnshop.demo.backend.service.YarnService;
import io.github.yarnshop.demo.ui.MainLayout;

import java.util.logging.Logger;

@Component
@Scope("prototype")
@Route(value = "yarn", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Yarn List | Yarn Shop")
public class YarnView extends VerticalLayout implements HasUrlParameter<String> {

    private static final Logger LOGGER = Logger.getLogger(YarnView.class.getName());

    YarnForm form;
    Grid<Yarn> grid = new Grid<>(Yarn.class);
    TextField filterText = new TextField();

    YarnService yarnService;
    StoreBoxService storeBoxService;
    StudioBoxService studioBoxService;

    public YarnView(YarnService yarnService,
                    StoreBoxService storeBoxService,
                    StudioBoxService studioBoxService) {
        this.yarnService = yarnService;
        this.storeBoxService = storeBoxService;
        this.studioBoxService = studioBoxService;

        addClassName("yarn-view");
        setSizeFull();
        configureGrid();

        form = new YarnForm(storeBoxService.findAll(), studioBoxService.findAll());
        form.addListener(YarnForm.SaveEvent.class, this::saveContact);
        form.addListener(YarnForm.DeleteEvent.class, this::deleteContact);
        form.addListener(YarnForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        setHeight("60%");
        closeEditor();
    }

    private void deleteContact(YarnForm.DeleteEvent evt) {
        try {
            yarnService.delete(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DataIntegrityViolationException dive) {
            Notification notification = new Notification(
                    "The Yarn is used in one ore more Products or has alternative Yarns defined, \n " +
                            "and cannot be removed.", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void saveContact(YarnForm.SaveEvent evt) {
        try {
            yarnService.save(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DuplicateKeyException e) {
            Notification notification = new Notification(
                    "A Yarn with this number already exist.", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
        catch (Exception e) {
            Notification notification = new Notification(
                    "Problems storing in the database. Reason: " + e.getMessage(), 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private HorizontalLayout getToolBar() {
        if (filterText.getValue().equals("")) {
            filterText.setPlaceholder("Filter on Yarn no");
        }
        else {
            updateList();
        }
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Yarn", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Yarn());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();

        grid.removeColumnByKey("boxInStore");
        grid.removeColumnByKey("boxInStudio");
        grid.removeColumnByKey("colorCodeString");

        grid.setColumns("yarnNo", "numberTotal", "numberInStudio", "numberInStore");

        grid.getColumnByKey("yarnNo").setHeader("Yarn no");
        grid.getColumnByKey("numberTotal").setHeader("Totalt number");
        grid.getColumnByKey("numberInStudio").setHeader("Number in Studio");
        grid.getColumnByKey("numberInStore").setHeader("Number in Store");

        grid.addColumn(contact -> {
            BoxInStudio boxInStudio = contact.getBoxInStudio();
            return (boxInStudio == null ) ? "-" : boxInStudio.getName();
        }).setHeader("Box in Studio").setSortable(true);

        grid.addColumn(contact -> {
            BoxInStore boxInStore = contact.getBoxInStore();
            return (boxInStore == null) ? "-" : boxInStore.getName();
        }).setHeader("Box in Store").setSortable(true);

        grid.addComponentColumn(contact -> {
            Button button = new Button("->");
            button.addThemeVariants(ButtonVariant.LUMO_ICON);
            String yarnNumber = contact.getYarnNoString();
            button.addClickListener(item -> UI.getCurrent().navigate(YarnAlternativeView.class,
                yarnNumber));
            return button;
        }).setHeader("Alt.").setSortable(false);

        grid.addComponentColumn(contact -> {
            if (contact.getColorCodeString() != null &&
                    (contact.getColorCodeString().length() == 3 || contact.getColorCodeString().length() == 6)) {
                Icon icon = new Icon(VaadinIcon.CIRCLE);
                // Color code in RGB values
                StringBuffer buf = new StringBuffer();
                buf.append("#");
                buf.append(contact.getColorCodeString());
                icon.setColor(buf.toString());
                return icon;
            }
            else {
                Icon icon = new Icon(VaadinIcon.CIRCLE);
                // Don't show in GUI
                icon.setVisible(false);
                return icon;
            }
        }).setHeader("Color").setSortable(false);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

        // Go to YarnAlternative view with same Yarn number when double-clicked
        grid.addItemDoubleClickListener(item -> UI.getCurrent().navigate(YarnProductView.class,
                item.getItem().getYarnNoString()));
    }

    private void editContact(Yarn contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing-type");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing-type");
    }

    private void updateList() {
        grid.setItems(yarnService.findAll(filterText.getValue()));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        if (s != null && s.length() > 0) {
            filterText.setValue(s);
        }
    }
}
