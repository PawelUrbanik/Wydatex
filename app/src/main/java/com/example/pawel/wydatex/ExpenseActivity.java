package com.example.pawel.wydatex;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ExpenseActivity extends Activity {

    /* Stałe */
    private static final String PREF_LAST_CATEGORY = "pref.last.category";
    private static final String PREF_SAVE_CATEGORY ="pref_save_category";
    private static final String PREF_DEFAULT_NAME = "pref_default_name";
    private static final String PREF_DEFAULT_PRICE = "pref_default_price" ;
    private static final String PREF_DEFAULT_VALUES="pref_default_values";

    /* Obiekt z ustawieniami */
    private SharedPreferences preferences;

    /* Komponenty */
    private TextView nameTextView;
    private TextView priceTextView;
    private Button addExpenseButton;
    private Spinner categorySpinner;

    /* Pobieranie domyślnych nazw i cen */
    private boolean shouldLoadDefaultValues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        /* Inicjaliza obiektu ustawień */
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        /* Inicjalizacja widoków  */
        addExpenseButton = (Button) findViewById(R.id.add_expense);
        nameTextView = (TextView) findViewById(R.id.expense_name);
        priceTextView = (TextView) findViewById(R.id.expense_price);
        categorySpinner = (Spinner) findViewById(R.id.expense_category);

        /* Ustawienie adaptera do Spinnera */
        categorySpinner.setAdapter(new CategoryAdapter(this));

        /* Jeśli zaznaczona opcja pamiętania ostatniej kategorii */
        if (shouldCareAboutLastCategory()){
            loadLastCategory(categorySpinner);
        }

        /* Jeśli zaznaczona opcja pobierania nazw i cen domyślnych*/
        shouldLoadDefaultValues = preferences.getBoolean(PREF_DEFAULT_VALUES, false);
        if (shouldLoadDefaultValues)
        {
            loadDefaultValues();
        }



        /* Dodanie Listnera do przycisku oraz dodanie nowego wydatku */
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewExpense();
            }

            private void addNewExpense(){
                /* Utworzenie obiektu Expense i dodanie go do bazy */
                String title = nameTextView.getText()   .toString();
                Double price = Double.parseDouble(priceTextView.getText().toString());
                ExpenseCategory category = (ExpenseCategory) categorySpinner.getSelectedItem();
                Expense expense = new Expense(title,price,category);
                ExpenseDatabase.addExpense(expense);
                /* Zapis wybranej kategorii */
                saveLastCategory(category);
                /* Załadowanie wiodku listy */
                Intent intent = new Intent(getApplicationContext(),ExpenseListActivity.class);
                startActivity(intent);
            }
        });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
        }
        return false;
    };

    /**
     * Załadowanie ostatnio wybranej kategorii do Spinnera
     * @param categorySpinner
     */
    private void loadLastCategory(Spinner categorySpinner)
    {
        String lastCategoryName = preferences.getString(PREF_LAST_CATEGORY, "");
        if (!lastCategoryName.isEmpty())
        {
            int categoryId = ExpenseCategory.getId(lastCategoryName);
            categorySpinner.setSelection(categoryId);
        }
    }

    /**
     * Zapisywanie ostatnio wybranej kategorii
     * @param expenseCategory
     */
    private void saveLastCategory(ExpenseCategory expenseCategory)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_LAST_CATEGORY, expenseCategory.name());
        editor.commit();
    }

    private boolean shouldCareAboutLastCategory(){
        return preferences.getBoolean(PREF_SAVE_CATEGORY, false);
    }

    /**
     * Ładowanie domyślnych wartości nazwy i ceny
     */
    private void loadDefaultValues()
    {
        /* Pobranie domyślnych wartości z preferences */
        String defName = preferences.getString(PREF_DEFAULT_NAME, "");
        String defPrice =  preferences.getString(PREF_DEFAULT_PRICE, "9.99");

        /* Ustawienie wartości w komponentach */
        nameTextView.setText(defName);
        priceTextView.setText(defPrice);
    }

}
