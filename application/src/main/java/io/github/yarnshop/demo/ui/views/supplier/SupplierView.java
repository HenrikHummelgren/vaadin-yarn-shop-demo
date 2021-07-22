package io.github.yarnshop.demo.ui.views.supplier;

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
import io.github.yarnshop.demo.backend.entity.Supplier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.service.SupplierService;
import io.github.yarnshop.demo.ui.MainLayout;

@Component
@Scope("prototype")
@Route(value = "supplier", layout = MainLayout.class)
@PageTitle("Supplier List | Yarn Shop")
public class SupplierView extends VerticalLayout {

    SupplierForm form;
    Grid<Supplier> grid = new Grid<>(Supplier.class);
    TextField filterText = new TextField();

    SupplierService supplierService;

    public SupplierView(SupplierService supplierService) {
        this.supplierService = supplierService;
        addClassName("type-view");
        setSizeFull();
        configureGrid();

        form = new SupplierForm(supplierService.findAll());
        form.addListener(SupplierForm.SaveEvent.class, this::saveContact);
        form.addListener(SupplierForm.DeleteEvent.class, this::deleteContact);
        form.addListener(SupplierForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        setHeight("80%");
        closeEditor();
    }

    private void deleteContact(SupplierForm.DeleteEvent evt) {
        try {
            supplierService.delete(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DataIntegrityViolationException dive) {
            Notification notification = new Notification(
                    "The Supplier is used and cannot be removed.", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void saveContact(SupplierForm.SaveEvent evt) {
        try {
            supplierService.save(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DuplicateKeyException e) {
            Notification notification = new Notification(
                    "A Supplier with this name already exist.", 3000,
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

        Button addContactButton = new Button("Add a Supplier", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Supplier());
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

    private void editContact(Supplier contact) {
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
        grid.setItems(supplierService.findAll(filterText.getValue()));
    }

}
