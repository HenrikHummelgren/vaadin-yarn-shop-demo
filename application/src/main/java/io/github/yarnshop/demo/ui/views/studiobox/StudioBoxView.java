package io.github.yarnshop.demo.ui.views.studiobox;

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
import io.github.yarnshop.demo.backend.entity.BoxInStudio;
import org.springframework.dao.DataIntegrityViolationException;
import io.github.yarnshop.demo.backend.service.StudioBoxService;
import io.github.yarnshop.demo.ui.MainLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "studio-box", layout = MainLayout.class)
@PageTitle("Studio Box | Yarn Shop")
public class StudioBoxView extends VerticalLayout {

    StudioBoxForm form;
    Grid<BoxInStudio> grid = new Grid<>(BoxInStudio.class);
    TextField filterText = new TextField();

    StudioBoxService studioBoxService;

    public StudioBoxView(StudioBoxService studioBoxService) {
        this.studioBoxService = studioBoxService;
        addClassName("type-view");
        setSizeFull();
        configureGrid();

        form = new StudioBoxForm(studioBoxService.findAll());
        form.addListener(StudioBoxForm.SaveEvent.class, this::saveContact);
        form.addListener(StudioBoxForm.DeleteEvent.class, this::deleteContact);
        form.addListener(StudioBoxForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        setHeight("80%");
        closeEditor();
    }

    private void deleteContact(StudioBoxForm.DeleteEvent evt) {
        try {
            studioBoxService.delete(evt.getContact());
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

    private void saveContact(StudioBoxForm.SaveEvent evt) {
        try {
            studioBoxService.save(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DuplicateKeyException e) {
            Notification notification = new Notification(
                    "A Box with this name already exists.", 3000,
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
        editContact(new BoxInStudio());
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

    private void editContact(BoxInStudio contact) {
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
        grid.setItems(studioBoxService.findAll(filterText.getValue()));
    }

}
