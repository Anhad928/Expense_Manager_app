package com.example.anhadpre_expbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends ViewModel {
    private MutableLiveData<List<Expense>> expensesData = new MutableLiveData<>();
    private List<Expense> expenses = new ArrayList<>();

    public ExpenseViewModel() {
        expensesData.setValue(new ArrayList<>());
    }

    public LiveData<List<Expense>> getExpensesData() {
        return expensesData;
    }

    public void addExpense(Expense expense) {
        List<Expense> currentExpenses = expensesData.getValue();
        if (currentExpenses != null) {
            currentExpenses.add(expense);
            expensesData.setValue(currentExpenses);
        }
    }

    public void deleteExpense(Expense expense) {
        List<Expense> currentExpenses = expensesData.getValue();
        if (currentExpenses != null) {
            currentExpenses.remove(expense);
            expensesData.setValue(currentExpenses);
        }
    }

    public void editExpense(Expense updatedExpense) {
        List<Expense> currentExpenses = expensesData.getValue();
        if (currentExpenses != null) {
            for (int i = 0; i < currentExpenses.size(); i++) {
                Expense expense = currentExpenses.get(i);


                if (expense.isChecked()) {
                    expense.setName(updatedExpense.getName());
                    expense.setMonthStarted(updatedExpense.getMonthStarted());
                    expense.setMonthlyCharge(updatedExpense.getMonthlyCharge());
                    expense.setComment(updatedExpense.getComment());

                    // Set the updated expense back into the list
                    currentExpenses.set(i, expense);

                    // Update the LiveData
                    expensesData.setValue(currentExpenses);
                    expense.setChecked(false);

                    // Break after updating the first matching expense (if needed)
                    break;
                }
            }
        }
    }
}
