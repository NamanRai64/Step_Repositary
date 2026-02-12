package gui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class DashboardScene {

    private Stage stage;
    private BankAccount account;
    private BankService bankService;

    public DashboardScene(Stage stage, BankAccount account, BankService bankService) {
        this.stage = stage;
        this.account = account;
        this.bankService = bankService;
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label balanceLabel = new Label("Balance: ₹" + account.getBalance());
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button transferBtn = new Button("Transfer");
        Button transactionsBtn = new Button("Transactions");
        Button logoutBtn = new Button("Logout");
        Label msgLabel = new Label();

        grid.add(balanceLabel, 0, 0, 2, 1);
        grid.add(depositBtn, 0, 1);
        grid.add(withdrawBtn, 1, 1);
        grid.add(transferBtn, 0, 2);
        grid.add(transactionsBtn, 1, 2);
        grid.add(logoutBtn, 0, 3);
        grid.add(msgLabel, 0, 4, 2, 1);

        depositBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Enter deposit amount:");
            dialog.showAndWait().ifPresent(amount -> {
                try {
                    account.deposit(Double.parseDouble(amount));
                    bankService.saveData();
                    balanceLabel.setText("Balance: ₹" + account.getBalance());
                    msgLabel.setText("Deposit successful!");
                } catch (Exception ex) {
                    msgLabel.setText("Error: " + ex.getMessage());
                }
            });
        });

        withdrawBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Enter withdrawal amount:");
            dialog.showAndWait().ifPresent(amount -> {
                try {
                    account.withdraw(Double.parseDouble(amount));
                    bankService.saveData();
                    balanceLabel.setText("Balance: ₹" + account.getBalance());
                    msgLabel.setText("Withdrawal successful!");
                } catch (Exception ex) {
                    msgLabel.setText("Error: " + ex.getMessage());
                }
            });
        });

        transferBtn.setOnAction(e -> {
            TextInputDialog dialogAcc = new TextInputDialog();
            dialogAcc.setHeaderText("Enter receiver account number:");
            dialogAcc.showAndWait().ifPresent(toAcc -> {
                TextInputDialog dialogAmt = new TextInputDialog();
                dialogAmt.setHeaderText("Enter amount:");
                dialogAmt.showAndWait().ifPresent(amount -> {
                    try {
                        bankService.transfer(account.getAccountNumber(), toAcc, Double.parseDouble(amount));
                        bankService.saveData();
                        balanceLabel.setText("Balance: ₹" + account.getBalance());
                        msgLabel.setText("Transfer successful!");
                    } catch (Exception ex) {
                        msgLabel.setText("Error: " + ex.getMessage());
                    }
                });
            });
        });

        transactionsBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Transaction History");
            StringBuilder sb = new StringBuilder();
            account.getTransactions().forEach(t -> sb.append(t).append("\n"));
            alert.setContentText(sb.toString());
            alert.showAndWait();
        });

        logoutBtn.setOnAction(e -> {
            LoginScene login = new LoginScene(stage);
            login.show();
        });

        Scene scene = new Scene(grid, 500, 350);
        stage.setTitle("Dashboard - " + account.getAccountNumber());
        stage.setScene(scene);
        stage.show();
    }
}
