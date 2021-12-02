package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

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
	private Button submitButton;
	
	Alert al = new Alert(AlertType.INFORMATION);
	
	public void addNewMember(ActionEvent event) {
		Address newAddress= new Address(street.getText(),city.getText(),state.getText(),zip.getText());
		LibraryMember newMember=new LibraryMember( UUID.randomUUID().toString(),firstName.getText(),lastName.getText(), telephone.getText(),newAddress);
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(newMember);
		al.setContentText("ammmuaaaa");
		al.show();
	}
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	public LibraryMember addNewMember() {
		DataAccessFacade daf = new DataAccessFacade();
		daf.saveNewMember(null);
		
		return null;
		
	}
	
	
}
