package io.github.yarnshop.demo.ui.views.supplier;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import io.github.yarnshop.demo.backend.entity.Supplier;

import java.util.List;

public class SupplierForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField description = new TextField("Description");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Supplier> binder = new BeanValidationBinder<>(Supplier.class);

    public SupplierForm(List<Supplier> companies) {
        addClassName("contact-form");

        binder.bindInstanceFields(this);

        add(name,
            description,
            createButtonsLayout()
        );
    }

    public void setContact(Supplier contact) {
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
        dialog.add(new Text("This operation will remove the Supplier from the database.\n" +
                "Are you sure you want to continue?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Yes", event -> {
            dialog.close();
            fireEvent(new SupplierForm.DeleteEvent(this, binder.getBean()));
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

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<SupplierForm> {
      private Supplier contact;

      protected ContactFormEvent(SupplierForm source, Supplier contact) {
        super(source, false);
        this.contact = contact;
      }

      public Supplier getContact() {
        return contact;
      }
    }

    public static class SaveEvent extends ContactFormEvent {
      SaveEvent(SupplierForm source, Supplier contact) {
        super(source, contact);
      }
    }

    public static class DeleteEvent extends ContactFormEvent {
      DeleteEvent(SupplierForm source, Supplier contact) {
        super(source, contact);
      }
    }

    public static class CloseEvent extends ContactFormEvent {
      CloseEvent(SupplierForm source) {
        super(source, null);
      }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
      return getEventBus().addListener(eventType, listener);
    }
}
