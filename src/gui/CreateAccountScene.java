package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import model.BankAccount;
import Service.BankService;

public class CreateAccountScene {

    private Stage stage;
    private BankService bankService;

    public CreateAccountScene(Stage stage, BankService bankService) {
        this.stage = stage;
        this.bankService = bankService;
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label pinLabel = new Label("PIN:");
        PasswordField pinField = new PasswordField();
        Label balanceLabel = new Label("Initial Deposit:");
        TextField balanceField = new TextField();
        Button createBtn = new Button("Create Account");
        Button backBtn = new Button("Back");
        Label msgLabel = new Label();

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(pinLabel, 0, 1);
        grid.add(pinField, 1, 1);
        grid.add(balanceLabel, 0, 2);
        grid.add(balanceField, 1, 2);
        grid.add(createBtn, 0, 3);
        grid.add(backBtn, 1, 3);
        grid.add(msgLabel, 0, 4, 2, 1);

        createBtn.setOnAction(e -> {
            try {
                double bal = Double.parseDouble(balanceField.getText());
                BankAccount acc = bankService.createAccount(nameField.getText(), pinField.getText(), bal);
                msgLabel.setText("Account Created! Acc No: " + acc.getAccountNumber());
            } catch (Exception ex) {
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> {
            LoginScene login = new LoginScene(stage);
            login.show();
        });

        Scene scene = new Scene(grid, 400, 250);
        stage.setTitle("Secure Bank - Create Account");
        stage.setScene(scene);
        stage.show();
    }
}
