package ui.controller;

import java.util.HashMap;
import java.util.Map.Entry;

import business.Book;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
				
		if(addCopiesToBookAndSave(ISBN, bookCopies)) {
			alertSuccess.setContentText("Book copies added succesfully!");
			alertSuccess.show();
			alertSuccess.setOnCloseRequest( e -> {
			          if (alertSuccess.getResult() == ButtonType.OK) {
			        	  backToMain(event);
			          }});
		} else {
			alertError.setContentText("ISBN cannot be found!");
			alertError.show();
		}	
	}
	
	public boolean addCopiesToBookAndSave(String ISBN, int copies) {
		DataAccessFacade da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		boolean isFound = false;
		Book foundedBook = null;
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
			return false;
		} else {
			for (int i = 0; i < copies; i++) {
				foundedBook.addCopy();
			}
			books.put(foundedBook.getIsbn(), foundedBook);
			da.saveBooks(books);
			return true;
		}
	}
	
	public void backToMain(ActionEvent event) {
		try {
			Node node = (Node) event.getSource();
			Stage thisStage = (Stage) node.getScene().getWindow();
			thisStage.hide();
			MainScreenController msc = new MainScreenController();
			msc.showMainScreen();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
 	
}
