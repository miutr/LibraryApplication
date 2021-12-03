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
	
	public void addNewBook(ActionEvent event) {
		int checkOutMaxLen = bookSeven.isArmed() ? 7:21;
		Book newBook= new Book(bookISBN.getText(),bookTitle.getText(),checkOutMaxLen,new ArrayList<>());
		lastAddedISBN = bookISBN.getText();
		DataAccessFacade da = new DataAccessFacade();
		int numOfCopies = Integer.parseInt(bookCopies.getText());
		for (int i = 0; i < numOfCopies; i++) {
			newBook.addCopy();
		}
		da.saveNewBook(newBook);
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
	
	public void saveAndgoToAuthorScreen(ActionEvent event) {
		try {
			addNewBook(event);
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../AddAuthorScreen.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void saveAndAddAnotherAuthor(ActionEvent event) {
		try {
			saveAuthor();
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("../AddAuthorScreen.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void saveAuthor() {
		Address newAddress = new Address(authorStreet.getText(), authorCity.getText(), authorState.getText(), authorZip.getText());
		Author newAuthor = new Author(authorName.getText(), authorLastName.getText(),authorPhone.getText(),newAddress,authorBio.getText());
		DataAccessFacade da = new DataAccessFacade();
		String ISBNvalue = lastAddedISBN;
		
		HashMap<String, Book> books = da.readBooksMap();
		HashMap<String, Book> bookCopy = new HashMap<>();
		
		for (Map.Entry<String, Book> entry : books.entrySet()) {
			String key = entry.getKey();
			Book val = entry.getValue();
			
			if(key.equals(ISBNvalue)) {
				ArrayList<Author> tmp = new ArrayList<>();
				for (Author author : val.getAuthors()) {
					tmp.add(author);
				}
				tmp.add(newAuthor);
				Book b = new Book(val.getIsbn(), val.getTitle(), val.getMaxCheckoutLength(), tmp);
				bookCopy.put(ISBNvalue,b);
			} else {
				bookCopy.put(val.getIsbn(), val);
			}
		}
		da.saveBooks(bookCopy);
	}
	
	
	public void saveAuthorEvent(ActionEvent event) {
		try {
			saveAuthor();
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
