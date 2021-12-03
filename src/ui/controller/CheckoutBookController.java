package ui.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import business.Book;
import business.BookCopy;
import business.CheckoutEntry;
import business.LibraryMember;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckoutBookController extends Stage {
	@FXML
	private TextField coID;
	@FXML
	private TextField coISBN;

	Alert alertError = new Alert(AlertType.ERROR);
	Alert alertSuccess = new Alert(AlertType.INFORMATION);
	
	public void checkoutBook(ActionEvent event) {
		String id = coID.getText();
		String ISBN = coISBN.getText();
		DataAccessFacade da = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = da.readMemberMap();
		HashMap<String, Book> books = da.readBooksMap();
		Book foundedBook = null;
		
		if(id.isEmpty() || ISBN.isEmpty()) {
			alertError.setContentText("Fields need to be filled!");
		}
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
		}
		if(checkAvailabilityAndSet(foundedBook) != null) {
			books.put(foundedBook.getIsbn(), foundedBook);
			isFound = false;
			
			LibraryMember foundedMember = null;
			for (Entry<String, LibraryMember> entry : mems.entrySet()) {
				String memID = entry.getKey();
				LibraryMember member =entry.getValue();
				if(memID.equals(id)) {
					foundedMember = member;
					isFound = true;
					break;
				}
			}
			if(!isFound) {
				alertError.setContentText("Member ID cannot be found!");
			}
			
			CheckoutEntry ce = new CheckoutEntry(new Date(),calculateDueDate(foundedBook.getMaxCheckoutLength(),new Date()));
			foundedMember.getCheckoutRecord().getCheckoutEntries().add(ce);
			mems.put(foundedMember.getMemberId(), foundedMember);
			
			da.saveMembers(mems);
			da.saveBooks(books);
			alertSuccess.setContentText("Checkout completed successfully!");
			alertSuccess.show();
			alertSuccess.setOnCloseRequest( e -> {
		          if (alertSuccess.getResult() == ButtonType.OK) {
		        	  backToMain(event);
		          }});	
		}
		
	}

	public Date calculateDueDate(int dateLong , Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, dateLong);
		return c.getTime();

	}
	
	public BookCopy checkAvailabilityAndSet(Book book) {	
		BookCopy nextAvailable = book.getNextAvailableCopy();
		
		if(nextAvailable == null) {
			alertError.setContentText("Book copy does not available!");
			alertError.show();
		} else {
			nextAvailable.changeAvailability();
		}
		return nextAvailable;
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
