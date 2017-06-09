package ru.mipt.sbt.scenes;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.mipt.sbt.utils.Constants;
import ru.mipt.sbt.utils.ScenesUtils;
import ru.mipt.sbt.writer.WriterService;

import java.io.File;

/**
 * Created by Toma on 09.06.2017.
 */
public class ResultScene {
    private Text reportInterfaceNorm;
    private Text reportInterfaceNotNorm;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private WriterService writerService = new WriterService();
    private File outputDirectory;

    private Scene resultScene;
    private UploadFileScene nextScene;

    public Scene createResultScene(Stage primaryStage) {
        JFXButton nextBtn = ScenesUtils.createButton("Начать сначала", 650.0, 350.0);
        nextBtn.setOnAction(event -> {
            primaryStage.setScene(nextScene.getScene());
        });

        reportInterfaceNorm = ScenesUtils.createText(null, 200, 40, Constants.FONT);
        reportInterfaceNotNorm = ScenesUtils.createText(null, 200, 200, Constants.FONT);

        final Text reportFile = ScenesUtils.createText(null, 400, 410, Constants.FONT);
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
