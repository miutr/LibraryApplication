package ui.controller;
import business.SystemController;
import dataaccess.Auth;
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
import ui.Start;

public class MainScreenController extends Stage{

	@FXML
	private Button submitButton;
	@FXML
	private Button addMemberButton;
	@FXML
	private Button addBookButton;
	@FXML
	private Button checkoutButton;
	@FXML
	private Button addBookCopyButton;

	Alert al = new Alert(AlertType.INFORMATION);

	public void showMainScreen() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../MainScreen.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			setTitle("Main Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
		Auth auth = SystemController.currentAuth;
		switch (auth) {
		case ADMIN:
			checkoutButton.setDisable(true);
			break;
		case LIBRARIAN:
			addBookCopyButton.setDisable(true);
			addBookButton.setDisable(true);
			addMemberButton.setDisable(true);
			break;
		case BOTH:
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + auth);
		}
		
	}

	public void addNewMember(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../NewMemberScreen.fxml"));
			Scene scene = new Scene(root);
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
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../AddNewBookScreen.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			setTitle("Adding New Book Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	public void showAddBookCopyScreen(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../AddBookCopy.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			setTitle("Add Book Copy Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	public void checkoutBook(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../CheckoutBook.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			setTitle("Book Checkout Window");
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	public void backToMain(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Start.primStage().show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

}


