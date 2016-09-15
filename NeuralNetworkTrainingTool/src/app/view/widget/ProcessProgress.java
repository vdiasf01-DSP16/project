package app.view.widget;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProcessProgress extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL processProgress = getClass().getResource("ProcessProgress.fxml");
		Parent root = FXMLLoader.load(processProgress);
		Scene scene = new Scene(root, 500, 70);
	    primaryStage.setScene(scene);
	    primaryStage.setResizable(false);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
