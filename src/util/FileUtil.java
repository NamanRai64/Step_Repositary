package util;

import model.BankAccount;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    private static final String FILE_PATH = "accounts.dat";

    // Save accounts to file
    public static void saveAccounts(Map<String, BankAccount> accounts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Load accounts from file
    @SuppressWarnings("unchecked")
    public static Map<String, BankAccount> loadAccounts() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<String, BankAccount>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
