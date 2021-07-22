package io.github.yarnshop.demo.ui.views.yarnalternative;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import io.github.yarnshop.demo.backend.entity.Supplier;
import io.github.yarnshop.demo.backend.entity.Yarn;
import io.github.yarnshop.demo.backend.entity.YarnAlternative;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.service.SupplierService;
import io.github.yarnshop.demo.backend.service.YarnService;
import io.github.yarnshop.demo.ui.MainLayout;
import io.github.yarnshop.demo.ui.views.yarnlist.YarnView;

@Component
@Scope("prototype")
@Route(value = "yarn-alternative", layout = MainLayout.class)
@PageTitle("Yarn-Alternative List | Yarn Shop")
public class YarnAlternativeView extends VerticalLayout implements HasUrlParameter<String> {

    YarnAlternativeForm form;
    Grid<YarnAlternative> grid = new Grid<>(YarnAlternative.class);
    TextField filterText = new TextField();

    SupplierService supplierService;
    YarnService yarnService;

    public YarnAlternativeView(SupplierService supplierService, YarnService yarnService) {
        this.supplierService = supplierService;
        this.yarnService = yarnService;

        addClassName("person-view");
        setSizeFull();
        configureGrid();

        form = new YarnAlternativeForm(supplierService.findAll(), yarnService.findAll());
        form.addListener(YarnAlternativeForm.SaveEvent.class, this::saveContact);
        form.addListener(YarnAlternativeForm.DeleteEvent.class, this::deleteContact);
        form.addListener(YarnAlternativeForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        setHeight("70%");
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();

        grid.removeAllColumns();

        grid.addColumn(contact -> {
            Yarn type = contact.getYarn();
            return (type == null) ? "-" : type.getYarnNo();
        }).setHeader("Yarn id").setSortable(true).setKey("yarn").setWidth("100px").setFlexGrow(0);

        grid.addColumn(contact -> {
            Supplier type = contact.getSupplier();
            return (type == null) ? "-" : type.getName();
        }).setHeader("Supplier").setSortable(true).setKey("supplier").setWidth("220px").setFlexGrow(1);

        grid.addColumn(contact -> contact.getAltYarnId()).setHeader("Yarn id (supplier)").
                setSortable(true).setKey("altYarnId").setWidth("180px").setFlexGrow(0);

        grid.addColumn(contact -> contact.getDescription()).setHeader("Comment").
                setSortable(true).setKey("description").setWidth("500px").setFlexGrow(2);

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

        // Go to Yarn view with same Yarn number when double-clicked
        grid.addItemDoubleClickListener(item -> UI.getCurrent().navigate(YarnView.class,
                item.getItem().getYarn().getYarnNoString()));
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter on Yarn no");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add alternative", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void deleteContact(YarnAlternativeForm.DeleteEvent evt) {
        yarnService.delete(evt.getContact());
        updateList();
    }

    private void addContact() {
        grid.asSingleSelect().clear();

        YarnAlternative yarnAlternative = new YarnAlternative();

        editContact(yarnAlternative);
    }

    private void editContact(YarnAlternative contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing-person");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing-person");
    }

    private void updateList() {
        grid.setItems(yarnService.findAllYarnAlternatives(filterText.getValue()));
    }

    private void saveContact(YarnAlternativeForm.SaveEvent evt) {

        YarnAlternative participator = evt.getContact();

        if (participator.getSupplier() == null) {
            Notification notification = new Notification(
                    "A Supplier must be chosen!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
        else {
            try {
                yarnService.save(evt.getContact());
                updateList();
                closeEditor();
            }
            catch (DuplicateKeyException e) {
                Notification notification = new Notification(
                        "The Supplier is already selected for this Yarn.", 3000,
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
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        if (s != null && s.length() > 0) {
            filterText.setValue(s);
        }
    }
}
