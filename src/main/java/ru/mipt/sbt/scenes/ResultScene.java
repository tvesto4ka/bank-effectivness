package ru.mipt.sbt.scenes;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.mipt.sbt.writer.WriterService;

import java.io.File;

/**
 * Created by Toma on 09.06.2017.
 */
public class ResultScene {
    private Font font = Font.font("Tahoma", FontWeight.NORMAL, 14);

    private Scene resultScene;
    private UploadFileScene nextScene;

    private final Text reportInterfaceNorm = new Text();
    private final Text reportInterfaceNotNorm = new Text();

    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private WriterService writerService = new WriterService();
    private File outputDirectory;

    public Scene createResultScene(Stage primaryStage) {
        JFXButton nextBtn = ScenesUtils.createButton("Начать сначала", 650.0, 350.0);
        nextBtn.setOnAction(event -> {
            primaryStage.setScene(nextScene.getScene());
        });

        reportInterfaceNorm.setFont(font);
        reportInterfaceNorm.setLayoutX(200.0);
        reportInterfaceNorm.setLayoutY(40.0);
        reportInterfaceNotNorm.setFont(font);
        reportInterfaceNotNorm.setLayoutX(200.0);
        reportInterfaceNotNorm.setLayoutY(200.0);

        final Text reportFile = ScenesUtils.createText(null, 400, 410, font);
        JFXButton printInFileBtn = ScenesUtils.createButton("Сохранить в файл", 400.0, 350.0);
        printInFileBtn.setOnAction(event -> {
            if (NumberScene.values != null) {
                outputDirectory = directoryChooser.showDialog(primaryStage);
                File outputFile = new File(outputDirectory, "результаты анализа.xlsx");
                writerService.writeResultInFile(NumberScene.values, outputFile);
                reportFile.setFill(Color.DODGERBLUE);
                reportFile.setText("Файл '" + outputFile.getName() + "'\nсохранен");
            } else {
                reportFile.setFill(Color.FIREBRICK);
                reportFile.setText("Необходимо загрузить файл!");
            }
        });

        Pane mainPane = new Pane();
        mainPane.getChildren().add(reportInterfaceNorm);
        mainPane.getChildren().add(reportInterfaceNotNorm);
        mainPane.getChildren().add(printInFileBtn);
        mainPane.getChildren().add(reportFile);
        mainPane.getChildren().add(nextBtn);

        resultScene = ScenesUtils.createScene(mainPane, primaryStage);
        return resultScene;
    }

    public Text getReportInterfaceNorm() {
        return reportInterfaceNorm;
    }

    public Text getReportInterfaceNotNorm() {
        return reportInterfaceNotNorm;
    }

    public Scene getScene() {
        return resultScene;
    }

    public void setNextScene(UploadFileScene nextScene) {
        this.nextScene = nextScene;
    }
}
