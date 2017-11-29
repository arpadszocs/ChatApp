package app.ui.login;

import app.client.Client;
import app.model.User;
import app.ui.main.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Szocs, Arpad (EXTERN: msg DAVID)
 * @version $Id: LoginController.java 31604 11/28/2017 11:33 Szocs, Arpad (EXTERN: msg DAVID) $
 * @since 11/28/2017
 */
public class LoginController implements Initializable {

	private Stage stage;

	private ExecutorService executorService = Executors.newWorkStealingPool();

	@FXML
	private TextField addressField;
	@FXML
	private TextField usernameField;
	@FXML
	private Button loginButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.setOnAction(event -> loadApp());
		loginButton.disableProperty().bind(usernameField.textProperty().isEmpty());
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void loadApp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/ui/main/View.fxml"));
			BorderPane pane = loader.load();
			Scene scene = new Scene(pane, 800, 600);
			stage.setScene(scene);
			startClient(loader.getController());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void startClient(Controller controller) {
		executorService.execute(() -> {
			Client client = new Client();
			controller.setListener(client);
			controller.setUser(new User(Client.getUserId(), usernameField.getText()));
			client.start(new InetSocketAddress(getAddress(), getPort()));
			controller.startReceiver();
		});
	}

	// TODO add StringUtils as maven dependency
	private Integer getPort() {
		return Integer.valueOf(addressField.getText()
				.substring(addressField.getText().indexOf(":") + 1, addressField.getText().length()));
	}

	private String getAddress() {
		return addressField.getText().substring(0, addressField.getText().indexOf(":"));
	}
}
