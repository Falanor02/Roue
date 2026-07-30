package fr.falanor.roue;

import fr.falanor.roue.service.WheelService;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color:#202225;"
        );

        Scene scene =
                new Scene(
                        root,
                        1400,
                        900
                );
        scene.getStylesheets().add(
                getClass()
                        .getResource("/style.css")
                        .toExternalForm()
        );

        stage.setTitle(
                "WheelUI - Hearthstone"
        );

        stage.setScene(scene);

        System.out.println(
                "Projet WheelUI v1.0"
        );

        WheelService service = new WheelService();

        for (int i = 0; i < 20; i++) {

            System.out.println(
                    service.spin()
            );

        }

        stage.show();

    }

}