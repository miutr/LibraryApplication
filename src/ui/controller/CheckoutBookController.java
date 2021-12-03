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
import javafx.scene.Node;
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
	@FXML
	private TextField memberId;

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
		BookCopy givenBook = checkAvailabilityAndSet(foundedBook);
		if(givenBook != null) {
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
			
			CheckoutEntry ce = new CheckoutEntry(givenBook, new Date(),calculateDueDate(foundedBook.getMaxCheckoutLength(),new Date()));
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
	
	public void printCheckoutDetails(ActionEvent event) {
		String memId = memberId.getText();
		DataAccessFacade daf = new DataAccessFacade();
		HashMap<String, LibraryMember> members = daf.readMemberMap();
		for (Entry<String, LibraryMember> entry : members.entrySet()) {
			String entryId = entry.getKey();
			LibraryMember member =entry.getValue();
			if(entryId.equals(memId)) {
				System.out.println(member.getFirstName() + " " + member.getLastName() + " Checkout Record Details:");
				for(CheckoutEntry checkoutEntry : member.getCheckoutRecord().getCheckoutEntries()) {
					System.out.print(checkoutEntry.getBookCopy().getBook().getIsbn());
					System.out.print("\t" + checkoutEntry.getBookCopy().getBook().getTitle());
					System.out.print("\t" + checkoutEntry.getCheckoutDate());
					System.out.println("\t" + checkoutEntry.getDueDate());	
				}
			}
		}
		alertSuccess.setContentText("Member checkout details printed successfully!");
		alertSuccess.show();
		alertSuccess.setOnCloseRequest( e -> {
	          if (alertSuccess.getResult() == ButtonType.OK) {
	        	  backToMain(event);
	          }});	
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
