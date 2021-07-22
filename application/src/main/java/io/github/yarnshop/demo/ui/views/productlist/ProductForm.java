package io.github.yarnshop.demo.ui.views.productlist;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import io.github.yarnshop.demo.backend.entity.Product;

import java.util.List;

public class ProductForm extends FormLayout {

    IntegerField productNo = new IntegerField("Product no");
    TextField name = new TextField("Name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Product> binder = new BeanValidationBinder<>(Product.class);

    public ProductForm(List<Product> companies) {
        addClassName("contact-form");

        binder.bindInstanceFields(this);

        add(productNo,
                name,
            createButtonsLayout()
        );
    }

    public void setContact(Product contact) {
        binder.setBean(contact);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> confirmAndDelete());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private void confirmAndDelete() {
        Dialog dialog = new Dialog();
        dialog.add(new Text("This operation will remove the Product from the database.\n" +
                "Are you sure you want to continue?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Yes", event -> {
            dialog.close();
            fireEvent(new ProductForm.DeleteEvent(this, binder.getBean()));
        });
        Button cancelButton = new Button("No", event -> {
            dialog.close();
        });

        // Cancel action on ESC press
        Shortcuts.addShortcutListener(dialog, () -> {
            dialog.close();
        }, Key.ESCAPE);

        dialog.add(new Div( confirmButton, cancelButton));
        dialog.open();
    }

    public static abstract class ContactFormEvent extends ComponentEvent<ProductForm> {
      private Product contact;

      protected ContactFormEvent(ProductForm source, Product contact) {
        super(source, false);
        this.contact = contact;
      }

      public Product getContact() {
        return contact;
      }
    }

    public static class SaveEvent extends ContactFormEvent {
      SaveEvent(ProductForm source, Product contact) {
        super(source, contact);
      }
    }

    public static class DeleteEvent extends ContactFormEvent {
      DeleteEvent(ProductForm source, Product contact) {
        super(source, contact);
      }
    }

    public static class CloseEvent extends ContactFormEvent {
      CloseEvent(ProductForm source) {
        super(source, null);
      }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
      return getEventBus().addListener(eventType, listener);
    }
}
