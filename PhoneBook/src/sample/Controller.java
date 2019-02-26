package sample;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import javafx.beans.property.SimpleStringProperty;
import com.itextpdf.text.Document;

import java.sql.ResultSet;




public class Controller implements Initializable {

    @FXML
    TableView table;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputEmail;
    @FXML
    Button addNewContactButton;
    @FXML
    Button exportButton;
    @FXML
    TextField exportInput;


    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXIT = "Kilépés";

    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Szabó", "Gyula","vik.maria24@gmail.com" ),
                    new Person("Kiss","Krisztián","valami@example.com"),
                    new Person("Tul" , "Gyorsan","tulgyorsan@halad.com")
            );


    public void setTableData(){
        TableColumn lastNameCol = new TableColumn("Vezetéknév:");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("lastname"));
        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person,String> t) {
                        ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastname(t.getNewValue());
                    }
                }

        );

        TableColumn firstNameCol = new TableColumn("Keresztnév:");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("firstname"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person,String> t) {
                        ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstname(t.getNewValue());
                    }
                }

        );

        TableColumn emailCol = new TableColumn("Email:");
        emailCol.setMinWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));
        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person,String> t) {
                        ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    }
                }

        );

        table.getColumns().addAll(lastNameCol,firstNameCol,emailCol);
        table.setItems(data);
    }

    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);
        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        //nodeItemA.setExpanded(true);


        Node contactsNode = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node exportsNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));

        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST,contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT,exportsNode);
        nodeItemA.getChildren().addAll(nodeItemA1,nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA,nodeItemB);

        menuPane.getChildren().addAll(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if(selectedMenu != null){
                    switch (selectedMenu){
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            exportPane.setVisible(false);
                            contactPane.setVisible(true);
                            break;
                        case MENU_EXPORT:
                            exportPane.setVisible(true);
                            contactPane.setVisible(false);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }

                }
            }

        });

    }

    public void addContact(ActionEvent actionEvent) {
        System.out.println("Mukodik");
        String email = inputEmail.getText();
        if(email.length()> 5 && email.contains("@") && email.contains(".")){
            data.add(new Person(inputLastName.getText(),inputFirstName.getText(),email));
            inputEmail.clear();
            inputFirstName.clear();
            inputLastName.clear();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableData();
        setMenuData();
    }


    public void exportList(ActionEvent actionEvent) {
        String fileName = exportInput.getText();
        fileName = fileName.replaceAll("\\s","");
        //System.out.println("A"+fileName+"A");
        if(fileName != null && !fileName.equals("")){
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName,data);
        }
    }
}
