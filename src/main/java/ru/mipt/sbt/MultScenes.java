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
        final Text welcomeMess = new Text();
        welcomeMess.setFont(headerFont);
        welcomeMess.setText("Пожалуйста, выберете файл с финансовым отчетом банка");
        welcomeMess.setLayoutX(280);
        welcomeMess.setLayoutY(180.0);
        final Text infoMess = new Text();
        infoMess.setFont(font);
        infoMess.setLayoutX(420);
        infoMess.setLayoutY(270);
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
                infoMess.setFill(Color.DODGERBLUE);
                infoMess.setText("Файл успешно загружен");
                nextBtn.setDisable(false);
            } else {
                infoMess.setFill(Color.FIREBRICK);
                infoMess.setText("Это не .xlsx файл!");
            }
        });
        Pane mainPane = new Pane();
        mainPane.getChildren().add(welcomeMess);
        mainPane.getChildren().add(uploadFile);
        mainPane.getChildren().add(infoMess);
        mainPane.getChildren().add(nextBtn);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:WHITE");

        stackPane.getChildren().add(mainPane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(stackPane).getView());

        uploadScene = new Scene(decorator, 1000, 500);
        uploadScene.getStylesheets().add(this.getClass().getResource("/jfoenix-components.css").toExternalForm());
    }

    private void createDataNumberScene(Stage primaryStage) {
        final Text welcomeMess = new Text();
        welcomeMess.setFont(headerFont);
        welcomeMess.setText("Пожалуйста, введите количество дат для анализа");
        welcomeMess.setLayoutX(280);
        welcomeMess.setLayoutY(180.0);
        final Text infoMess = new Text();
        infoMess.setFont(font);
        infoMess.setLayoutX(380);
        infoMess.setLayoutY(270);
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
                    infoMess.setFill(Color.FIREBRICK);
                    infoMess.setText("Введите число!");
                }
                try {
                    data = readerService.readFile(inputFile, year);
                } catch (RuntimeException e) {
                    infoMess.setFill(Color.FIREBRICK);
                    infoMess.setText("В файле нет данных на указанное число дат");
                }
                values = analysisService.analyseReport(data);
                writerService.writeResultInConsole(values);
                infoMess.setFill(Color.DODGERBLUE);
                infoMess.setText("Выбранное количество дат \nуспешно проанализировано");
                nextBtn.setDisable(false);
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

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:WHITE");

        stackPane.getChildren().add(mainPane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(stackPane).getView());

        numberScene = new Scene(decorator, 1000, 500);
        numberScene.getStylesheets().add(this.getClass().getResource("/jfoenix-components.css").toExternalForm());
    }
    
    private void createResultScene(Stage primaryStage) {
        final Text welcomeMess = new Text();
        welcomeMess.setFont(headerFont);
        welcomeMess.setText("Пожалуйста, выберете способ получения результатов анализа");
        welcomeMess.setLayoutX(220);
        welcomeMess.setLayoutY(50.0);
        final Text infoMess = new Text();
        infoMess.setFont(font);
        infoMess.setLayoutX(380);
        infoMess.setLayoutY(270);
        JFXButton nextBtn = new JFXButton();
        nextBtn.setText("Начать сначала");
        nextBtn.getStyleClass().add("button-raised");
        nextBtn.setLayoutX(530.0);
        nextBtn.setLayoutY(350.0);
        nextBtn.setOnAction(event -> {
            primaryStage.setScene(uploadScene);
        });
        JFXButton prevBtn = new JFXButton();
        prevBtn.setText("Назад");
        prevBtn.getStyleClass().add("button-raised");
        prevBtn.setLayoutX(230.0);
        prevBtn.setLayoutY(350.0);
        prevBtn.setOnAction(event -> {
            primaryStage.setScene(numberScene);
        });

        final Text reportInterfaceNorm = new Text();
        reportInterfaceNorm.setFont(font);
        reportInterfaceNorm.setLayoutX(530.0);
        reportInterfaceNorm.setLayoutY(120.0);
        final Text reportInterfaceNotNorm = new Text();
        reportInterfaceNotNorm.setFont(font);
        reportInterfaceNotNorm.setLayoutX(530.0);
        reportInterfaceNotNorm.setLayoutY(200.0);
        JFXButton printInConsoleBtn = new JFXButton();
        printInConsoleBtn.setText("Показать результаты анализа на экране");
        printInConsoleBtn.setLayoutX(530.0);
        printInConsoleBtn.setLayoutY(70.0);
        printInConsoleBtn.getStyleClass().add("button-raised");
        printInConsoleBtn.setOnAction(event -> {
            if (values != null) {
                String resultNorm = writerService.writeNormalResultInInterface(values);
                String resultNotNorm = writerService.writeNotNormalResultInInterface(values);
                reportInterfaceNorm.setFill(Color.FORESTGREEN);
                reportInterfaceNorm.setText(resultNorm);
                reportInterfaceNotNorm.setFill(Color.FIREBRICK);
                reportInterfaceNotNorm.setText(resultNotNorm);
            } else {
                reportInterfaceNorm.setFill(Color.FIREBRICK);
                reportInterfaceNorm.setText("Необходимо загрузить файл!");
            }
        });

        final Text reportFile = new Text();
        reportFile.setFont(font);
        reportFile.setLayoutX(230.0);
        reportFile.setLayoutY(150.0);
        JFXButton printInFileBtn = new JFXButton();
        printInFileBtn.setText("Сохранить результаты анализа в файл");
        printInFileBtn.setLayoutX(230.0);
        printInFileBtn.setLayoutY(70.0);
        printInFileBtn.getStyleClass().add("button-raised");
        printInFileBtn.setOnAction(event -> {
            if (values != null) {
                outputDirectory = directoryChooser.showDialog(primaryStage);
                File outputFile = new File(outputDirectory, "результаты анализа.xlsx");
                writerService.writeResultInFile(values, outputFile);
                reportFile.setFill(Color.DODGERBLUE);
                reportFile.setText("Анализ показателей стабильности\nсохранен в файле\n"
                        + outputFile.getName()
                );
            } else {
                reportFile.setFill(Color.FIREBRICK);
                reportFile.setText("Необходимо загрузить файл!");
            }
        });

        Pane mainPane = new Pane();
        mainPane.getChildren().add(welcomeMess);
        mainPane.getChildren().add(printInConsoleBtn);
        mainPane.getChildren().add(reportInterfaceNorm);
        mainPane.getChildren().add(reportInterfaceNotNorm);
        mainPane.getChildren().add(printInFileBtn);
        mainPane.getChildren().add(reportFile);
        mainPane.getChildren().add(infoMess);
        mainPane.getChildren().add(nextBtn);
        mainPane.getChildren().add(prevBtn);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:WHITE");

        stackPane.getChildren().add(mainPane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(stackPane).getView());

        resultScene = new Scene(decorator, 1000, 500);
        resultScene.getStylesheets().add(this.getClass().getResource("/jfoenix-components.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {

        createUploadFileScene(primaryStage);
        createDataNumberScene(primaryStage);
        createResultScene(primaryStage);

        primaryStage.setScene(uploadScene);
        primaryStage.show();
    }
}
