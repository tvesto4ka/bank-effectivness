package ru.mipt.sbt;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
public class LauncherFX extends Application {

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
        Button uploadFile = new Button();
        uploadFile.setText("Загрузить финансовый отчет банка");
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
        TextField userTextField = new TextField();
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


        final Text reportInterfaceNorm = new Text();
        reportInterfaceNorm.setFont(font);
        final Text reportInterfaceNotNorm = new Text();
        reportInterfaceNotNorm.setFont(font);
        Button printInConsoleBtn = new Button();
        printInConsoleBtn.setText("Показать результаты анализа на экране");
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
        Button printInFileBtn = new Button();
        printInFileBtn.setText("Сохранить результаты анализа в файл");
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

        Button exitBtn = new Button();
        exitBtn.setText("Выйти из программы");
        exitBtn.setOnAction(event -> primaryStage.close());


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        grid.add(uploadFile, 0, 0);
        grid.add(uploadFileText, 0, 1);
        grid.add(numberOfYearsQ, 0, 2);
        grid.add(numberOfYearsText, 0, 3);
        grid.add(userTextField, 1, 2);
        grid.add(printInConsoleBtn, 2, 6);
        grid.add(reportInterfaceNorm, 2, 7);
        grid.add(reportInterfaceNotNorm, 2, 8);
        grid.add(printInFileBtn, 0, 6);
        grid.add(reportFile, 0, 7);
        grid.add(exitBtn, 2, 0);

        Scene scene = new Scene(grid, 1300, 600);

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}