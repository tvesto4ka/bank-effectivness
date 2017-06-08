package ru.mipt.sbt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.mipt.sbt.builder.AnalysisService;
import ru.mipt.sbt.builder.Norms;
import ru.mipt.sbt.builder.Value;
import ru.mipt.sbt.reader.BankReportInfo;
import ru.mipt.sbt.reader.ReaderService;
import ru.mipt.sbt.writer.WriterService;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 28.05.2017.
 */
public class MultScenes extends Application {

    private Map<Norms, List<Value>> values;
    private List<BankReportInfo> data;
    private WriterService writerService = new WriterService();
    private ReaderService readerService = new ReaderService();
    private AnalysisService analysisService = new AnalysisService();
    private File inputFile;
    private File outputDirectory;

    private Scene numberScene;
    private Scene uploadScene;
    private Scene resultScene;

    private Font font = Font.font("Tahoma", FontWeight.NORMAL, 14);
    private Font headerFont = Font.font("Tahoma", FontWeight.NORMAL, 18);

    private final FileChooser fileChooser = new FileChooser();
    final DirectoryChooser directoryChooser = new DirectoryChooser();

    private void createUploadFileScene(Stage primaryStage){
        final Text uploadFileMess = new Text();
        uploadFileMess.setFont(headerFont);
        uploadFileMess.setText("Пожалуйста, выберете файл с финансовым отчетом банка");
        uploadFileMess.setLayoutX(280);
        uploadFileMess.setLayoutY(180.0);
        final Text uploadFileText = new Text();
        uploadFileText.setFont(font);
        uploadFileText.setLayoutX(420);
        uploadFileText.setLayoutY(270);
        JFXButton nextBtn = new JFXButton();
        nextBtn.setText("Далее");
        nextBtn.getStyleClass().add("button-raised");
        nextBtn.setLayoutX(400.0);
        nextBtn.setLayoutY(350.0);
        nextBtn.setDisable(true);
        nextBtn.setOnAction(event -> {
            primaryStage.setScene(numberScene);
        });
        JFXButton uploadFile = new JFXButton();
        uploadFile.setText("Загрузить файл");
        uploadFile.getStyleClass().add("button-raised");
        uploadFile.setLayoutX(400.0);
        uploadFile.setLayoutY(200.0);
        uploadFile.setOnAction(event -> {
            inputFile = fileChooser.showOpenDialog(primaryStage);
            if (inputFile != null && inputFile.getAbsolutePath().split("\\.")[1].equalsIgnoreCase("xlsx")) {
                uploadFileText.setFill(Color.DODGERBLUE);
                uploadFileText.setText("Файл успешно загружен");
                nextBtn.setDisable(false);
            } else {
                uploadFileText.setFill(Color.FIREBRICK);
                uploadFileText.setText("Это не .xlsx файл!");
            }
        });
        Pane uploadFilePane = new Pane();
        uploadFilePane.getChildren().add(uploadFileMess);
        uploadFilePane.getChildren().add(uploadFile);
        uploadFilePane.getChildren().add(uploadFileText);
        uploadFilePane.getChildren().add(nextBtn);

        StackPane uploadFileScene = new StackPane();
        uploadFileScene.setStyle("-fx-background-color:WHITE");

        uploadFileScene.getChildren().add(uploadFilePane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(uploadFileScene).getView());

        uploadScene = new Scene(decorator, 1000, 500);
        uploadScene.getStylesheets().add(this.getClass().getResource("/jfoenix-components.css").toExternalForm());
    }

    private void createDataNumberScene(Stage primaryStage) {
        final Text numberMess = new Text();
        numberMess.setFont(headerFont);
        numberMess.setText("Пожалуйста, введите количество дат для анализа");
        numberMess.setLayoutX(280);
        numberMess.setLayoutY(180.0);
        final Text numberOfYearsText = new Text();
        numberOfYearsText.setFont(font);
        numberOfYearsText.setLayoutX(380);
        numberOfYearsText.setLayoutY(270);
        JFXButton nextBtn = new JFXButton();
        nextBtn.setText("Далее");
        nextBtn.getStyleClass().add("button-raised");
        nextBtn.setLayoutX(530.0);
        nextBtn.setLayoutY(350.0);
        nextBtn.setDisable(true);
        nextBtn.setOnAction(event -> {
            primaryStage.setScene(resultScene);
        });
        JFXButton prevBtn = new JFXButton();
        prevBtn.setText("Назад");
        prevBtn.getStyleClass().add("button-raised");
        prevBtn.setLayoutX(230.0);
        prevBtn.setLayoutY(350.0);
        prevBtn.setOnAction(event -> {
            primaryStage.setScene(uploadScene);
        });
        JFXTextField userTextField = new JFXTextField();
        userTextField.setLayoutX(400.0);
        userTextField.setLayoutY(200.0);
        userTextField.setOnAction(event -> {
            if (inputFile != null) {
                Integer year = null;
                try {
                    year = Integer.valueOf(userTextField.getText());
                } catch (RuntimeException e) {
                    numberOfYearsText.setFill(Color.FIREBRICK);
                    numberOfYearsText.setText("Введите число!");
                }
                try {
                    data = readerService.readFile(inputFile, year);
                } catch (RuntimeException e) {
                    numberOfYearsText.setFill(Color.FIREBRICK);
                    numberOfYearsText.setText("В файле нет данных на указанное число дат");
                }
                values = analysisService.analyseReport(data);
                writerService.writeResultInConsole(values);
                numberOfYearsText.setFill(Color.DODGERBLUE);
                numberOfYearsText.setText("Выбранное количество дат \nуспешно проанализировано");
                nextBtn.setDisable(false);
            } else {
                numberOfYearsText.setFill(Color.FIREBRICK);
                numberOfYearsText.setText("Необходимо загрузить файл!");
            }
        });

        Pane numberPane = new Pane();
        numberPane.getChildren().add(numberMess);
        numberPane.getChildren().add(userTextField);
        numberPane.getChildren().add(numberOfYearsText);
        numberPane.getChildren().add(nextBtn);
        numberPane.getChildren().add(prevBtn);

        StackPane numberMainPane = new StackPane();
        numberMainPane.setStyle("-fx-background-color:WHITE");

        numberMainPane.getChildren().add(numberPane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(numberMainPane).getView());

        numberScene = new Scene(decorator, 1000, 500);
        numberScene.getStylesheets().add(this.getClass().getResource("/jfoenix-components.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {

        createUploadFileScene(primaryStage);
        createDataNumberScene(primaryStage);

        primaryStage.setScene(uploadScene);
        primaryStage.show();
    }
}
