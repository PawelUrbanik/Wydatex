package com.example.pawel.wydatex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ExpenseListActivity extends Activity {

    private ListView expenseListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.add_new_expense:
                intent = new Intent(getApplicationContext(), ExpenseActivity.class);
                startActivity(intent);
                return true;
            case  R.id.preferences:
                intent = new Intent(getApplicationContext(), PreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onMenuItemSelected(featureId,item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        expenseListView = (ListView) findViewById(R.id.expense_list);
        expenseListView.setAdapter(new ExpenseListAdapter());

        /* Przyciski pod listą produktów */
        Button addExpenseButton = (Button) findViewById(R.id.add_expense_btn);
        Button changePreferencesButton = (Button) findViewById(R.id.change_preferences_button);


        /* Dodanie listnerów do przycisków */
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExpenseActivity.class);
                startActivity(intent);
            }
        });
        changePreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PreferencesActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        expenseListView.invalidateViews();
    }



    private class ExpenseListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ExpenseDatabase.getExpenses().size();
        }

        @Override
        public Expense getItem(int position) {
            return ExpenseDatabase.getExpenses().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.list_item_expense,parent,false);
            }
            TextView name  = (TextView) convertView.findViewById(R.id.name);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            TextView category = (TextView) convertView.findViewById(R.id.category);

            Expense item = getItem(position);
            name.setText(item.getName());
            price.setText(String.format("%.2f",item.getPrice())+ "zł");
            category.setText(item.getCategory().getName());
            return convertView;
        }
    }
}
