package com.example.pawel.wydatex;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDatabase {

    private static List<Expense> expenses = new ArrayList<>();

    public static List<Expense> getExpenses() {
        return expenses;
    }
    static {
        expenses.add(new Expense("Jaja", 4.2, ExpenseCategory.FOOD));
        expenses.add(new Expense("Kino", 23.90, ExpenseCategory.ENTERTAINMENT));
        expenses.add(new Expense("Szampon", 19.20, ExpenseCategory.HYGIENE));
    }


    public static void addExpense(Expense expense){
        expenses.add(expense);
    }
}
