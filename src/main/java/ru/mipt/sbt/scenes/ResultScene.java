package ru.mipt.sbt.scenes;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.mipt.sbt.GraphPanel;
import ru.mipt.sbt.builder.Norms;
import ru.mipt.sbt.builder.Value;
import ru.mipt.sbt.utils.Constants;
import ru.mipt.sbt.utils.ScenesUtils;
import ru.mipt.sbt.writer.WriterService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 09.06.2017.
 */
public class ResultScene {
    private static final String outputFileName = "результаты анализа.xlsx";
    private Text reportInterfaceNorm;
    private Text reportInterfaceNotNorm;
    private Map<Norms, List<Value>> values;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private WriterService writerService = new WriterService();
    private File outputDirectory;

    private Scene resultScene;
    private UploadFileScene nextScene;
    private NumberScene numberScene;

    private Text reportFile;

    public ResultScene(Stage primaryStage) {
        JFXButton nextBtn = ScenesUtils.createButton("Начать сначала", 650.0, 350.0);
        nextBtn.setOnAction(event -> {
            nextScene.recreateScene();
            primaryStage.setScene(nextScene.getScene());
        });

        JFXButton prevBtn = ScenesUtils.createButton("Выбрать другие даты", 150.0, 350.0);
        prevBtn.setOnAction(event -> {
            numberScene.recreateScene();
            primaryStage.setScene(numberScene.getScene());
        });

        reportInterfaceNorm = ScenesUtils.createText(null, 200, 40, Constants.FONT);
        reportInterfaceNotNorm = ScenesUtils.createText(null, 200, 200, Constants.FONT);

        reportFile = ScenesUtils.createText(null, 400, 410, Constants.FONT);
        JFXButton printInFileBtn = ScenesUtils.createButton("Сохранить в файл", 400.0, 350.0);
        printInFileBtn.setOnAction(event -> {
            Map<Norms, List<Value>> values = numberScene.getValues();
            outputDirectory = directoryChooser.showDialog(primaryStage);
            File outputFile;
            Path outputFilePath = Paths.get(outputDirectory.getAbsolutePath(), outputFileName);
            if (Files.exists(outputFilePath)) {
                try {
                    Files.delete(outputFilePath);
                    outputFile = new File(outputDirectory, outputFileName);
                    writerService.writeResultInFile(values, outputFile);
                    reportFile.setFill(Color.DODGERBLUE);
                    reportFile.setText("Файл '" + outputFileName + "'\nсохранен");
                } catch (IOException e) {
                    reportFile.setFill(Color.FIREBRICK);
                    reportFile.setText("Файл '" + outputFileName + "'\nзанят другим процессом");
                }
            } else {
                outputFile = new File(outputDirectory, outputFileName);
                writerService.writeResultInFile(values, outputFile);
                reportFile.setFill(Color.DODGERBLUE);
                reportFile.setText("Файл '" + outputFileName + "'\nсохранен");
            }
        });

        JFXButton graphBtn = ScenesUtils.createButton("Показать график", 800.0, 350.0);
        graphBtn.setOnAction(event -> {
            GraphPanel panel = new GraphPanel();
            panel.setScores(this.values);
            SwingUtilities.invokeLater(GraphPanel::createAndShowGui);
        });

        Pane mainPane = new Pane();
        mainPane.getChildren().add(reportInterfaceNorm);
        mainPane.getChildren().add(reportInterfaceNotNorm);
        mainPane.getChildren().add(printInFileBtn);
        mainPane.getChildren().add(reportFile);
        mainPane.getChildren().add(nextBtn);
        mainPane.getChildren().add(prevBtn);
        mainPane.getChildren().add(graphBtn);

        resultScene = ScenesUtils.createScene(mainPane, primaryStage);
    }

    public void recreateScene() {
        reportFile.setText(null);
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

    public void setPreviousScene(NumberScene numberScene) {
        this.numberScene = numberScene;
    }

    public void setNextScene(UploadFileScene nextScene) {
        this.nextScene = nextScene;
    }

    public void setValues(Map<Norms, List<Value>> values) {
        this.values = values;
    }
}
