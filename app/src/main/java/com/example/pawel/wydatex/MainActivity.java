package com.example.pawel.wydatex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Inicjalizacja przycisków */
        Button addExpenseButton = (Button) findViewById(R.id.new_expense_button);
        Button listExpenseButton = (Button) findViewById(R.id.list_expense_btn);
        Button changePreferencesButton = (Button) findViewById(R.id.change_preferences_button);


        /* Dodanie listnerów do przycisków */
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
                startActivity(intent);
            }
        });
        listExpenseButton.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), ExpenseListActivity.class);
            startActivity(intent);
        });
        changePreferencesButton.setOnClickListener( (v) -> {
            Intent intent = new Intent(getApplicationContext(), PreferencesActivity.class);
            startActivity(intent);
        });
    }

}
