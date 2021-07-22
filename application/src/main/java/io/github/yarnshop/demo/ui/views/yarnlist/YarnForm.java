package io.github.yarnshop.demo.ui.views.yarnlist;

import com.github.juchar.colorpicker.ColorPickerFieldRaw;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import io.github.yarnshop.demo.backend.entity.BoxInStudio;
import io.github.yarnshop.demo.backend.entity.BoxInStore;
import io.github.yarnshop.demo.backend.entity.Yarn;

import java.util.List;

public class YarnForm extends FormLayout {

    IntegerField yarnNo = new IntegerField("Yarn no");

    IntegerField numberInStore = new IntegerField("Number in Store");
    IntegerField numberInStudio = new IntegerField("Number in Studio");

    ComboBox<BoxInStore> boxInStore = new ComboBox<>("Box in Store");
    ComboBox<BoxInStudio> boxInStudio = new ComboBox<>("Box in Studio");

    // Field used to keep the color in 6 hex digits. Not shown in GUI but used
    // by the ColorPickerFieldRaw component to keep the color value.
    TextField colorCodeString = new TextField("Color (RGB)");
    // The color picker field is originally created by Julien Charpenel and is now
    // found here:  https://vaadin.com/directory/component/color-picker-field-for-flow
    ColorPickerFieldRaw colorPickerFieldRaw = new ColorPickerFieldRaw("Color (RGB)",
            "-- no color selected --", " -- no color selected --");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Yarn> binder = new BeanValidationBinder<>(Yarn.class);

    public YarnForm(List<BoxInStore> boxInStoreList, List<BoxInStudio> boxInStudioList) {
        addClassName("contact-form");

        // add a "not selected" option
        boxInStoreList.add(0, new BoxInStore(0L, "-", null ));
        boxInStudioList.add(0, new BoxInStudio(0L, "-",   null ));

        binder.bindInstanceFields(this);

        colorPickerFieldRaw.setPinnedPalettes(true);
        colorPickerFieldRaw.setHexEnabled(false);
        colorPickerFieldRaw.setPalette("#ff0000", "#00ff00", "#0000ff", "--lumo-contrast");
        colorPickerFieldRaw.getTextField().addValueChangeListener(event -> setPaletteColor(event.getValue()));
        colorPickerFieldRaw.setChangeFormatButtonVisible(false);
        colorPickerFieldRaw.setWidth("400px");
        colorPickerFieldRaw.setCssCustomPropertiesEnabled(true);

        colorCodeString.addValueChangeListener(event -> getPaletteColor(event.getValue()));

        boxInStore.setItems(boxInStoreList);
        boxInStore.setItemLabelGenerator(BoxInStore::getName);

        boxInStudio.setItems(boxInStudioList);
        boxInStudio.setItemLabelGenerator(BoxInStudio::getName);

        add(yarnNo,
                numberInStore,
                boxInStore,
                numberInStudio,
                boxInStudio,
                colorPickerFieldRaw,
                createButtonsLayout()
        );
    }

    private void getPaletteColor(String colorStr) {
        String newColor;

        if (colorStr!=null && colorStr.length()== 6) {
            StringBuilder sb = new StringBuilder();
            sb.append("rgb(");
            sb.append(Integer.valueOf(colorStr.substring(0,2), 16).intValue());
            sb.append(",");
            sb.append(Integer.valueOf(colorStr.substring(2,4), 16).intValue());
            sb.append(",");
            sb.append(Integer.valueOf(colorStr.substring(4,6), 16).intValue());
            sb.append(")");
            newColor = sb.toString();
        }
        else {
            newColor = "";
        }

        colorPickerFieldRaw.setValue(newColor);
    }

    private void setPaletteColor(String inputString) {
        try {
            String result = inputString.substring(inputString.indexOf("(") + 1, inputString.indexOf(")"));
            String[] colors = result.split(",");
            Integer redInt = Integer.valueOf(colors[0].trim());
            Integer greenInt = Integer.valueOf(colors[1].trim());
            Integer blueInt = Integer.valueOf(colors[2].trim());

            StringBuffer sb = new StringBuffer();
            sb.append(pruneColorCode(Integer.toHexString(redInt).toUpperCase()));
            sb.append(pruneColorCode(Integer.toHexString(greenInt).toUpperCase()));
            sb.append(pruneColorCode(Integer.toHexString(blueInt).toUpperCase()));

            colorCodeString.setValue(sb.toString());
        }
        catch (Exception e) {
            colorCodeString.setValue("");
        }
    }

    private String pruneColorCode(String codeString) {
        String returnString = codeString;
        if (codeString.length() < 2) {
            returnString = "0" + codeString;
        }
        return returnString;
    }

    public void setContact(Yarn contact) {
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
        dialog.add(new Text("This operation will remove the Yarn from the database.\n" +
                "Are you sure you want to continue?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Yes", event -> {
            dialog.close();
            fireEvent(new DeleteEvent(this, binder.getBean()));
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
    public static abstract class ContactFormEvent extends ComponentEvent<YarnForm> {
      private Yarn contact;

      protected ContactFormEvent(YarnForm source, Yarn contact) {
        super(source, false);
        this.contact = contact;
      }

      public Yarn getContact() {
        return contact;
      }
    }

    public static class SaveEvent extends ContactFormEvent {
      SaveEvent(YarnForm source, Yarn contact) {
        super(source, contact);
      }
    }

    public static class DeleteEvent extends ContactFormEvent {
      DeleteEvent(YarnForm source, Yarn contact) {
        super(source, contact);
      }
    }

    public static class CloseEvent extends ContactFormEvent {
      CloseEvent(YarnForm source) {
        super(source, null);
      }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
      return getEventBus().addListener(eventType, listener);
    }
}
