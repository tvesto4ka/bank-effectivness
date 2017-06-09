package ru.mipt.sbt;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.mipt.sbt.scenes.NumberScene;
import ru.mipt.sbt.scenes.ResultScene;
import ru.mipt.sbt.scenes.UploadFileScene;

/**
 * Created by Toma on 28.05.2017.
 */
public class MultScenes extends Application {
    public static void main(String[] args) {
        launch((String) null);
    }

    @Override
    public void start(Stage primaryStage) {
        UploadFileScene uploadScene = new UploadFileScene();
        NumberScene numberScene = new NumberScene();
        ResultScene resultScene = new ResultScene();

        uploadScene.setNextScene(numberScene);
        numberScene.setNextScene(resultScene);
        numberScene.setPrevScene(uploadScene);
        resultScene.setNextScene(uploadScene);

        uploadScene.createUploadFileScene(primaryStage);
        numberScene.createDataNumberScene(primaryStage);
        resultScene.createResultScene(primaryStage);

        primaryStage.setScene(uploadScene.getScene());
        primaryStage.show();
    }
}
