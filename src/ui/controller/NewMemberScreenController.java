package ui.controller;

import java.util.UUID;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
	
	public boolean addNewMember(ActionEvent event) {
		if(firstName.getText().equals("") || lastName.getText().equals("") || 
				telephone.getText().equals("") || street.getText().equals("") || 
				city.getText().equals("") || state.getText().equals("") || zip.getText().equals("")) {
			infoAlert.setContentText("Please fill all fields!");
			infoAlert.show();
			return false;
		} 
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
		return true;
	}
	
	public void backToMain(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			MainScreenController msc = new MainScreenController();
			msc.showMainScreen();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

}
