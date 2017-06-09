package ru.mipt.sbt.scenes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.mipt.sbt.builder.AnalysisService;
import ru.mipt.sbt.builder.Norms;
import ru.mipt.sbt.builder.Value;
import ru.mipt.sbt.reader.BankReportInfo;
import ru.mipt.sbt.reader.ReaderService;
import ru.mipt.sbt.writer.WriterService;

import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 09.06.2017.
 */
public class NumberScene {
    private Font font = Font.font("Tahoma", FontWeight.NORMAL, 14);
    private Font headerFont = Font.font("Tahoma", FontWeight.NORMAL, 18);

    public static Map<Norms, List<Value>> values;
    private List<BankReportInfo> data;
    private WriterService writerService = new WriterService();
    private ReaderService readerService = new ReaderService();
    private AnalysisService analysisService = new AnalysisService();

    private Scene numberScene;
    private ResultScene nextScene;
    private UploadFileScene prevScene;

    public NumberScene createDataNumberScene(Stage primaryStage) {
        final Text welcomeMess = ScenesUtils.createText("Пожалуйста, введите количество дат для анализа", 280, 180, headerFont);
        final Text infoMess = ScenesUtils.createText(null, 380, 270, font);
        JFXButton nextBtn = ScenesUtils.createButton("Посмотреть результаты", 530.0, 350.0);
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
            primaryStage.setScene(prevScene.getScene());
        });
        JFXTextField userTextField = new JFXTextField();
        userTextField.setLayoutX(400.0);
        userTextField.setLayoutY(200.0);
        userTextField.setOnAction(event -> {
            if (UploadFileScene.inputFile != null) {
                Integer year;
                try {
                    year = Integer.valueOf(userTextField.getText());
                    try {
                        data = readerService.readFile(UploadFileScene.inputFile, year);
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

        return this;
    }

    public Scene getScene() {
        return numberScene;
    }

    public void setNextScene(ResultScene nextScene) {
        this.nextScene = nextScene;
    }

    public void setPrevScene(UploadFileScene prevScene) {
        this.prevScene = prevScene;
    }
}
