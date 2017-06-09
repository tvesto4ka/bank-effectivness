package ru.mipt.sbt.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Toma on 09.06.2017.
 */
public class ScenesUtils {

    public static JFXButton createButton(String name, double x, double y) {
        JFXButton btn = new JFXButton();
        btn.setText(name);
        btn.getStyleClass().add("button-raised");
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        return btn;
    }

    public static Text createText(String name, double x, double y, Font font) {
        Text text = new Text();
        text.setFont(font);
        text.setText(name);
        text.setLayoutX(x);
        text.setLayoutY(y);
        return text;
    }

    public static Scene createScene(Pane mainPane, Stage primaryStage) {
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:WHITE");

        stackPane.getChildren().add(mainPane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(stackPane).getView());

        Scene scene = new Scene(decorator, 1000, 500);
        scene.getStylesheets().add(ScenesUtils.class.getResource("/jfoenix-components.css").toExternalForm());
        return scene;
    }
}
