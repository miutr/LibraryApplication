package ui.controller;

import java.util.UUID;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

//fx:controller="business.SystemController"
public class NewMemberScreenController extends Stage{
	@FXML
	private TextField firstName; 
	@FXML
	private TextField lastName;
	@FXML
	private TextField telephone;
	@FXML
	private TextField city;
	@FXML
	private TextField street;
	@FXML
	private TextField state;
	@FXML
	private TextField zip;
	@FXML
	private Button backButton;
	@FXML
	private Button submitButton;
	
	Alert infoAlert = new Alert(AlertType.INFORMATION);
	
	public void addNewMember(ActionEvent event) {
		Address newAddress= new Address(street.getText(),city.getText(),state.getText(),zip.getText());
		LibraryMember newMember=new LibraryMember( UUID.randomUUID().toString(),firstName.getText(),lastName.getText(), telephone.getText(),newAddress);
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(newMember);
		infoAlert.setContentText("Member added succesfully!");
		infoAlert.show();
		infoAlert.setOnCloseRequest( e -> {
	          if (infoAlert.getResult() == ButtonType.OK) {
	        	  backToMain(event);
	          }});
	}
	
	public void backToMain(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../MainScreen.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

}