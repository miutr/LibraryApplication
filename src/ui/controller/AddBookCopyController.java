package ui.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import business.Book;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddBookCopyController extends Stage{
	@FXML
	private TextField copyISBN;
	
	@FXML
	private TextField copyNumbers;
	
	Alert alertError = new Alert(AlertType.ERROR);
	Alert alertSuccess = new Alert(AlertType.INFORMATION);
	
	public void addBookCopy(ActionEvent event) {
		String ISBN = copyISBN.getText();
		Integer bookCopies = Integer.parseInt(copyNumbers.getText());
		
		DataAccessFacade da = new DataAccessFacade();
		
		HashMap<String, Book> books = da.readBooksMap();
		Book foundedBook = null;
		
		if(ISBN.isEmpty()) {
			alertError.setContentText("ISBN needs to be filled!");
		} else {
			boolean isFound = false;
			for (Entry<String, Book> entry : books.entrySet()) {
				String bookISBN = entry.getKey();
				Book book = entry.getValue();
				if(bookISBN.equals(ISBN)) {
					isFound = true;
					foundedBook = book;
					break;
				}
			}
			
			if(!isFound) {
				alertError.setContentText("ISBN cannot be found!");
				alertError.show();
			} else {
				for (int i = 0; i < bookCopies; i++) {
					foundedBook.addCopy();
				}
				books.put(foundedBook.getIsbn(), foundedBook);
				da.saveBooks(books);
				alertSuccess.setContentText("Book copies added succesfully!");
				alertSuccess.show();
				alertSuccess.setOnCloseRequest( e -> {
				          if (alertSuccess.getResult() == ButtonType.OK) {
				        	  backToMain(event);
				          }});
			}
		}
	}
	
	public void backToMain(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.hide();
			Parent root = FXMLLoader.load(getClass().getResource("../MainScreen.fxml"));
			Scene scene = new Scene(root);
			setScene(scene);
			show();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
 	
}
