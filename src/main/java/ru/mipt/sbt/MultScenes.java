package ru.mipt.sbt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import javafx.application.Application;
import javafx.scene.Scene;
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

    private final String STYLE = this.getClass().getResource("/jfoenix-components.css").toExternalForm();

    private Map<Norms, List<Value>> values;
    private List<BankReportInfo> data;
    private WriterService writerService = new WriterService();
    private ReaderService readerService = new ReaderService();
    private AnalysisService analysisService = new AnalysisService();
    private File inputFile;
    private File outputDirectory;
    private String resultNorm;
    private String resultNotNorm;

    private Scene numberScene;
    private Scene uploadScene;
    private Scene resultScene;

    private Font font = Font.font("Tahoma", FontWeight.NORMAL, 14);
    private Font headerFont = Font.font("Tahoma", FontWeight.NORMAL, 18);

    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final Text reportInterfaceNorm = new Text();
    private final Text reportInterfaceNotNorm = new Text();

    private JFXButton createButton(String name, double x, double y) {
        JFXButton btn = new JFXButton();
        btn.setText(name);
        btn.getStyleClass().add("button-raised");
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        return btn;
    }

    private Text createText(String name, double x, double y, Font font) {
        Text text = new Text();
        text.setFont(font);
        text.setText(name);
        text.setLayoutX(x);
        text.setLayoutY(y);
        return text;
    }

    private Scene createScene(Pane mainPane, Stage primaryStage) {
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color:WHITE");

        stackPane.getChildren().add(mainPane);
        JFXDecorator decorator = new JFXDecorator(primaryStage, new DefaultFlowContainer(stackPane).getView());

        Scene scene = new Scene(decorator, 1000, 500);
        scene.getStylesheets().add(STYLE);
        return scene;
    }

    private void createUploadFileScene(Stage primaryStage) {
        final Text welcomeMess = createText("Пожалуйста, выберете файл с финансовым отчетом банка", 280, 180, headerFont);
        final Text infoMess = createText(null, 420, 270, font);
        JFXButton nextBtn = createButton("Далее", 400.0, 350.0);
        nextBtn.setDisable(true);
        nextBtn.setOnAction(event -> primaryStage.setScene(numberScene));
        JFXButton uploadFile = createButton("Загрузить файл", 400.0, 200.0);
        uploadFile.setOnAction(event -> {
            inputFile = fileChooser.showOpenDialog(primaryStage);
            if (inputFile != null && inputFile.getAbsolutePath().split("\\.")[1].equalsIgnoreCase("xlsx")) {
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
                infoMess.setText("Это не .xlsx файл!");
            }
        });
        Pane mainPane = new Pane();
        mainPane.getChildren().add(welcomeMess);
        mainPane.getChildren().add(uploadFile);
        mainPane.getChildren().add(infoMess);
        mainPane.getChildren().add(nextBtn);

        uploadScene = createScene(mainPane, primaryStage);
    }

    private void createDataNumberScene(Stage primaryStage) {
        final Text welcomeMess = createText("Пожалуйста, введите количество дат для анализа", 280, 180, headerFont);
        final Text infoMess = createText(null, 380, 270, font);
        JFXButton nextBtn = createButton("Посмотреть результаты", 530.0, 350.0);
        nextBtn.setDisable(true);
        nextBtn.setOnAction(event -> {
            resultNorm = writerService.writeNormalResultInInterface(values);
            resultNotNorm = writerService.writeNotNormalResultInInterface(values);
            reportInterfaceNorm.setFill(Color.FORESTGREEN);
            reportInterfaceNorm.setText(resultNorm);
            reportInterfaceNotNorm.setFill(Color.FIREBRICK);
            reportInterfaceNotNorm.setText(resultNotNorm);
            primaryStage.setScene(resultScene);
        });
        JFXButton prevBtn = createButton("Начать сначала", 230.0, 350.0);
        prevBtn.setOnAction(event -> {
            inputFile = null;
            primaryStage.setScene(uploadScene);
        });
        JFXTextField userTextField = new JFXTextField();
        userTextField.setLayoutX(400.0);
        userTextField.setLayoutY(200.0);
        userTextField.setOnAction(event -> {
            if (inputFile != null) {
                Integer year;
                try {
                    year = Integer.valueOf(userTextField.getText());
                    try {
                        data = readerService.readFile(inputFile, year);
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

        numberScene = createScene(mainPane, primaryStage);
    }

    private void createResultScene(Stage primaryStage) {
        JFXButton nextBtn = createButton("Начать сначала", 650.0, 350.0);
        nextBtn.setOnAction(event -> {
            inputFile = null;
            primaryStage.setScene(uploadScene);
        });

        reportInterfaceNorm.setFont(font);
        reportInterfaceNorm.setLayoutX(200.0);
        reportInterfaceNorm.setLayoutY(40.0);
        reportInterfaceNotNorm.setFont(font);
        reportInterfaceNotNorm.setLayoutX(200.0);
        reportInterfaceNotNorm.setLayoutY(200.0);

        final Text reportFile = createText(null, 400, 410, font);
        JFXButton printInFileBtn = createButton("Сохранить в файл", 400.0, 350.0);
        printInFileBtn.setOnAction(event -> {
            if (values != null) {
                outputDirectory = directoryChooser.showDialog(primaryStage);
                File outputFile = new File(outputDirectory, "результаты анализа.xlsx");
                writerService.writeResultInFile(values, outputFile);
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

        resultScene = createScene(mainPane, primaryStage);
    }

    public static void main(String[] args) {
        launch((String) null);
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
