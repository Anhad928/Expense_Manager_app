package com.example.anhadpre_expbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class dashFragment extends Fragment {

    private ExpenseViewModel expenseViewModel;

    private ExpenseAdapter adapter;
    private ListView expenseListView;
    private TextView textInitialAmount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ViewModel
        expenseViewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);

        // Initialize the adapter with an empty list (this will be updated by LiveData)
        adapter = new ExpenseAdapter(requireContext(), new ArrayList<Expense>());

        // To observe changes in the expenses list
        expenseViewModel.getExpensesData().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                adapter.setExpenses(expenses);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash, container, false);

        // Initialize your ListView
        expenseListView = view.findViewById(R.id.listViewExpenses);
        expenseListView.setAdapter(adapter);

        // Find the "btnAdd" button by its ID
        Button btnAddExpense = view.findViewById(R.id.btnAdd);

        // Set a click listener for the button
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_of_expenses expenseFragment = new Fragment_of_expenses();

                // Replace the current fragment with the expenseFragment
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, expenseFragment)
                        .addToBackStack(null) // Optional: Add to back stack for navigation
                        .commit();
            }
        });

        // Find the "btnDelete" button by its ID
        Button btnDeleteExpense = view.findViewById(R.id.btnDelete);

        // Set a click listener for the "Delete" button
        btnDeleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Expense> checkedExpenses = adapter.getCheckedExpenses();

                // Remove the checked expenses from the ViewModel
                for (Expense expense : checkedExpenses) {
                    expenseViewModel.deleteExpense(expense);
                }
            }
        });

        textInitialAmount = view.findViewById(R.id.textInitialAmount);

        // To observe changes in the expenses list
        expenseViewModel.getExpensesData().observe(getViewLifecycleOwner(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                adapter.notifyDataSetChanged();

                // Update the total expenses TextView
                updateTotalExpenses(expenses);
            }
        });

        // Find the "btnEdit" button by its ID
        Button btnEditExpense = view.findViewById(R.id.btnEdit);

        btnEditExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected expense from the adapter
                List<Expense> checkedExpenses = adapter.getCheckedExpenses();

                for (Expense expense : checkedExpenses) {
                    if (expense != null) {
                        Fragment_of_expenses expenseFragment = new Fragment_of_expenses();

                        // Pass the selected expense as an argument
                        Bundle args = new Bundle();
                        args.putParcelable("selected_expense", expense);
                        expenseFragment.setArguments(args);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, expenseFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }

                }

        });

        return view;
    }

    private void updateTotalExpenses(List<Expense> expenses) {
        double total = 0.00;

        for (Expense expense : expenses) {
            total += expense.getMonthlyCharge();
        }

        // Update the textInitialAmount TextView with the calculated total
        // Format the string to 2 decimal places
        String totalExpensesText = String.format("%.2f", total);
        textInitialAmount.setText("Total Expenses: $" + totalExpensesText);
    }
}
