package io.github.yarnshop.demo.ui.views.productyarn;

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
import io.github.yarnshop.demo.backend.entity.ProductYarn;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.entity.Product;
import io.github.yarnshop.demo.backend.service.ProductService;
import io.github.yarnshop.demo.backend.service.YarnService;
import io.github.yarnshop.demo.ui.MainLayout;
import io.github.yarnshop.demo.ui.views.yarnlist.YarnView;

import java.util.List;

@Component
@Scope("prototype")
@Route(value = "product-yarn", layout = MainLayout.class)
@PageTitle("Product-Yarn List | Yarn Shop")
public class ProductYarnView extends VerticalLayout implements HasUrlParameter<String> {

    ProductYarnForm form;
    Grid<ProductYarn> grid = new Grid<>(ProductYarn.class);

    Long currentProductId = 0L;

    List<Product> productList;
    ComboBox<Product> productComboBox = new ComboBox<>("Product:");

    Button addContactButton = new Button("Add a Yarn", click -> addContact());

    ProductService productService;
    YarnService yarnService;

    public ProductYarnView(ProductService productService, YarnService yarnService) {
        this.productService = productService;
        this.yarnService = yarnService;

        addClassName("person-view");
        setSizeFull();
        configureGrid();

        form = new ProductYarnForm(yarnService.findAll());
        form.addListener(ProductYarnForm.SaveEvent.class, this::saveContact);
        form.addListener(ProductYarnForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ProductYarnForm.CloseEvent.class, e -> closeEditor());

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

        grid.removeAllColumns();

        grid.addColumn(contact -> contact.getYarn().getYarnNo()).
                setHeader("Yarn ID").setSortable(true);

        grid.addColumn(contact -> contact.getNumberUsed()).
                setHeader("Weight (g)").setSortable(true);

        grid.addColumn(contact -> contact.getNumberOfBallsAtHome()).
                setHeader("Number of balls").setSortable(true);

        grid.addColumn(contact -> contact.getNumberOfBallsAtHome()*100/contact.getNumberUsed()).
                setHeader("Will be enough for number").setSortable(true);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

        // Go to Yarn view with same Yarn number when double-clicked
        grid.addItemDoubleClickListener(item -> UI.getCurrent().navigate(YarnView.class,
                item.getItem().getYarn().getYarnNoString()));
    }

    private HorizontalLayout getToolBar() {

        addContactButton.setEnabled(false);

        productList = productService.getProductList();
        productComboBox.setItems(productList);
        productComboBox.addValueChangeListener(e -> search());

        HorizontalLayout toolbar = new HorizontalLayout(productComboBox, addContactButton);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void search() {
        Product selectedProduct = productComboBox.getValue();

        if (selectedProduct != null) {
            currentProductId = selectedProduct.getId();

            addContactButton.setEnabled(true);
            updateList();
        }
    }

    private void deleteContact(ProductYarnForm.DeleteEvent evt) {
        productService.delete(evt.getContact());
        updateList();
    }

    private void addContact() {
        grid.asSingleSelect().clear();

        ProductYarn productYarn = new ProductYarn();
        productYarn.setProductId(currentProductId);

        editContact(productYarn);
    }

    private void editContact(ProductYarn contact) {
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
        grid.setItems(productService.findAllProductYarns(currentProductId));
    }

    private void saveContact(ProductYarnForm.SaveEvent evt) {

        ProductYarn participator = evt.getContact();

        if (participator.getYarn() == null) {
            Notification notification = new Notification(
                    "A Yarn must be chosen!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
        else if (participator.getNumberUsed() == null || participator.getNumberUsed() < 1 ) {
            Notification notification = new Notification(
                    "Weight must be at least 1 gram!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
        else {
            try {
                productService.save(evt.getContact());
                updateList();
                closeEditor();
            }
            catch (DuplicateKeyException e) {
                Notification notification = new Notification(
                        "The Yarn is already chosen for this Product.", 3000,
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
            for (Product item : productList) {
                if (item.getProductNo().toString().equals(s)) {
                    productComboBox.setValue(item);
                }
            }
        }
    }
}
