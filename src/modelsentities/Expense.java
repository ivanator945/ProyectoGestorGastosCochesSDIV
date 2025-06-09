package modelsentities;

import java.time.LocalDate;

public class Expense {
    public enum ExpenseType { GASOLINE, REVIEW, ITV, OIL_CHANGE, OTHER }

    private String expenseId;
    private String carId;
    private ExpenseType expenseType;
    private int mileage;
    private LocalDate date;
    private double amount;
    private String description;

    public Expense(String expenseId, String carId, ExpenseType expenseType, int mileage, LocalDate date, double amount, String description) {
        this.expenseId = expenseId;
        this.carId = carId;
        this.expenseType = expenseType;
        this.mileage = mileage;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

  
    public String getExpenseId() { return expenseId; }
    public String getCarId() { return carId; }
    public ExpenseType getExpenseType() { return expenseType; }
    public int getMileage() { return mileage; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
