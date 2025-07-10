package com.juank.javafx.parcial3.javafxdbparcial3.controller;

import com.juank.javafx.parcial3.javafxdbparcial3.model.Customer;
import com.juank.javafx.parcial3.javafxdbparcial3.model.User;
import com.juank.javafx.parcial3.javafxdbparcial3.repository.CustomerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CrudController implements Initializable {


    @FXML private HBox authButtonsBox;
    @FXML private HBox welcomeBox;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label welcomeLabel;
    @FXML private Label loginPromptMessage;

    @FXML private ComboBox<String> filterColumnComboBox;
    @FXML private TextField filterTextField;
    @FXML private Button clearFilterButton;

    @FXML private SplitPane mainContent;
    @FXML private VBox formSection;
    @FXML private Label formTitle;
    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, String> customerIdColumn;
    @FXML private TableColumn<Customer, String> companyNameColumn;
    @FXML private TableColumn<Customer, String> contactNameColumn;
    @FXML private TableColumn<Customer, String> contactTitleColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> cityColumn;
    @FXML private TableColumn<Customer, String> regionColumn;
    @FXML private TableColumn<Customer, String> postalCodeColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private TableColumn<Customer, Void> actionsColumn;

    @FXML private TextField customerIdField;
    @FXML private TextField companyNameField;
    @FXML private TextField contactNameField;
    @FXML private TextField contactTitleField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField regionField;
    @FXML private TextField postalCodeField;
    @FXML private TextField countryField;
    @FXML private TextField phoneField;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button refreshButton;

    private CustomerRepository customerRepository;
    private ObservableList<Customer> customerList;
    private FilteredList<Customer> filteredData;
    private User currentUser;
    private Customer editingCustomer;
    private boolean isEditing = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerRepository = new CustomerRepository();
        customerList = FXCollections.observableArrayList();

        setupTableColumns();
        setupActionsColumn();
        setupFilter();
        setupFilterComboBox();

        customersTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    loadCustomerToForm(newValue);
                }
            }
        );

        showLoginPrompt();
    }

    private void setupFilterComboBox() {
        filterColumnComboBox.setItems(FXCollections.observableArrayList(
            "All Columns",
            "Customer ID",
            "Company Name",
            "Contact Name",
            "Contact Title",
            "Address",
            "City",
            "Region",
            "Postal Code",
            "Country",
            "Phone"
        ));

        filterColumnComboBox.setValue("All Columns");
    }

    private void setupFilter() {
        filteredData = new FilteredList<>(customerList, p -> true);

        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(customersTable.comparatorProperty());

        customersTable.setItems(sortedData);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });

        filterColumnComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter();
        });
    }

    private void applyFilter() {
        filteredData.setPredicate(customer -> {
            String filterText = filterTextField.getText();
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = filterText.toLowerCase();

            String selectedColumn = filterColumnComboBox.getValue();
            if (selectedColumn == null) {
                selectedColumn = "All Columns";
            }

            switch (selectedColumn) {
                case "Customer ID":
                    return customer.getCustomerID() != null &&
                           customer.getCustomerID().toLowerCase().contains(lowerCaseFilter);
                case "Company Name":
                    return customer.getCompanyName() != null &&
                           customer.getCompanyName().toLowerCase().contains(lowerCaseFilter);
                case "Contact Name":
                    return customer.getContactName() != null &&
                           customer.getContactName().toLowerCase().contains(lowerCaseFilter);
                case "Contact Title":
                    return customer.getContactTitle() != null &&
                           customer.getContactTitle().toLowerCase().contains(lowerCaseFilter);
                case "Address":
                    return customer.getAddress() != null &&
                           customer.getAddress().toLowerCase().contains(lowerCaseFilter);
                case "City":
                    return customer.getCity() != null &&
                           customer.getCity().toLowerCase().contains(lowerCaseFilter);
                case "Region":
                    return customer.getRegion() != null &&
                           customer.getRegion().toLowerCase().contains(lowerCaseFilter);
                case "Postal Code":
                    return customer.getPostalCode() != null &&
                           customer.getPostalCode().toLowerCase().contains(lowerCaseFilter);
                case "Country":
                    return customer.getCountry() != null &&
                           customer.getCountry().toLowerCase().contains(lowerCaseFilter);
                case "Phone":
                    return customer.getPhone() != null &&
                           customer.getPhone().toLowerCase().contains(lowerCaseFilter);
                case "All Columns":
                default:
                    return (customer.getCustomerID() != null && customer.getCustomerID().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getCompanyName() != null && customer.getCompanyName().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getContactName() != null && customer.getContactName().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getContactTitle() != null && customer.getContactTitle().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getAddress() != null && customer.getAddress().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getCity() != null && customer.getCity().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getRegion() != null && customer.getRegion().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getPostalCode() != null && customer.getPostalCode().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getCountry() != null && customer.getCountry().toLowerCase().contains(lowerCaseFilter)) ||
                           (customer.getPhone() != null && customer.getPhone().toLowerCase().contains(lowerCaseFilter));
            }
        });
    }

    @FXML
    private void onClearFilterClick() {
        filterTextField.clear();
        filterColumnComboBox.setValue("All Columns");
    }

    // Authentication methods
    @FXML
    private void handleShowLogin() {
        openLoginWindow();
    }

    @FXML
    private void handleShowRegister() {
        openRegisterWindow();
    }

    @FXML
    private void handleLogout() {
        currentUser = null;
        showLoginPrompt();
        clearForm();
        customerList.clear();
    }

    private void openLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/juank/javafx/parcial3/javafxdbparcial3/login-view.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setCrudController(this);

            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(new Scene(root, 400, 300));
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setResizable(false);
            loginStage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open login window: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void openRegisterWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/juank/javafx/parcial3/javafxdbparcial3/register-view.fxml"));
            Parent root = loader.load();

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root, 400, 500));
            registerStage.initModality(Modality.APPLICATION_MODAL);
            registerStage.setResizable(false);
            registerStage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open register window: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void onSuccessfulLogin(User user) {
        this.currentUser = user;
        showMainContent();
        loadCustomers();
    }

    private void showLoginPrompt() {
        authButtonsBox.setVisible(true);
        authButtonsBox.setManaged(true);

        welcomeBox.setVisible(false);
        welcomeBox.setManaged(false);

        loginPromptMessage.setVisible(true);
        loginPromptMessage.setManaged(true);

        customersTable.setDisable(true);
        formSection.setDisable(true);
        refreshButton.setDisable(true);
        filterColumnComboBox.setDisable(true);
        filterTextField.setDisable(true);
        clearFilterButton.setDisable(true);
    }

    private void showMainContent() {
        authButtonsBox.setVisible(false);
        authButtonsBox.setManaged(false);


        welcomeBox.setVisible(true);
        welcomeBox.setManaged(true);
        updateWelcomeMessage();


        loginPromptMessage.setVisible(false);
        loginPromptMessage.setManaged(false);


        customersTable.setDisable(false);
        formSection.setDisable(false);
        refreshButton.setDisable(false);
        filterColumnComboBox.setDisable(false);
        filterTextField.setDisable(false);
        clearFilterButton.setDisable(false);
    }

    private void setupTableColumns() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactTitleColumn.setCellValueFactory(new PropertyValueFactory<>("contactTitle"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    editCustomer(customer);
                });

                deleteButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    deleteCustomer(customer);
                });

                editButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-padding: 2 8;");
                deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 2 8;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new javafx.scene.layout.HBox(5, editButton, deleteButton));
                }
            }
        });
    }


    private void updateWelcomeMessage() {
        if (currentUser != null && welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getName() + "!");
        }
    }

    private void loadCustomers() {
        try {
            customerList.clear();
            customerList.addAll(customerRepository.findAll());

        } catch (Exception e) {
            showAlert("Error", "Failed to load customers: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadCustomerToForm(Customer customer) {
        customerIdField.setText(customer.getCustomerID());
        companyNameField.setText(customer.getCompanyName());
        contactNameField.setText(customer.getContactName());
        contactTitleField.setText(customer.getContactTitle());
        addressField.setText(customer.getAddress());
        cityField.setText(customer.getCity());
        regionField.setText(customer.getRegion());
        postalCodeField.setText(customer.getPostalCode());
        countryField.setText(customer.getCountry());
        phoneField.setText(customer.getPhone());
    }

    private Customer getCustomerFromForm() {
        Customer customer = new Customer();
        customer.setCustomerID(customerIdField.getText().trim());
        customer.setCompanyName(companyNameField.getText().trim());
        customer.setContactName(contactNameField.getText().trim());
        customer.setContactTitle(contactTitleField.getText().trim());
        customer.setAddress(addressField.getText().trim());
        customer.setCity(cityField.getText().trim());
        customer.setRegion(regionField.getText().trim());
        customer.setPostalCode(postalCodeField.getText().trim());
        customer.setCountry(countryField.getText().trim());
        customer.setPhone(phoneField.getText().trim());
        return customer;
    }

    private void clearForm() {
        customerIdField.clear();
        companyNameField.clear();
        contactNameField.clear();
        contactTitleField.clear();
        addressField.clear();
        cityField.clear();
        regionField.clear();
        postalCodeField.clear();
        countryField.clear();
        phoneField.clear();

        customerIdField.setDisable(false);
        editingCustomer = null;
        isEditing = false;
        formTitle.setText("Add New Customer");
        saveButton.setText("Save Customer");
    }

    private boolean validateForm() {
        if (customerIdField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Customer ID is required", Alert.AlertType.WARNING);
            return false;
        }
        if (companyNameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Company Name is required", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void editCustomer(Customer customer) {
        editingCustomer = customer;
        isEditing = true;
        loadCustomerToForm(customer);
        customerIdField.setDisable(true);
        formTitle.setText("Edit Customer");
        saveButton.setText("Update Customer");
    }

    private void deleteCustomer(Customer customer) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Customer");
        confirmAlert.setHeaderText("Are you sure you want to delete this customer?");
        confirmAlert.setContentText("Customer: " + customer.getCustomerID() + " - " + customer.getCompanyName());

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                customerRepository.delete(customer.getCustomerID());
                customerList.remove(customer);
                clearForm();
                showAlert("Success", "Customer deleted successfully", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", "Failed to delete customer: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onSaveButtonClick() {
        if (!validateForm()) {
            return;
        }

        try {
            Customer customer = getCustomerFromForm();

            if (isEditing) {
                customerRepository.update(customer);
                // Update the customer in the list
                int index = customerList.indexOf(editingCustomer);
                if (index != -1) {
                    customerList.set(index, customer);
                }
                showAlert("Success", "Customer updated successfully", Alert.AlertType.INFORMATION);
            } else {
                // Check if customer ID already exists
                if (customerRepository.findById(customer.getCustomerID()) != null) {
                    showAlert("Error", "Customer ID already exists", Alert.AlertType.ERROR);
                    return;
                }
                customerRepository.save(customer);
                customerList.add(customer);
                showAlert("Success", "Customer created successfully", Alert.AlertType.INFORMATION);
            }

            clearForm();
        } catch (Exception e) {
            showAlert("Error", "Failed to save customer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onCancelButtonClick() {
        clearForm();
        customersTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void onRefreshButtonClick() {
        loadCustomers();
        clearForm();
        customersTable.getSelectionModel().clearSelection();
    }
}
