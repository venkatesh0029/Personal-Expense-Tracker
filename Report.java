public class Report {
    private ExpenseManager manager;

    public Report(ExpenseManager manager) {
        this.manager = manager;
    }

    public void generateMonthlyReport() {
        User user = manager.getUser();
        double totalExpenses = manager.getTotalExpenses();

        System.out.println("\n=== Monthly Expense Report for " + user.getName() + " ===");
        System.out.println("Total Budget: " + user.getTotalBudget());
        System.out.println("Total Expenses: " + totalExpenses);

        if (totalExpenses > user.getTotalBudget()) {
            System.out
                    .println("⚠️ Warning: You have exceeded your budget by " + (totalExpenses - user.getTotalBudget()));
        } else {
            System.out.println("You are within your budget. Good job!");
        }

        System.out.println("\nExpenses by Category:");
        for (Category category : Category.values()) {
            double categoryTotal = manager.getExpensesByCategory(category).stream()
                    .mapToDouble(Expense::getAmount).sum();
            System.out.println(category + ": " + categoryTotal);
        }
    }
}
