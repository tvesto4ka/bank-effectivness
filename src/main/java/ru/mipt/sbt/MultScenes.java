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
        UploadFileScene uploadScene = new UploadFileScene(primaryStage);
        NumberScene numberScene = new NumberScene(primaryStage);
        ResultScene resultScene = new ResultScene(primaryStage);

        uploadScene.setNextScene(numberScene);
        numberScene.setPrevScene(uploadScene);
        numberScene.setNextScene(resultScene);
        resultScene.setPreviousScene(numberScene);
        resultScene.setNextScene(uploadScene);

        primaryStage.setScene(uploadScene.getScene());
        primaryStage.show();
    }
}
