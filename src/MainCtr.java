import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class MainCtr implements Initializable{


    TextField editName = new TextField();
    TextField editCategory = new TextField();
    TextField editEmail = new TextField();
    TextField editPhone = new TextField();
    TextField editAddress = new TextField();
    TextArea editNotes = new TextArea();



    @FXML
    private TableView<PersonData> personDataTableView;
    @FXML
    private TableColumn<PersonData, String> nameColumn;
    @FXML
    private TableColumn<PersonData, String> categoryColumn;
    @FXML
    private TableColumn<PersonData, String> emailColumn;
    @FXML
    private TableColumn<PersonData, String> phoneColumn;
    @FXML
    private TableColumn<PersonData, String> notesColumn;

    @FXML
    private Button showDetailBtn;
    @FXML
    private Button newContactBtn;
    @FXML
    private Button deleteBtn;

    Dialog<ButtonType> dialog = null;
    Alert alert = new Alert(AlertType.NONE);
    //instantiate a model
    Mainmodel mainmodel = null;

    private String editIdString,editNameString,editCategoryString,editEmailString,editPhoneString,editAddressString,editNotesString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mainmodel = new Mainmodel();
        this.setDatatoTable();  
        
        showDetailBtn.setDisable(true);
        deleteBtn.setDisable(true);



        personDataTableView.setOnMouseClicked(e -> {
            PersonData selected = personDataTableView.getSelectionModel().getSelectedItem();

            if(selected != null){
                showDetailBtn.setDisable(false);
                deleteBtn.setDisable(false);

                editIdString = selected.idProperty().getValue();
                editNameString = selected.nameProperty().getValue();
                editCategoryString = selected.categoryProperty().getValue();
                editEmailString = selected.emailProperty().getValue();
                editPhoneString = selected.phoneProperty().getValue();
                editAddressString = selected.addressProperty().getValue();
                editNotesString = selected.notesProperty().getValue();
            }
        });


    }

    //load data to Table
    @FXML
    public void setDatatoTable(){

        this.nameColumn.setCellValueFactory( new PropertyValueFactory<PersonData, String>("name"));
        this.categoryColumn.setCellValueFactory( new PropertyValueFactory<PersonData, String>("category"));
        this.emailColumn.setCellValueFactory( new PropertyValueFactory<PersonData, String>("email"));
        this.phoneColumn.setCellValueFactory( new PropertyValueFactory<PersonData, String>("phone"));
        this.notesColumn.setCellValueFactory( new PropertyValueFactory<PersonData, String>("notes"));


        this.personDataTableView.setItems(mainmodel.getPersonData());
    }


    @FXML
    private void addPersonData(ActionEvent event){
        newContactModal();
        JFrame successAdd = new JFrame();

        dialog.showAndWait().ifPresent(response ->{
            if (response.getButtonData().equals(ButtonData.OK_DONE)) {
                mainmodel.addPerson(editName.getText(), editCategory.getText(),editEmail.getText(),editPhone.getText(),editAddress.getText(),editNotes.getText());
                JOptionPane.showMessageDialog(successAdd, "Added new contact successfully");
                successAdd.setSize(300,300);
                successAdd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setDatatoTable();

                
                
            }
        });

        
    }

    @FXML
    private void showDetail(ActionEvent event){
    JFrame successUpdate = new JFrame();
    
        detailModal();

        dialog.showAndWait().ifPresent(response -> {
            if(response.getButtonData().equals(ButtonData.OK_DONE)){
                mainmodel.editPersonData(editName.getText(), editCategory.getText(), editEmail.getText(), editPhone.getText(),editNotes.getText(),editAddress.getText(),editIdString);
                JOptionPane.showMessageDialog(successUpdate, "Updated contact successfully");
                successUpdate.setSize(300,300);
                successUpdate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setDatatoTable();
            }
        });
    }
    
    //delete person
    @FXML
    private void deletePerson(ActionEvent event){
        JFrame successDelete = new JFrame();
        PersonData selected = personDataTableView.getSelectionModel().getSelectedItem();
        int option = JOptionPane.showOptionDialog(null, "Do you delete "+selected.nameProperty().getValue()+"'s contact?", "delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, event);
        
        if (option == JOptionPane.YES_OPTION) {
        personDataTableView.getItems().remove(selected);
        mainmodel.delete(selected.idProperty().getValue());
        JOptionPane.showMessageDialog(successDelete, "Deleted "+selected.nameProperty().getValue()+"'s contact");
        successDelete.setSize(300,300);
        successDelete.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
       
       


    }

    
    private void newContactModal(){

        dialog = new Dialog<ButtonType>();

        dialog.setTitle("Add New Contact");
        ButtonType newContactBtn = new ButtonType("Add New Contact", ButtonData.OK_DONE);

        //set the content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);   
        gridPane.setVgap(10);   
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        

        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(editName, 1, 0);
        gridPane.add(new Label("Category"), 0, 1);
        gridPane.add(editCategory, 1, 1);
        gridPane.add(new Label("Email"), 0, 2);
        gridPane.add(editEmail, 1, 2);
        gridPane.add(new Label("Phone"), 0, 3);
        gridPane.add(editPhone, 1, 3);
        gridPane.add(new Label("Address"), 0, 4);
        gridPane.add(editAddress, 1, 4);
        gridPane.add(new Label("Notes"), 0, 5);
        gridPane.add(editNotes, 1, 5);


        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().add(newContactBtn);
    }


    private void detailModal(){

        dialog = new Dialog<ButtonType>();

        dialog.setTitle("Edit Person Data");
        ButtonType editBtn = new ButtonType("Edit", ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        //set the content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);   
        gridPane.setVgap(10);   
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        
        editName.setText(editNameString);
        editCategory.setText(editCategoryString);
        editEmail.setText(editEmailString);
        editPhone.setText(editPhoneString);
        editAddress.setText(editAddressString);
        editNotes.setText(editNotesString);

        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(editName, 1, 0);
        gridPane.add(new Label("Category"), 0, 1);
        gridPane.add(editCategory, 1, 1);
        gridPane.add(new Label("Email"), 0, 2);
        gridPane.add(editEmail, 1, 2);
        gridPane.add(new Label("Phone"), 0, 3);
        gridPane.add(editPhone, 1, 3);
        gridPane.add(new Label("Address"), 0, 4);
        gridPane.add(editAddress, 1, 4);
        gridPane.add(new Label("Notes"), 0, 5);
        gridPane.add(editNotes, 1, 5);


        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().add(editBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelBtn);
    }

   


    }




