package demo;
import java.util.*;

class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<String> transactions;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: $" + amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= balance && amount > 0) {
            balance -= amount;
            transactions.add("Withdrew: $" + amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Invalid or insufficient funds.");
        }
    }

    public void changePin(String newPin) {
        this.pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    public void printMiniStatement() {
        System.out.println("Mini Statement:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String t : transactions) {
                System.out.println("- " + t);
            }
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

public class ATM_System {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        
        accounts.put("12345678", new Account("12345678", "1234", 1000.00));
        accounts.put("87654321", new Account("87654321", "4321", 500.00));

        while (true) {
            System.out.println("\n==== Welcome to the ATM ====");
            System.out.print("Enter account number (or 0 to exit): ");
            String accNum = scanner.nextLine();

            if (accNum.equals("0")) {
                System.out.println("Thank you for visiting the ATM..!! Bye!");
                break;
            }

            if (!accounts.containsKey(accNum)) {
                System.out.println("Account not found.");
                continue;
            }

            Account currentAccount = accounts.get(accNum);
            boolean authenticated = false;

            // Limited login attempts
            for (int attempts = 3; attempts > 0; attempts--) {
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();
                if (currentAccount.authenticate(pin)) {
                    authenticated = true;
                    break;
                } else {
                    System.out.println("Incorrect PIN. Attempts left: " + (attempts - 1));
                }
            }

            if (!authenticated) {
                System.out.println("Too many failed attempts. Returning to main menu.");
                continue;
            }

            int choice;
            do {
                System.out.println("\n=== ATM Menu ===");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Mini Statement");
                System.out.println("5. Change PIN");
                System.out.println("6. Logout");
                System.out.print("Choose an option: ");
                choice = getIntegerInput();

                switch (choice) {
                    case 1:
                        System.out.printf("Current Balance: $%.2f%n", currentAccount.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = getDoubleInput();
                        currentAccount.deposit(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = getDoubleInput();
                        currentAccount.withdraw(withdrawAmount);
                        break;
                    case 4:
                        currentAccount.printMiniStatement();
                        break;
                    case 5:
                        System.out.print("Enter new PIN: ");
                        String newPin = scanner.nextLine();
                        currentAccount.changePin(newPin);
                        break;
                    case 6:
                        System.out.println("Logged out.");
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } while (choice != 6);
        }

        scanner.close();
    }

    private static int getIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid amount: ");
            }
        }
    }
}
