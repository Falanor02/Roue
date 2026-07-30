package fr.falanor.roue;

import fr.falanor.roue.service.WheelService;
import fr.falanor.roue.ui.MainView;
import fr.falanor.roue.ui.WheelCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import fr.falanor.roue.animation.WheelAnimator;

public class App extends Application {



    @Override
    public void start(Stage stage) {

        WheelService service = new WheelService();

        MainView mainView = new MainView(service);

        Scene scene = new Scene(mainView, 1400, 900);

        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("WheelUI v1.0");

        stage.setScene(scene);

        stage.show();
    }

}