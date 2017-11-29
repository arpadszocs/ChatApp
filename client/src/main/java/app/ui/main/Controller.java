package app.ui.main;

import app.MessageListener;
import app.model.Message;
import app.model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author Szocs, Arpad (EXTERN: msg DAVID)
 * @version $Id: Controller.java 31604 11/27/2017 13:56 Szocs, Arpad (EXTERN: msg DAVID) $
 * @since 11/27/2017
 */
public class Controller implements Initializable {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private MessageListener listener;
	private User user;

	@FXML
	private Label label;
	@FXML
	private BorderPane content;
	@FXML
	private TextField testField;
	@FXML
	private Button send;
	@FXML
	private TextArea textArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTopComponents();
		send.setOnAction(event -> {
			listener.send(new Message(1L, user, LocalDateTime.now(), testField.getText()));
			testField.clear();
		});
	}

	public void setListener(MessageListener listener) {
		this.listener = listener;
	}

	public void startReceiver() {
		receiverTask().play();
	}

	public void setUser(User user) {
		this.user = user;
	}

	private Timeline receiverTask() {
		if (listener == null) {
			throw new IllegalStateException();
		}
		Timeline receive = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			if (listener.ready()) {
				Message message = listener.receive();
				if (message != null) {
					textArea.appendText(composeLine(message) + "\n");
				}
			}
			listener.ping();
		}));
		receive.setCycleCount(Timeline.INDEFINITE);
		return receive;
	}

	private void initTopComponents() {
		testField.prefWidthProperty()
				.bind(content.widthProperty().subtract(label.widthProperty()).subtract(send.widthProperty()));
	}

	private String composeLine(Message message) {
		return message.getDate().format(formatter) + ": " + message.getUser().getName() + ": " + message.getMessage();
	}
}
