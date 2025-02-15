import java.util.Date;

public class Expense {
    private Category category;
    private double amount;
    private String description;
    private Date date;

    public Expense(Category category, double amount, String description) {
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = new Date(); // Set current date as default
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "category=" + category +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
