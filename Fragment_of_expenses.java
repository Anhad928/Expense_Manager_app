package com.example.anhadpre_expbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Fragment_of_expenses extends Fragment {

    private ExpenseViewModel expenseViewModel;
    private Expense selectedExpense;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expenseViewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);

        // Check if the fragment was provided with a selected expense
        if (getArguments() != null && getArguments().containsKey("selected_expense")) {
            selectedExpense = getArguments().getParcelable("selected_expense");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_of_expenses, container, false);

        // Find the btnCancel by its ID
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Find the btnConfirm by its ID
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        // Retrieve data from EditText fields
        EditText editTextName = view.findViewById(R.id.editTextName);
        EditText editTextMonthStarted = view.findViewById(R.id.editTextMonthStarted);
        EditText editTextMonthlyCharge = view.findViewById(R.id.editTextMonthlyCharge);
        EditText editTextComment = view.findViewById(R.id.editTextComment);

        // For checking if it should perform edit task
        if (selectedExpense != null) {
            editTextName.setText(selectedExpense.getName());
            editTextMonthStarted.setText(selectedExpense.getMonthStarted());
            editTextMonthlyCharge.setText(String.valueOf(selectedExpense.getMonthlyCharge()));
            editTextComment.setText(selectedExpense.getComment());
        }

        // Set an OnClickListener for the Confirm button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText fields
                String name = editTextName.getText().toString();
                String monthStarted = editTextMonthStarted.getText().toString();
                double monthlyCharge = Double.parseDouble(editTextMonthlyCharge.getText().toString());
                String comment = editTextComment.getText().toString();

                if (selectedExpense != null) {
                    // It's an edit operation
                    Expense updatedExpense = new Expense(name, monthStarted, monthlyCharge, comment);
                    updatedExpense.setId(selectedExpense.getId()); // Set the ID

                    expenseViewModel.editExpense(updatedExpense);
                } else {
                    // It's an add operation
                    Expense expense = new Expense(name, monthStarted, monthlyCharge, comment);

                    expenseViewModel.addExpense(expense);
                }

                getFragmentManager().popBackStack();
            }
        });

        // Set an OnClickListener for the Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }
}
