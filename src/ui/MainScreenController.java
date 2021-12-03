package ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
	public void checkoutBook(ActionEvent event) {


		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.hide();
			Parent root = FXMLLoader.load(getClass().getResource("CheckoutBook.fxml"));
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			setScene(scene);
			setTitle("Book Checkouting Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}



}


