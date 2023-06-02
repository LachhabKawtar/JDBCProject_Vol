package com.example.demo.views;

import com.example.demo.entities.Vol;
import com.example.demo.securité.User;
import com.example.demo.services.UserServices;
import com.example.demo.services.VolService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ViewVol extends Application {

    private final TextField textFieldAeroportA = new TextField();
    private final TextField textFieldAeroportD = new TextField();
    private final TextField nbrEscales = new TextField();
    private final DatePicker textFieldDateD = new DatePicker();
    private final DatePicker textFieldDateA = new DatePicker();

     Button addBtn = new Button();
     Button deleteBtn = new Button();
     Button editBtn = new Button();
     Button btnRegister = new Button("Register");
    // Button test = new Button("Test");
    Alert alertWarning = new Alert(Alert.AlertType.ERROR, "Error");
    VolService volService = new VolService();
    TableView<Vol> table = new TableView<Vol>(getDataVol());


    //login Form
    Button btnLogin = new Button("Sing In");
    TextField textFieldLogin = new TextField();
    TextField textFieldPassword = new TextField();
    Label labelLogin = new Label("Login");


    //
    Button importTxt = new Button("Importer from txt");
    Button exportTxt = new Button("Exporter to txt ");

    Button btnRestor = new Button("RestoreRows");



    //Combobox
    ComboBox<String> compoImport = new ComboBox<>();
    Label labimport = new Label("Import Data:");

    ComboBox<String> compoExport = new ComboBox<>();
    Label labExport = new Label("Export Data:");
    Label labTest = new Label("");

    //search imput
    TextField textFieldSearch = new TextField();



    @Override
    public void start(Stage stage) {

        TableView.TableViewSelectionModel<Vol> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

        addColumsId();
        addColumsAeroportA();
        addColumsAeroportD();
        addColumsDateA();
        addColumsDateD();
        addColumsNbrEScales();



        //Import Export
        compoImport.getItems().addAll("txt","xl");
        compoExport.getItems().addAll("txt","xl");
        compoImport.setOnAction(e -> {
            try {
                if( compoImport.getValue().equals("txt")){
                    labTest.setText("Data is imported from txt file");
                    labTest.setStyle("-fx-background-color: green;"+
                            "-fx-text-fill: White");
                 // compoImport.setValue("Select Format file");
                    importDATA();
                }
                else if (compoImport.getValue().equals("xl")){
                    importDataXL();
                    labTest.setText("Data is imported from XL file");
                    labTest.setStyle("-fx-background-color: green;"+
                            "-fx-text-fill: White");
                   // compoImport.setValue("Select Format file");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        compoExport.setOnAction(e -> {
            try {
                if( compoExport.getValue().equals("txt")){
                    labTest.setText("Data is exported to txt file");
                   /// compoExport.setValue("Select Format file");
                    exportDataToTxt();
                }
                else if (compoExport.getValue().equals("xl")){
                    labTest.setText("Data is exported to XL file");
                    labTest.setStyle("-fx-background-color: green;"+
                            "-fx-text-fill: White");
                    //compoExport.setValue("Select Format file");
                    exportToXLFile();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        compoImport.setValue("Select Format file");
        compoExport.setValue("Select Format file");
      ///  HBox hBoxImportExport = new HBox(labimport,compoImport,labExport,compoExport,labTest);
        VBox vboxTable = new VBox(new Label("LIST OF VOL"),table);
        table.setEditable(true);
        GridPane newDataPane = this.getNewVolDataPane();

        deleteBtn.setText("Delete");
        deleteBtn. setStyle( "-fx-border-radius: 5;" +
                "-fx-border-width: 2;" +
                "-fx-text-fill: White;" +
                "-fx-border-color: red;" +
                "-fx-background-color: red;");
        editBtn. setStyle( "-fx-border-radius: 5;" +
                "-fx-border-width: 2;" +
                "-fx-text-fill: White;" +
                "-fx-border-color: green;" +
                "-fx-background-color: green;");
        editBtn.setText("Cancel");
        editBtn.setDisable(true);
        editBtn.setOnAction(e-> cancel());
        deleteBtn.setOnAction(e -> deleteSelectedRows());

        importTxt.setOnAction(e-> {
            try {
                importDATA();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exportTxt.setOnAction(e-> exportDataToTxt());

        setDisabledForm(true);
        GridPane panelOprationDataVol = new GridPane();
        panelOprationDataVol.setHgap(10);
        panelOprationDataVol.setVgap(5);
        panelOprationDataVol.addRow(0, labimport, compoImport,labTest);
        panelOprationDataVol.addRow(1, labExport, compoExport);
        panelOprationDataVol.addRow(2, new Label("Search by ID:"), textFieldSearch,btnRestor);
        textFieldSearch.setOnAction(e-> getVolSearched());
        btnRestor.setOnAction(e-> restoreRows());
        VBox hBoxTbleXport = new VBox(panelOprationDataVol,vboxTable);
        HBox root = new HBox(newDataPane, hBoxTbleXport);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 4;" +
                "-fx-border-insets: 4;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: green;");
        labTest.setStyle("-fx-background-color: green");
        Scene scene = new Scene(root);
        GridPane p = createGridPaneLogin();
        Scene scene2 = new Scene(p);
        btnLogin.setOnAction(e -> {
            if (loginUser()==true) {
                stage.setWidth(900);
                stage.setHeight(600);
                stage.setScene(scene);
            }else alertWarning.showAndWait();});
        stage.setScene(scene2);
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Compagnie Aériennes");

        stage.show();
    }

    private void exportToXLFile() throws IOException {
       volService.exportDataXLFile();
    }

    private void importDataXL() throws Exception {
        volService.importXL();
        table.setItems(getDataVol());
    }

    private void getVolSearched() {
        Vol v = volService.getVolByID(Integer.parseInt(textFieldSearch.getText()));
        table.setItems(FXCollections.<Vol>observableArrayList(v));
    }

    private void exportDataToTxt() {
      volService.exportTxt(volService.findAll());
    }

    private void importDATA() throws Exception {
        //volService.importDataTxt().forEach(vol -> volService.save(vol));
        volService.importDataTxt();
        table.setItems(getDataVol());
    }

    private void deleteSelectedRows() {
       TableView.TableViewSelectionModel<Vol> tableViewSelectionModel = table.getSelectionModel();
        if (tableViewSelectionModel.isEmpty()) {
            alertWarning.showAndWait();
            return;
        }
        else {
            Alert alerteConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sur?!");
            alerteConfirm.showAndWait();

            // Get all selected row indices in an array
            ObservableList<Integer> list = tableViewSelectionModel.getSelectedIndices();
            Integer[] selectedIndices = new Integer[list.size()];
            selectedIndices = list.toArray(selectedIndices);

            // Sort the array
            Arrays.sort(selectedIndices);

            // Delete rows (last to first)
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
               int id =   table.getItems().get(i).getId();
                System.out.println(id);
                tableViewSelectionModel.clearSelection(selectedIndices[i].intValue());
                table.getItems().remove(selectedIndices[i].intValue());
                volService.remove(id);
            }
            labTest.setText("Deleted sucessfuly!");
            labTest.setStyle("-fx-background-color: red;"+
                             "-fx-text-fill: White;");
        }
    }
    public void cancel(){
        clearFields();
        addBtn.setText("Add");
        setDisabledForm(true);
        editBtn.setDisable(true);
    }

    public void clearFields() {
        textFieldAeroportA.clear();
        textFieldAeroportD.clear();
        nbrEscales.clear();
        textFieldDateA.setValue(null);
        textFieldDateD.setValue(null);
    }
    public void setDisabledForm(Boolean b) {
        textFieldAeroportA.setDisable(b);
        textFieldAeroportD.setDisable(b);
        nbrEscales.setDisable(b);
        textFieldDateA.setDisable(b);
        textFieldDateD.setDisable(b);
    }
    public GridPane getNewVolDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("Aeroport de départ:"), textFieldAeroportD);
        pane.addRow(1, new Label("Aeroport d'arrivée: "), textFieldAeroportA);
        pane.addRow(2, new Label("Date de départ:"), textFieldDateD);
        pane.addRow(3, new Label("Date d'arrivée:"), textFieldDateA);
        pane.addRow(4, new Label("Nombre d'escales:"), nbrEscales);


        addBtn.setText("Add");
        addBtn. setStyle( "-fx-border-radius: 5;" +
                "-fx-border-width: 2;" +
                "-fx-text-fill: White;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: #24a0ed;");
        addBtn.borderProperty();
        addBtn.setOnAction((e -> { addVol(); }));
        HBox hboxBtn = new HBox(addBtn,deleteBtn,editBtn);

        pane.addRow(5, hboxBtn);
        return pane;
    }

    public ObservableList<Vol> getDataVol() {
        List listVol = volService.findAll();
        ObservableList<Vol> data = FXCollections.observableArrayList(listVol);
        return data;
    }
    public Vol getVol()
    {
        String aeroportA = textFieldAeroportA.getText();
        String aeroportD = textFieldAeroportD.getText();
        String textFieldnbrEscales = nbrEscales.getText();

        if (aeroportA.isEmpty() || aeroportD.isEmpty() ||
                textFieldnbrEscales.isEmpty() ||
                textFieldDateD==null ||
                textFieldDateA==null) {
                alertWarning.showAndWait();
                return  null;
        } else {
            int nbr = Integer.parseInt(textFieldnbrEscales);
            Date date1 = Date.from(textFieldDateA.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date date2 = Date.from(textFieldDateD.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            return  new Vol(1, date1, date2, aeroportA, aeroportD, nbr);
        }
    }
    public void addVol() {
        if(addBtn.getText().equals("Add")){
            setDisabledForm(false);
            addBtn.setText("Confirm");
            editBtn.setDisable(false);
        }
        else {
            Vol vol = getVol();
            if (vol != null) {
                volService.save(vol);
                table.getItems().add(vol);
                clearFields();
                setDisabledForm(true);
                addBtn.setText("Add");
                editBtn.setDisable(true);
                labTest.setText("Added sucessfuly!");
                labTest.setStyle("-fx-background-color: blue;"+
                        "-fx-text-fill: White;");
            }
        }
    }

    //colums
    public void addColumsAeroportA(){

        TableColumn columnAeroportA = new TableColumn("aeroportA");
        columnAeroportA.setCellValueFactory(
                new PropertyValueFactory<Vol, String>("aeroportA"));
        table.getColumns().add(columnAeroportA);
        columnAeroportA.setCellFactory(
                TextFieldTableCell.forTableColumn());
    }

    public void addColumsId(){

        TableColumn columnId = new TableColumn("Id");
        columnId.setCellValueFactory(
                new PropertyValueFactory<Vol, String>("Id"));
        table.getColumns().add(columnId);
    }
    public void addColumsAeroportD(){

        TableColumn columnAeroportD = new TableColumn("aeroportD");
        columnAeroportD.setCellValueFactory(
                new PropertyValueFactory<Vol, String>("aeroportD"));
        table.getColumns().add(columnAeroportD);
        columnAeroportD.setCellFactory(
                TextFieldTableCell.forTableColumn());
        columnAeroportD.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent cellEditEvent) {
                volService.update((Vol) cellEditEvent.getRowValue());
            }
        });
    }
    public void addColumsDateA(){

        TableColumn columnDateA = new TableColumn("dateA");
        columnDateA.setCellValueFactory(
                new PropertyValueFactory<Vol, Date>("dateA"));
        DateStringConverter converter = new DateStringConverter();
        columnDateA.setCellFactory(
                TextFieldTableCell.<Vol, Date>forTableColumn(converter));
        table.getColumns().add(columnDateA);
        columnDateA.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent cellEditEvent) {
               Vol vol = (Vol) cellEditEvent.getRowValue();
               volService.update(vol);
            }
        });
    }
    public void addColumsDateD(){

        TableColumn columnDateD = new TableColumn("dateD");
        columnDateD.setCellValueFactory(
                new PropertyValueFactory<Vol, Date>("dateD"));
        DateStringConverter converter = new DateStringConverter();
        columnDateD.setCellFactory(
                TextFieldTableCell.<Vol, Date>forTableColumn(converter));
        table.getColumns().add(columnDateD);
    }
    private void addColumsNbrEScales() {
        TableColumn columnNbrEscales = new TableColumn("Escales");
        columnNbrEscales.setCellValueFactory(
                new PropertyValueFactory<Vol, Integer>("nbrEscales"));
        IntegerStringConverter i = new IntegerStringConverter();
        columnNbrEscales.setCellFactory(
                TextFieldTableCell.<Vol,Integer>forTableColumn(i));
        table.getColumns().add(columnNbrEscales);
    }
    public GridPane createGridPaneLogin()
    {
        GridPane p = new GridPane();
        p.setHgap(5);
        p.setVgap(5);
        p.add(labelLogin,7,0);
        labelLogin.setFont( new Font("SansSerif",15));
        labelLogin.setStyle("-fx-text-fill: #A9A9A9");
        p.add(textFieldLogin,7,1);
        textFieldPassword.setStyle( "-fx-border-width: 2;"+
                "-fx-border-color: #A9A9A9;"+
                "-fx-border-radius: 5;");
        p.add(textFieldPassword,7,2);
        btnLogin.setFont(new Font("SansSerif",15));
        btnLogin. setStyle( "-fx-border-radius: 5;" +
                "-fx-border-width: 2;" +
                "-fx-text-fill: White;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: #24a0ed;");
        btnRegister. setStyle( "-fx-border-radius: 5;" +
                "-fx-border-width: 2;" +
                "-fx-text-fill: White;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: #24a0ed;");
        p.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: transparent;");

        p.add(btnLogin,7,5);
       // p.add(btnRegister,8,5);
        return  p;
    }
    public Boolean loginUser(){
        String login =   textFieldLogin.getText();
        String password =   textFieldPassword.getText();
        UserServices  userServices = new UserServices();
        Boolean sucess= userServices.login(login,password);
        return  sucess;
    }
    public Boolean register(){
        String login =   textFieldLogin.getText();
        String password =   textFieldPassword.getText();
        if(textFieldPassword==null || textFieldLogin==null) return  false;
        else{
        UserServices  userServices = new UserServices();
        User user = new User(1,"kawtar","lachhhab",login,password);
        new UserServices().save(user);
        return true;}
    }
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(getDataVol());
    }
    public static void main(String[] args) {
        Application.launch(ViewVol.class);
    }
}