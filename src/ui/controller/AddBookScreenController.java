package ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import business.Address;
import business.Author;
import business.Book;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AddBookScreenController extends Stage {
	public static String lastAddedISBN;
	
	@FXML
	private TextField bookISBN;
	@FXML
	private TextField bookTitle;
	@FXML
	private RadioButton bookSeven;
	@FXML
	private RadioButton bookTwentyOne;
	@FXML
	private TextField bookCopies;
	@FXML
	private TextField authorName; 
	@FXML
	private TextField authorLastName;
	@FXML
	private TextField authorPhone;
	@FXML
	private TextField authorCity;
	@FXML
	private TextField authorStreet;
	@FXML
	private TextField authorState;
	@FXML
	private TextField authorZip;
	@FXML
	private TextArea authorBio;
	
	Alert infoAlert = new Alert(AlertType.INFORMATION);
	
	public boolean addNewBook(ActionEvent event) {
		if(bookISBN.getText().equals("") || bookTitle.getText().equals("")) {
			infoAlert.setContentText("Please fill all fields!");
			infoAlert.show();
			return false;
		}
		DataAccessFacade da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		int checkOutMaxLen = bookSeven.isSelected() ? 7:21;
		Book newBook= new Book(bookISBN.getText(), bookTitle.getText(), checkOutMaxLen, new ArrayList<>());
		books.put(newBook.getIsbn(), newBook);
		da.saveBooks(books);
		lastAddedISBN = bookISBN.getText();
		try {
			AddBookCopyController copyController = new AddBookCopyController();
			int numOfCopies = Integer.parseInt(bookCopies.getText());
			copyController.addCopiesToBookAndSave(newBook.getIsbn(), numOfCopies-1);
			return true;
		} catch (NumberFormatException e) {
			infoAlert.setContentText("Please enter proper copy number!");
			infoAlert.show();
			return false;
		}
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
	
	public void saveAndgoToAuthorScreen(ActionEvent event) {
		try {
			if(addNewBook(event)) {
				Node node = (Node) event.getSource();
				Stage thisStage = (Stage) node.getScene().getWindow();
				thisStage.close();
				Parent root = FXMLLoader.load(getClass().getResource("../AddAuthorScreen.fxml"));
				Scene scene = new Scene(root);
				setScene(scene);
				setTitle("Add Author");
				show();
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void saveAndAddAnotherAuthor(ActionEvent event) {
		try {
			if(saveAuthor()) {
				Node node = (Node) event.getSource();
				Stage thisStage = (Stage) node.getScene().getWindow();
				thisStage.close();
				Parent root = FXMLLoader.load(getClass().getResource("../AddAuthorScreen.fxml"));
				Scene scene = new Scene(root);
				setScene(scene);
				setTitle("Add Author");
				show();
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public boolean saveAuthor() {
		if(authorName.getText().equals("") || authorLastName.getText().equals("") || 
				authorPhone.getText().equals("") || authorStreet.getText().equals("") || 
				authorCity.getText().equals("") || authorState.getText().equals("") || authorZip.getText().equals("")) {
			infoAlert.setContentText("Please fill all fields!");
			infoAlert.show();
			return false;
		} 
		Address newAddress = new Address(authorStreet.getText(), authorCity.getText(), authorState.getText(), authorZip.getText());
		Author newAuthor = new Author(authorName.getText(), authorLastName.getText(),authorPhone.getText(),newAddress,authorBio.getText());
		DataAccessFacade da = new DataAccessFacade();
		String ISBNvalue = lastAddedISBN;
		
		HashMap<String, Book> books = da.readBooksMap();
		
		Book founded = null;
		for (Map.Entry<String, Book> entry : books.entrySet()) {
			String key = entry.getKey();
			Book val = entry.getValue();
			
			if(key.equals(ISBNvalue)) {
				founded = val;
			}
		}
		founded.getAuthors().add(newAuthor);
		books.put(founded.getIsbn(), founded);
		da.saveBooks(books);
		return true;
	}
	
	
	public void saveAuthorEvent(ActionEvent event) {
		try {
			if(saveAuthor()) {
				infoAlert.setContentText("Book and authors added succesfully!");
				infoAlert.show();
				infoAlert.setOnCloseRequest( e -> {
				          if (infoAlert.getResult() == ButtonType.OK) {
				        	  backToMain(event);
				          }});
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
	}
}
