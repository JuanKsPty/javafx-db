package com.juank.javafx.parcial3.javafxdbparcial3.controller;

import com.juank.javafx.parcial3.javafxdbparcial3.model.User;
import com.juank.javafx.parcial3.javafxdbparcial3.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    private final UserRepository userRepository = new UserRepository();
    private CrudController crudController;

    public void setCrudController(CrudController crudController) {
        this.crudController = crudController;
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Please fill in all fields", false);
            return;
        }

        User user = userRepository.authenticate(email, password);

        if (user != null) {
            showMessage("Login successful!", true);
            
            if (crudController != null) {
                crudController.onSuccessfulLogin(user);
            }

            closeWindow();
        } else {
            showMessage("Invalid email or password", false);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    private void showMessage(String message, boolean isSuccess) {
        messageLabel.setText(message);
        if (isSuccess) {
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
