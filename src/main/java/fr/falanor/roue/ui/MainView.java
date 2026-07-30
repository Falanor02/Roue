package fr.falanor.roue.ui;

import fr.falanor.roue.animation.WheelAnimator;
import fr.falanor.roue.model.WheelEntry;
import fr.falanor.roue.service.WheelService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

public class MainView extends BorderPane {

    private final WheelService service;

    private final WheelCanvas wheelCanvas;

    private final Label resultLabel;

    private final Button spinButton;

    private final Button winButton;

    private final Button lossButton;

    private final Button drawButton;

    private final WheelAnimator animator;

    private final EntryListPanel entryPanel;

    public MainView(WheelService service) {

        this.service = service;

        this.wheelCanvas = new WheelCanvas(600, 600);

        animator = new WheelAnimator(wheelCanvas);

        this.resultLabel = new Label("Résultat : -");

        this.spinButton = new Button("TOURNER");

        this.winButton = new Button("Victoire");

        this.lossButton = new Button("Défaite");

        this.drawButton = new Button("Match nul");

        entryPanel = new EntryListPanel(
                service,
                () -> {
                    wheelCanvas.setEntries(service.getEntries());
                    wheelCanvas.redraw();
                }
        );
        initialize();

    }

    private void initialize() {

        setPadding(new Insets(5));

        wheelCanvas.setEntries(service.getEntries());

        StackPane wheelPane = new StackPane(wheelCanvas);
        wheelPane.setAlignment(Pos.TOP_LEFT);
        wheelPane.setPadding(new Insets(5));

        setCenter(wheelPane);

        refreshList();

        spinButton.setOnAction(e -> spin());
        setBottom(spinButton);

    }

    private void refreshList() {
        VBox list = new VBox();

        for (WheelEntry e : service.getEntries()) {
            list.getChildren().add(
                    new EntryCard(
                            e,
                            this::refreshList
                    )
            );
        }

        setRight(entryPanel);
    }

    private VBox buildRightPanel() {

        VBox panel = new VBox(15);

        panel.setPadding(new Insets(20));

        panel.setPrefWidth(280);
        panel.setMinWidth(280);
        panel.setMaxWidth(280);

        panel.setStyle("""
                -fx-background-color:#2B2D31;
                """);

        resultLabel.setStyle("""
                -fx-font-size:20px;
                -fx-font-weight:bold;
                -fx-text-fill:white;
                """);

        HBox buttons = new HBox(10);

        buttons.setAlignment(Pos.CENTER);

        buttons.getChildren().addAll(
                winButton,
                lossButton,
                drawButton
        );

        VBox.setVgrow(entryPanel, Priority.ALWAYS);

        panel.getChildren().addAll(
                resultLabel,
                spinButton,
                buttons,
                entryPanel
        );

        return panel;

    }

    private void spin() {

        spinButton.setDisable(true);

        WheelEntry winner = service.spin();

        animator.spinTo(
                service.getEntries(),
                winner,
                () -> {
                    wheelCanvas.setEntries(service.getEntries());
                    wheelCanvas.redraw();
                });

        spinButton.setDisable(false);


    }

}