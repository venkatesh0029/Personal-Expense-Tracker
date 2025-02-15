import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class GUI {
    private JFrame frame;
    private ExpenseManager manager;

    public GUI() {
        // Initialize GUI and user details
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        String name = JOptionPane.showInputDialog("Enter your name:");
        double monthlyIncome = Double.parseDouble(JOptionPane.showInputDialog("Enter your monthly income:"));
        User user = new User(name, monthlyIncome);
        manager = new ExpenseManager(user);

        JOptionPane.showMessageDialog(frame, "Welcome " + name + "! Monthly budget: " + user.getTotalBudget());
    }

    public void showMainMenu() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel title = new JLabel("Expense Tracker", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(new EmptyBorder(10, 10, 20, 10));
        panel.add(title, gbc);

        JButton addExpenseButton = new JButton("Add Expense");
        customizeButton(addExpenseButton, new Color(50, 205, 50));
        addExpenseButton.addActionListener(e -> showAddExpenseWindow());
        panel.add(addExpenseButton, gbc);

        JButton viewReportButton = new JButton("View Report");
        customizeButton(viewReportButton, new Color(30, 144, 255));
        viewReportButton.addActionListener(e -> showReportWindow());
        panel.add(viewReportButton, gbc);

        JButton exitButton = new JButton("Exit");
        customizeButton(exitButton, new Color(255, 69, 0));
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showAddExpenseWindow() {
        JDialog addExpenseDialog = new JDialog(frame, "Add Expense", true);
        addExpenseDialog.setSize(350, 250);
        addExpenseDialog.setLocationRelativeTo(frame);
        addExpenseDialog.setLayout(new GridLayout(4, 2, 5, 5));
        addExpenseDialog.getContentPane().setBackground(new Color(255, 239, 213));

        addExpenseDialog.add(new JLabel("Category:"));
        JComboBox<Category> categoryBox = new JComboBox<>(Category.values());
        addExpenseDialog.add(categoryBox);

        addExpenseDialog.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        addExpenseDialog.add(amountField);

        addExpenseDialog.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        addExpenseDialog.add(descriptionField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                Category category = (Category) categoryBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                Expense expense = new Expense(category, amount, description);
                manager.addExpense(expense);
                JOptionPane.showMessageDialog(addExpenseDialog, "Expense added successfully!");
                addExpenseDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addExpenseDialog, "Invalid input. Please try again.");
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> addExpenseDialog.dispose());

        addExpenseDialog.add(addButton);
        addExpenseDialog.add(cancelButton);

        addExpenseDialog.setVisible(true);
    }

    private void showReportWindow() {
        StringBuilder reportText = new StringBuilder("<html><body style='font-family: Arial;'>");
        double totalExpenses = manager.getTotalExpenses();
        User user = manager.getUser();

        reportText.append("<h2>Expense Report</h2>");
        reportText.append("Monthly Budget: ").append(user.getTotalBudget()).append("<br>");
        reportText.append("Total Expenses: ").append(totalExpenses).append("<br>");

        if (totalExpenses > user.getTotalBudget()) {
            reportText.append("<p style='color:red'>⚠️ Over budget by ").append(totalExpenses - user.getTotalBudget()).append("</p>");
        } else {
            reportText.append("<p style='color:green'>You are within budget!</p>");
        }

        reportText.append("<br>Expenses by Category:<br>");
        for (Category category : Category.values()) {
            double categoryTotal = manager.getExpensesByCategory(category).stream().mapToDouble(Expense::getAmount).sum();
            reportText.append(category).append(": ").append(categoryTotal).append("<br>");
        }
        
        reportText.append("</body></html>");

        JLabel reportLabel = new JLabel(reportText.toString());
        JFrame reportFrame = new JFrame("Expense Report");
        reportFrame.setSize(400, 300);
        reportFrame.add(reportLabel);
        reportFrame.setVisible(true);
    }

    private void customizeButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.DARK_GRAY, 2));
    }
}
