package ui;
import java.util.List;
import java.util.UUID;

import business.Address;
import business.LibraryMember;
import business.LoginException;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
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
import javafx.stage.Window;

public class MainScreenController extends Stage{


	@FXML
	private Button submitButton;

	Alert al = new Alert(AlertType.INFORMATION);

	public void addNewMember(ActionEvent event) {

		try {

			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.hide();
			Parent root = FXMLLoader.load(getClass().getResource("NewMemberScreen.fxml"));
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			setTitle("Adding New Member Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	public void addNewBook(ActionEvent event) {


		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.hide();
			Parent root = FXMLLoader.load(getClass().getResource("AddNewBookScreen.fxml"));
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			setTitle("Adding New Member Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}



}


