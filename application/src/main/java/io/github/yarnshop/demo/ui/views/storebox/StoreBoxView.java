package io.github.yarnshop.demo.ui.views.storebox;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.dao.DataIntegrityViolationException;
import io.github.yarnshop.demo.backend.entity.BoxInStore;
import io.github.yarnshop.demo.backend.service.StoreBoxService;
import io.github.yarnshop.demo.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "store-box", layout = MainLayout.class)
@PageTitle("Store Box | Yarn Shop")
public class StoreBoxView extends VerticalLayout {

    StoreBoxForm form;
    Grid<BoxInStore> grid = new Grid<>(BoxInStore.class);
    TextField filterText = new TextField();

    StoreBoxService storeBoxService;

    public StoreBoxView(StoreBoxService storeBoxService) {
        this.storeBoxService = storeBoxService;
        addClassName("type-view");
        setSizeFull();
        configureGrid();

        form = new StoreBoxForm(storeBoxService.findAll());
        form.addListener(StoreBoxForm.SaveEvent.class, this::saveContact);
        form.addListener(StoreBoxForm.DeleteEvent.class, this::deleteContact);
        form.addListener(StoreBoxForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        setHeight("80%");
        closeEditor();
    }

    private void deleteContact(StoreBoxForm.DeleteEvent evt) {
        try {
            storeBoxService.delete(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DataIntegrityViolationException dive) {
            Notification notification = new Notification(
                    "The Box is used and cannot be removed.", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void saveContact(StoreBoxForm.SaveEvent evt) {
        try {
            storeBoxService.save(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DuplicateKeyException e) {
            Notification notification = new Notification(
                    "A Box with this name already exist.", 3000,
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
        filterText.setPlaceholder("Filter on name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add a Box", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new BoxInStore());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "description");

        grid.getColumnByKey("name").setHeader("Name");
        grid.getColumnByKey("description").setHeader("Description");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }

    private void editContact(BoxInStore contact) {
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
        grid.setItems(storeBoxService.findAll(filterText.getValue()));
    }

}
