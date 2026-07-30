package fr.falanor.roue.ui;

import fr.falanor.roue.model.WheelEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EntryCard extends VBox {

    private final WheelEntry entry;

    private final TextField nameField;
    private final Label weightLabel;

    private final Button plusButton;
    private final Button minusButton;
    private final Button deleteButton;

    private final ColorPicker colorPicker;

    private final Runnable onChanged;

    public EntryCard(WheelEntry entry, Runnable onChanged) {

        this.entry = entry;
        this.onChanged = onChanged;
        setSpacing(5);
        setPadding(new Insets(5));

        setStyle("""
                -fx-background-color:#2F3136;
                -fx-background-radius:12;
                -fx-border-radius:12;
                -fx-border-color:#444;
                """);

        //-----------------------

        nameField = new TextField(entry.getName());

        nameField.setStyle("""
                -fx-font-size:8;
                """);

        //-----------------------

        weightLabel = new Label(
                "Poids : " + entry.getWeight()
        );

        weightLabel.setStyle("""
                -fx-text-fill:white;
                -fx-font-size:8;
                -fx-font-weight:bold;
                """);

        //-----------------------

        minusButton = new Button("-");

        plusButton = new Button("+");

        deleteButton = new Button("Supprimer");

        //-----------------------

        colorPicker = new ColorPicker(
                Color.web(entry.getColor())
        );

        //-----------------------

        HBox buttons = new HBox(
                10,
                minusButton,
                plusButton,
                deleteButton
        );

        buttons.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(
                nameField,
                weightLabel,
                colorPicker,
                buttons
        );

        installEvents();

    }

    private void installEvents() {

        plusButton.setOnAction(e -> {

            entry.setWeight(
                    entry.getWeight() + 1
            );

            refresh();
            if (onChanged != null)
                onChanged.run();

        });

        minusButton.setOnAction(e -> {

            entry.setWeight(
                    Math.max(
                            2,
                            entry.getWeight() - 1
                    )
            );

            refresh();
            if (onChanged != null)
                onChanged.run();

        });

        nameField.textProperty().addListener(
                (obs, oldValue, newValue) ->

                        entry.setName(newValue)

        );

        colorPicker.setOnAction(e -> {

            entry.setColor(

                    toHex(
                            colorPicker.getValue()
                    )

            );
            refresh();
            if (onChanged != null)
                onChanged.run();

        });

    }

    private void refresh() {

        weightLabel.setText(
                "Poids : " + entry.getWeight()
        );

    }

    private String toHex(Color color) {

        return String.format(
                "#%02X%02X%02X",
                (int)(color.getRed()*255),
                (int)(color.getGreen()*255),
                (int)(color.getBlue()*255)
        );

    }

    public Button getDeleteButton() {

        return deleteButton;

    }

}