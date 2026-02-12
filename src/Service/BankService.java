package Service;

import model.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankService {

    private Map<String, BankAccount> accounts = new HashMap<>();

    public BankAccount createAccount(String name, String pin, double balance) {
        BankAccount account = new BankAccount(name, pin, balance);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public BankAccount login(String accNo, String pin) throws Exception {
        BankAccount account = accounts.get(accNo);

        if (account == null)
            throw new Exception("Account not found");

        account.validatePin(pin);
        return account;
    }

    public void transfer(String fromAccNo, String toAccNo, double amount) throws Exception {

        BankAccount sender = accounts.get(fromAccNo);
        BankAccount receiver = accounts.get(toAccNo);

        if (sender == null || receiver == null)
            throw new Exception("Invalid account number");

        sender.transferTo(receiver, amount);
    }
}
