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
import javafx.scene.layout.GridPane;
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
public class LauncherFoenix extends Application {

    private Map<Norms, List<Value>> values;
    private List<BankReportInfo> data;
    private WriterService writerService = new WriterService();
    private ReaderService readerService = new ReaderService();
    private AnalysisService analysisService = new AnalysisService();
    private File inputFile;
    private File outputDirectory;

    public static void main(String[] args) {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {

        Font font = Font.font("Tahoma", FontWeight.NORMAL, 14);

        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final Text uploadFileText = new Text();
        uploadFileText.setFont(font);
        JFXButton uploadFile = new JFXButton();
        uploadFile.setText("Загрузить финансовый отчет банка");
        uploadFile.getStyleClass().add("button-raised");
        uploadFile.setOnAction(event -> {
            inputFile = fileChooser.showOpenDialog(primaryStage);
            if (inputFile != null && inputFile.getAbsolutePath().split("\\.")[1].equalsIgnoreCase("xlsx")) {
                uploadFileText.setFill(Color.DODGERBLUE);
                uploadFileText.setText("Файл успешно загружен");
            } else {
                uploadFileText.setFill(Color.FIREBRICK);
                uploadFileText.setText("Это не .xlsx файл!");
            }
        });

        final Text numberOfYearsText = new Text();
        numberOfYearsText.setFont(font);
        Label numberOfYearsQ = new Label("Сколько дат нужно проанализировать?");
        numberOfYearsQ.setFont(font);
        JFXTextField userTextField = new JFXTextField();
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
            } else {
                numberOfYearsText.setFill(Color.FIREBRICK);
                numberOfYearsText.setText("Необходимо загрузить файл!");
            }
        });

        /*RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        userTextField.getValidators().add(validator);
        userTextField.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) userTextField.validate();
        });*/


        final Text reportInterfaceNorm = new Text();
        reportInterfaceNorm.setFont(font);
        final Text reportInterfaceNotNorm = new Text();
        reportInterfaceNotNorm.setFont(font);
        JFXButton printInConsoleBtn = new JFXButton();
        printInConsoleBtn.setText("Показать результаты анализа на экране");
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
        JFXButton printInFileBtn = new JFXButton();
        printInFileBtn.setText("Сохранить результаты анализа в файл");
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

        JFXButton exitBtn = new JFXButton();
        exitBtn.setText("Выйти из программы");
        exitBtn.getStyleClass().add("button-raised");
        exitBtn.setCancelButton(true);
        exitBtn.setOnAction(event -> primaryStage.close());


        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));


        pane.add(uploadFile, 0, 0);
        pane.add(uploadFileText, 0, 1);

        pane.add(numberOfYearsQ, 0, 2);
        pane.add(numberOfYearsText, 0, 3);
        pane.add(userTextField, 1, 2);

        pane.add(printInConsoleBtn, 2, 4);
        pane.add(reportInterfaceNorm, 2, 5);
        pane.add(reportInterfaceNotNorm, 2, 6);

        pane.add(printInFileBtn, 0, 4);
        pane.add(reportFile, 0, 5);

        pane.add(exitBtn, 2, 0);

        pane.setStyle("-fx-background-color:WHITE");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(pane);

        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(stackPane).getView());

        Scene scene = new Scene(decorator, 1300, 600);
        scene.getStylesheets().add(this.getClass().getResource("/jfoenix-components.css").toExternalForm());

        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
