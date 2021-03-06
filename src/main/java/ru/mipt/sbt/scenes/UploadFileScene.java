package ru.mipt.sbt.scenes;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.mipt.sbt.reader.ReaderService;
import ru.mipt.sbt.utils.Constants;
import ru.mipt.sbt.utils.ScenesUtils;

import java.io.File;

/**
 * Created by Toma on 09.06.2017.
 */
public class UploadFileScene {

    private File inputFile;
    private Text infoMess;
    private Scene uploadScene;
    private NumberScene nextScene;
    private JFXButton nextBtn;

    public UploadFileScene(Stage primaryStage) {
        ReaderService readerService = new ReaderService();
        FileChooser fileChooser = new FileChooser();
        final Text welcomeMess = ScenesUtils.createText("Пожалуйста, выберете файл с финансовым отчетом банка", 280, 180, Constants.HEADER_FONT);
        infoMess = ScenesUtils.createText(null, 420, 270, Constants.FONT);
        nextBtn = ScenesUtils.createButton("Далее", 400.0, 350.0);
        nextBtn.setDisable(true);
        nextBtn.setOnAction(event -> {
            nextScene.recreateScene();
            primaryStage.setScene(nextScene.getScene());
        });
        JFXButton uploadFile = ScenesUtils.createButton("Загрузить файл", 400.0, 200.0);
        uploadFile.setOnAction(event -> {
            inputFile = fileChooser.showOpenDialog(primaryStage);
            if (inputFile != null) {
                String fileType = inputFile.getAbsolutePath().split("\\.")[1];
                if (fileType.equalsIgnoreCase("xlsx") || fileType.equalsIgnoreCase("xls")) {
                    try {
                        readerService.readFile(inputFile, 1);
                        infoMess.setFill(Color.DODGERBLUE);
                        infoMess.setText("Файл успешно загружен");
                        nextBtn.setDisable(false);
                    } catch (RuntimeException e) {
                        infoMess.setFill(Color.FIREBRICK);
                        infoMess.setText("Файл не соответствует формату!");
                    }
                } else {
                    infoMess.setFill(Color.FIREBRICK);
                    infoMess.setText("Это не Excel файл!");
                }
            } else {
                infoMess.setFill(Color.FIREBRICK);
                infoMess.setText("Загрузите файл");
            }
        });
        Pane mainPane = new Pane();
        mainPane.getChildren().add(welcomeMess);
        mainPane.getChildren().add(uploadFile);
        mainPane.getChildren().add(infoMess);
        mainPane.getChildren().add(nextBtn);

        uploadScene = ScenesUtils.createScene(mainPane, primaryStage);
    }

    public Scene getScene() {
        return uploadScene;
    }

    public void setNextScene(NumberScene nextScene) {
        this.nextScene = nextScene;
    }

    public void recreateScene() {
        inputFile = null;
        infoMess.setText(null);
        nextBtn.setDisable(true);
    }

    public File getInputFile() {
        return inputFile;
    }
}
