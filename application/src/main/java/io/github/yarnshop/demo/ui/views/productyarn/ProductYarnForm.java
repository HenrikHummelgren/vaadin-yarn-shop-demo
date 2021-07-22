package io.github.yarnshop.demo.ui.views.productyarn;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import io.github.yarnshop.demo.backend.entity.ProductYarn;
import io.github.yarnshop.demo.backend.entity.Yarn;

import java.util.List;

public class ProductYarnForm extends FormLayout {

    ComboBox<Yarn> yarn = new ComboBox<>("Yarn:");
    IntegerField numberUsed = new IntegerField("Weight (g):");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<ProductYarn> binder = new BeanValidationBinder<>(ProductYarn.class);

    public ProductYarnForm(List<Yarn> yarnList) {
        addClassName("contact-form-2");

        // The fields are bound by magic (the usual way)
        binder.bindInstanceFields(this);

        yarn.setItems(yarnList);
        yarn.setItemLabelGenerator(Yarn::getYarnNoString);
        yarn.setRequired(true);

        add(yarn,
                numberUsed,
                createButtonsLayout()
        );
    }

    public void setContact(ProductYarn contact) {
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

        return new HorizontalLayout(save, delete, close); //, gotoLink);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private void confirmAndDelete() {
        Dialog dialog = new Dialog();
        dialog.add(new Text("This operation will remove the Product to Yarn association from the database.\n" +
                "Are you sure you want to continue?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Yes", event -> {
            dialog.close();
            fireEvent(new ProductYarnForm.DeleteEvent(this, binder.getBean()));
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
    public static abstract class ContactFormEvent extends ComponentEvent<ProductYarnForm> {
        private ProductYarn contact;

        protected ContactFormEvent(ProductYarnForm source, ProductYarn contact) {
            super(source, false);
            this.contact = contact;
        }

        public ProductYarn getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ProductYarnForm source, ProductYarn contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ProductYarnForm source, ProductYarn contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ProductYarnForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

