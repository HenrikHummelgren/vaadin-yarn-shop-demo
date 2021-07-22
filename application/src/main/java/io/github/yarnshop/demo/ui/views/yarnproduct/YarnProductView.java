package io.github.yarnshop.demo.ui.views.yarnproduct;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.entity.Product;
import io.github.yarnshop.demo.backend.entity.Yarn;
import io.github.yarnshop.demo.backend.entity.YarnProduct;
import io.github.yarnshop.demo.backend.service.ProductService;
import io.github.yarnshop.demo.backend.service.YarnService;
import io.github.yarnshop.demo.ui.MainLayout;
import io.github.yarnshop.demo.ui.views.productlist.ProductView;

import java.util.List;

@Component
@Scope("prototype")
@Route(value = "yarn-product", layout = MainLayout.class)
@PageTitle("Yarn-Product List | Yarn Shop")
public class YarnProductView extends VerticalLayout implements HasUrlParameter<String> {

    YarnProductForm form;
    Grid<YarnProduct> grid = new Grid<>(YarnProduct.class);

    Long currentYarnId = 0L;

    List<Yarn> yarnList;
    ComboBox<Yarn> yarnComboBox = new ComboBox<>("Yarn:");

    Button addContactButton = new Button("Add Product", click -> addContact());

    ProductService productService;
    YarnService yarnService;

    public YarnProductView(ProductService productService, YarnService yarnService) {
        this.productService = productService;
        this.yarnService = yarnService;

        addClassName("person-view");
        setSizeFull();
        configureGrid();

        form = new YarnProductForm(productService.findAll());
        form.addListener(YarnProductForm.SaveEvent.class, this::saveContact);
        form.addListener(YarnProductForm.DeleteEvent.class, this::deleteContact);
        form.addListener(YarnProductForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        setHeight("80%");
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();

        grid.removeColumnByKey("product");

        grid.setColumns("numberUsed");
        grid.getColumnByKey("numberUsed").setHeader("Vikt (g)");

        grid.addColumn(contact -> {
            Product type = contact.getProduct();
            return (type == null) ? "-" : type.getProductNoString();
        }).setHeader("Product number");

        grid.addColumn(contact -> {
            Product type = contact.getProduct();
            return (type == null) ? "-" : type.getName();
        }).setHeader("Product name");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

        // Go to Product view with same Product number when double-clicked
        grid.addItemDoubleClickListener(item -> UI.getCurrent().navigate(ProductView.class,
                item.getItem().getProduct().getProductNoString()));
    }

    private HorizontalLayout getToolBar() {

        addContactButton.setEnabled(false);

        yarnList = yarnService.findAll();
        yarnComboBox.setItems(yarnList);
        yarnComboBox.addValueChangeListener(e -> search());

        HorizontalLayout toolbar = new HorizontalLayout(yarnComboBox, addContactButton);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void search() {
        Yarn selectedYarn = yarnComboBox.getValue();

        if (selectedYarn != null) {
            currentYarnId = selectedYarn.getId();

            addContactButton.setEnabled(true);
            updateList();
        }
    }

    private void deleteContact(YarnProductForm.DeleteEvent evt) {
        yarnService.delete(evt.getContact());
        updateList();
    }

    private void addContact() {
        grid.asSingleSelect().clear();

        YarnProduct yarnProduct = new YarnProduct();
        yarnProduct.setYarnId(currentYarnId);

        editContact(yarnProduct);
    }

    private void editContact(YarnProduct contact) {
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
        grid.setItems(yarnService.findAllYarnProducts(currentYarnId));
    }

    private void saveContact(YarnProductForm.SaveEvent evt) {

        YarnProduct participator = evt.getContact();

        if (participator.getProduct() == null) {
            Notification notification = new Notification(
                    "A Product must be chosen!", 3000,
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
                        "The Product is already chosen for this Yarn.", 3000,
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
            for (Yarn item : yarnList) {
               if (item.getYarnNoString().equals(s)) {
                    yarnComboBox.setValue(item);
               }
           }
        }
    }
}
