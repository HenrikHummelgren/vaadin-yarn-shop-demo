package io.github.yarnshop.demo.ui.views.productlist;

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
import io.github.yarnshop.demo.ui.views.productyarn.ProductYarnView;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.entity.Product;
import io.github.yarnshop.demo.backend.service.ProductService;
import io.github.yarnshop.demo.ui.MainLayout;

@Component
@Scope("prototype")
@Route(value = "product-list", layout = MainLayout.class)
@PageTitle("Product List | Yarn Shop")
public class ProductView extends VerticalLayout implements HasUrlParameter<String> {

    ProductForm form;
    Grid<Product> grid = new Grid<>(Product.class);
    TextField filterText = new TextField();

    ProductService productService;

    public ProductView(ProductService productService) {
        this.productService = productService;
        addClassName("type-view");
        setSizeFull();
        configureGrid();

        form = new ProductForm(productService.findAll());
        form.addListener(ProductForm.SaveEvent.class, this::saveContact);
        form.addListener(ProductForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ProductForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        setHeight("80%");
        updateList();
        closeEditor();
    }

    private void deleteContact(ProductForm.DeleteEvent evt) {
        try {
            productService.delete(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DataIntegrityViolationException dive) {
            Notification notification = new Notification(
                    "The Product used one or more Yarns and cannot be removed.", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void saveContact(ProductForm.SaveEvent evt) {
        try {
            productService.save(evt.getContact());
            updateList();
            closeEditor();
        }
        catch (DuplicateKeyException e) {
            Notification notification = new Notification(
                    "A Product with this number already exists.", 3000,
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
        filterText.setPlaceholder("Filter on Product no");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add a Product", click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Product());
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();

        grid.setColumns("productNo", "name", "noOfYarn");

        grid.getColumnByKey("productNo").setHeader("Product no");
        grid.getColumnByKey("name").setHeader("Name");
        grid.getColumnByKey("noOfYarn").setHeader("Number of Yarns");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

        // Go to Product Yarn view with same Product number when double-clicked
        grid.addItemDoubleClickListener(item -> UI.getCurrent().navigate(ProductYarnView.class,
                item.getItem().getProductNoString()));
    }

    private void editContact(Product contact) {
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
        grid.setItems(productService.findAll(filterText.getValue()));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String s) {
        if (s != null && s.length() > 0) {
            filterText.setValue(s);
        }
    }
}
