package appController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.MyDataSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import myExceptions.FieldEmptyException;
import myExceptions.ServerException;

public class AnmeldungController {

	private AppController controller;
	private MyDataSet model;
	private Stage stage;
	private Scene BasicAppViewScene;

	public void setAppController(AppController controller) {
		this.controller = controller;
	}

	public void setModelListener(MyDataSet model) {
		this.model = model;

	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField username_lb;

	@FXML
	private TextField name_lb;

	@FXML
	private Button anmeldenBtn;

	@FXML
	void initialize() {
		assert username_lb != null : "fx:id=\"username_lb\" was not injected: check your FXML file 'StartView.fxml'.";
		assert name_lb != null : "fx:id=\"name_lb\" was not injected: check your FXML file 'StartView.fxml'.";
		assert anmeldenBtn != null : "fx:id=\"anmeldenBtn\" was not injected: check your FXML file 'StartView.fxml'.";

	}

	@FXML
	void anmeldenHandel(ActionEvent event) {
		try {
			prepareBasicView(event);
			controller.register(username_lb.getText(), name_lb.getText());
			changeToBasicView();
		} catch (ServerException | FieldEmptyException e) {
			showError(e.getMessage());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			showError("error : cannot connect to server.\nDetails " + e1.getMessage());
		}

	}

	public void showError(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fehler");
		alert.setHeaderText("Anmeldung konnte leider nicht erledigt werden");
		alert.setContentText(message);

		alert.show();

	}

	public void prepareBasicView(ActionEvent event) {

		try {
			FXMLLoader basiclod = new FXMLLoader(App.class.getResource("BasicAppView.fxml"));

			Parent root = basiclod.load();

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			BasicAppViewController basicController = basiclod.getController();
			basicController.setModelListener(model);
			basicController.setAppController(controller);

			BasicAppViewScene = new Scene(root);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("prepared");

	}

	public void changeToBasicView() {

		stage.setScene(BasicAppViewScene);
		stage.show();
		System.out.println("changed");
	}

}
