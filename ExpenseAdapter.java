package com.example.anhadpre_expbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExpenseAdapter extends BaseAdapter {
    private List<Expense> expenses;
    private LayoutInflater inflater;
    private Set<Integer> selectedPositions;
    private Expense selectedExpense;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        this.expenses = expenses;
        inflater = LayoutInflater.from(context);
        this.selectedPositions = selectedPositions;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }


    public Expense getSelectedExpense() {
        return selectedExpense;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expense_item, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView monthStartedTextView = convertView.findViewById(R.id.monthStartedTextView);
        TextView monthlyChargeTextView = convertView.findViewById(R.id.monthlyChargeTextView);
        TextView commentTextView = convertView.findViewById(R.id.commentTextView);
        CheckBox checkBox = convertView.findViewById(R.id.checkboxItem);

        nameTextView.setText("Name: " + expense.getName());
        monthStartedTextView.setText("Month Started: " + expense.getMonthStarted());
        String formattedMonthlyCharge = String.format("%.2f", expense.getMonthlyCharge());
        monthlyChargeTextView.setText("Monthly Charge: $" + formattedMonthlyCharge);

        commentTextView.setText("Comment: " + expense.getComment());

        checkBox.setChecked(expense.isChecked());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                expense.setChecked(isChecked);
                if (isChecked) {
                    selectedExpense = expense;
                } else {
                    selectedExpense = null;
                }
            }
        });

        return convertView;
    }

    // Method to get the list of checked expenses
    public List<Expense> getCheckedExpenses() {
        List<Expense> checkedExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.isChecked()) {
                checkedExpenses.add(expense);
            }
        }
        return checkedExpenses;
    }
}