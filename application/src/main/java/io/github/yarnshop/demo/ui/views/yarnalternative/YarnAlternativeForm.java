package io.github.yarnshop.demo.ui.views.yarnalternative;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import io.github.yarnshop.demo.backend.entity.Supplier;
import io.github.yarnshop.demo.backend.entity.Yarn;
import io.github.yarnshop.demo.backend.entity.YarnAlternative;

import java.util.List;

public class YarnAlternativeForm extends FormLayout {

    ComboBox<Yarn> yarn = new ComboBox<>("Yarn");
    ComboBox<Supplier> supplier = new ComboBox<>("Supplier");
    TextField altYarnId = new TextField("Yarn ID (supplier)");
    TextArea description = new TextArea("Comment");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<YarnAlternative> binder = new BeanValidationBinder<>(YarnAlternative.class);

    public YarnAlternativeForm(List<Supplier> supplierList, List<Yarn> yarnList) {
        addClassName("contact-yarn-alt");

        // The other fields are bound by magic (altYarnId, description)
        binder.bindInstanceFields(this);

        yarn.setItems(yarnList);
        yarn.setItemLabelGenerator(Yarn::getYarnNoString);
        yarn.setRequired(true);

        supplier.setItems(supplierList);
        supplier.setItemLabelGenerator(Supplier::getName);
        supplier.setRequired(true);

        altYarnId.setRequired(true);

        add(yarn,
                supplier,
                altYarnId,
                description,
                createButtonsLayout()
        );
    }

    public void setContact(YarnAlternative contact) {
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
        dialog.add(new Text("This operation will remove the alternative Yarn from the database.\n" +
                "Are you sure you want to continue?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Yes", event -> {
            dialog.close();
            fireEvent(new YarnAlternativeForm.DeleteEvent(this, binder.getBean()));
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
    public static abstract class ContactFormEvent extends ComponentEvent<YarnAlternativeForm> {
        private YarnAlternative contact;

        protected ContactFormEvent(YarnAlternativeForm source, YarnAlternative contact) {
            super(source, false);
            this.contact = contact;
        }

        public YarnAlternative getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(YarnAlternativeForm source, YarnAlternative contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(YarnAlternativeForm source, YarnAlternative contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(YarnAlternativeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
