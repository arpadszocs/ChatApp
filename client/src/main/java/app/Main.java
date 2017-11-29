package app;

import app.ui.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/login/Login.fxml"));
		BorderPane content = loader.load();
		((LoginController) loader.getController()).setStage(primaryStage);
		primaryStage.setScene(new Scene(content, 300, 600));
		primaryStage.show();
	}
}
