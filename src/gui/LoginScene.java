package gui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.BankAccount;
import Service.BankService;

public class LoginScene {

    private Stage stage;
    private BankService bankService;

    public LoginScene(Stage stage) {
        this.stage = stage;
        this.bankService = new BankService();
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label accLabel = new Label("Account Number:");
        TextField accField = new TextField();
        Label pinLabel = new Label("PIN:");
        PasswordField pinField = new PasswordField();
        Button loginBtn = new Button("Login");
        Button createBtn = new Button("Create Account");
        Label msgLabel = new Label();

        grid.add(accLabel, 0, 0);
        grid.add(accField, 1, 0);
        grid.add(pinLabel, 0, 1);
        grid.add(pinField, 1, 1);
        grid.add(loginBtn, 0, 2);
        grid.add(createBtn, 1, 2);
        grid.add(msgLabel, 0, 3, 2, 1);

        loginBtn.setOnAction(e -> {
            try {
                BankAccount acc = bankService.login(accField.getText(), pinField.getText());
                DashboardScene dash = new DashboardScene(stage, acc, bankService);
                dash.show();
            } catch (Exception ex) {
                msgLabel.setText(ex.getMessage());
            }
        });

        createBtn.setOnAction(e -> {
            CreateAccountScene createScene = new CreateAccountScene(stage, bankService);
            createScene.show();
        });

        Scene scene = new Scene(grid, 400, 250);
        stage.setTitle("Secure Bank - Login");
        stage.setScene(scene);
        stage.show();
    }
}
