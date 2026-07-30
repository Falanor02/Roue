package fr.falanor.roue.ui;

import fr.falanor.roue.model.WheelEntry;
import fr.falanor.roue.service.WheelService;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class EntryListPanel extends VBox {

    private final WheelService service;
    private final Runnable refreshCallback;

    private final VBox cards = new VBox(5);

    public EntryListPanel(WheelService service, Runnable refreshCallback) {

        this.service = service;
        this.refreshCallback = refreshCallback;

        setSpacing(5);
        setPadding(new Insets(5));

        ScrollPane scroll = new ScrollPane(cards);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(500);

        Button add = new Button("+ Ajouter");

        Button reset = new Button("Réinitialiser");

        add.setMaxWidth(Double.MAX_VALUE);
        reset.setMaxWidth(Double.MAX_VALUE);

        add.setOnAction(e -> addEntry());

        reset.setOnAction(e -> {
            service.resetDefaults();
            refresh();
        });

        VBox.setVgrow(scroll, Priority.ALWAYS);

        getChildren().addAll(scroll, add, reset);

        refresh();
    }

    public void refresh() {

        cards.getChildren().clear();

        for (WheelEntry entry : service.getEntries()) {

            EntryCard card = new EntryCard(
                    entry,
                    () -> {
                        service.save();
                        refreshCallback.run();
                    }
            );

            card.getDeleteButton().setOnAction(e -> {

                service.removeEntry(entry);
                refresh();

            });

            cards.getChildren().add(card);

        }

    }

    private void addEntry() {

        TextInputDialog dialog =
                new TextInputDialog();

        dialog.setTitle("Nouvelle entrée");
        dialog.setHeaderText(null);
        dialog.setContentText("Nom :");

        dialog.showAndWait().ifPresent(name -> {

            if (!name.isBlank()) {

                service.addEntry(name);

                refresh();

            }

        });

    }

}