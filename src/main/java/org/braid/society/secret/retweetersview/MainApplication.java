package org.braid.society.secret.retweetersview;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/main.fxml"));
    Scene mainAppScene = new Scene(fxmlLoader.load());
    mainAppScene.getStylesheets().add(getClass().getResource("style/main.css").toExternalForm());
    stage.setTitle("Hello!");
    stage.setScene(mainAppScene);
    stage.show();
  }

}