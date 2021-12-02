package ui;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

	public void addNewMember(ActionEvent event) {
		Address newAddress= new Address(street.getText(),city.getText(),state.getText(),zip.getText());
		LibraryMember newMember=new LibraryMember( UUID.randomUUID().toString(),firstName.getText(),lastName.getText(), telephone.getText(),newAddress);
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(newMember);


	}
	public void backToMain(ActionEvent event) {

		try {

			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.hide();
			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

}
