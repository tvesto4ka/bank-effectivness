package ru.mipt.sbt.scenes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.mipt.sbt.builder.AnalysisService;
import ru.mipt.sbt.builder.Norms;
import ru.mipt.sbt.builder.Value;
import ru.mipt.sbt.reader.BankReportInfo;
import ru.mipt.sbt.reader.ReaderService;
import ru.mipt.sbt.utils.Constants;
import ru.mipt.sbt.utils.ScenesUtils;
import ru.mipt.sbt.writer.WriterService;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 09.06.2017.
 */
public class NumberScene {

    private Map<Norms, List<Value>> values;
    private Scene numberScene;
    private ResultScene nextScene;
    private UploadFileScene prevScene;
    private JFXButton nextBtn;
    private Text infoMess;

    public NumberScene(Stage primaryStage) {
        WriterService writerService = new WriterService();
        ReaderService readerService = new ReaderService();
        AnalysisService analysisService = new AnalysisService();
        final Text welcomeMess = ScenesUtils.createText("Пожалуйста, введите количество дат для анализа", 280, 180, Constants.HEADER_FONT);
        infoMess = ScenesUtils.createText(null, 380, 270, Constants.FONT);
        nextBtn = ScenesUtils.createButton("Посмотреть результаты", 530.0, 350.0);
        nextBtn.setDisable(true);
        nextBtn.setOnAction(event -> {
            String resultNorm = writerService.writeNormalResultInInterface(values);
            String resultNotNorm = writerService.writeNotNormalResultInInterface(values);
            nextScene.getReportInterfaceNorm().setFill(Color.FORESTGREEN);
            nextScene.getReportInterfaceNorm().setText(resultNorm);
            nextScene.getReportInterfaceNotNorm().setFill(Color.FIREBRICK);
            nextScene.getReportInterfaceNotNorm().setText(resultNotNorm);
            primaryStage.setScene(nextScene.getScene());
        });
        JFXButton prevBtn = ScenesUtils.createButton("Начать сначала", 230.0, 350.0);
        prevBtn.setOnAction(event -> {
            prevScene.recreateScene();
            primaryStage.setScene(prevScene.getScene());
        });
        JFXTextField userTextField = new JFXTextField();
        userTextField.setLayoutX(400.0);
        userTextField.setLayoutY(200.0);
        userTextField.setOnAction(event -> {
            File inputFile = prevScene.getInputFile();
            if (inputFile != null) {
                Integer year;
                try {
                    year = Integer.valueOf(userTextField.getText());
                    try {
                        List<BankReportInfo> data = readerService.readFile(inputFile, year);
                        values = analysisService.analyseReport(data);
                        //writerService.writeResultInConsole(values);
                        infoMess.setFill(Color.DODGERBLUE);
                        infoMess.setText("Выбранное количество дат \nуспешно проанализировано");
                        nextBtn.setDisable(false);
                    } catch (RuntimeException e) {
                        infoMess.setFill(Color.FIREBRICK);
                        infoMess.setText("В файле нет данных на указанное число дат");
                    }
                } catch (RuntimeException e) {
                    infoMess.setFill(Color.FIREBRICK);
                    infoMess.setText("Введите число!");
                }
            } else {
                infoMess.setFill(Color.FIREBRICK);
                infoMess.setText("Необходимо загрузить файл!");
            }
        });

        Pane mainPane = new Pane();
        mainPane.getChildren().add(welcomeMess);
        mainPane.getChildren().add(userTextField);
        mainPane.getChildren().add(infoMess);
        mainPane.getChildren().add(nextBtn);
        mainPane.getChildren().add(prevBtn);

        numberScene = ScenesUtils.createScene(mainPane, primaryStage);
    }

    public Scene getScene() {
        return numberScene;
    }

    public void setNextScene(ResultScene nextScene) {
        this.nextScene = nextScene;
    }

    public Map<Norms, List<Value>> getValues() {
        return values;
    }

    public void setPrevScene(UploadFileScene prevScene) {
        this.prevScene = prevScene;
    }

    public void recreateScene() {
        nextBtn.setDisable(true);
        infoMess.setText(null);
        values = null;
    }
}
