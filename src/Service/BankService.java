package service;

import model.BankAccount;
import util.FileUtil;

import java.util.Map;

public class BankService {

    private Map<String, BankAccount> accounts;

    public BankService() {
        accounts = FileUtil.loadAccounts();
    }

    public BankAccount createAccount(String name, String pin, double balance) {
        BankAccount account = new BankAccount(name, pin, balance);
        accounts.put(account.getAccountNumber(), account);
        FileUtil.saveAccounts(accounts);
        return account;
    }

    public BankAccount login(String accNo, String pin) throws Exception {
        BankAccount account = accounts.get(accNo);
        if (account == null) throw new Exception("Account not found.");

        account.validatePin(pin);
        return account;
    }

    public void transfer(String fromAccNo, String toAccNo, double amount) throws Exception {
        BankAccount sender = accounts.get(fromAccNo);
        BankAccount receiver = accounts.get(toAccNo);
        if (sender == null || receiver == null) throw new Exception("Invalid account number.");

        sender.transferTo(receiver, amount);
        FileUtil.saveAccounts(accounts); // persist after transfer
    }

    public void saveData() {
        FileUtil.saveAccounts(accounts);
    }
}
