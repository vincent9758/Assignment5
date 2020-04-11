package edu.auburn.comp3710.assignment5;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper Database;
    TextView balance;
    EditText date;
    EditText amount;
    EditText event;
    Button btnAdd;
    Button btnSub;
    TableLayout history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database = new DatabaseHelper(this);

        balance = findViewById(R.id.balance);
        date =  findViewById(R.id.editDate);
        amount = findViewById(R.id.editAmount);
        event = findViewById(R.id.editEvent);
        btnAdd =  findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        history =  findViewById(R.id.table);
        AddEvent();
        GetHistory();
    }

    public void AddEvent(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double Amount = Double.parseDouble(amount.getText().toString());
                        Database.Create(date.getText().toString(), Amount, event.getText().toString());
                        GetHistory();
                        MainActivity.this.date.setText("");
                        MainActivity.this.amount.setText("");
                        MainActivity.this.event.setText("");
                    }
                }
        );

        btnSub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double Amount =  Double.parseDouble(amount.getText().toString());
                        Database.Create(date.getText().toString(), Amount, event.getText().toString());
                        GetHistory();
                        MainActivity.this.date.setText("");
                        MainActivity.this.amount.setText("");
                        MainActivity.this.event.setText("");
                    }
                }
        );
    }

    public void GetHistory(){
        Cursor result = Database.ReadData();
        Double balance = 0.0;

        if (history.getChildCount() > 0) {
            history.removeAllViews();

        }

        while(result.moveToNext()){
            double price = Double.parseDouble(result.getString(2));
            balance += price;
            TableRow t = new TableRow(this);
            TableRow.LayoutParams cl = new TableRow.LayoutParams();
            cl.weight = 1;

            TextView D = new TextView(this);
            D.setLayoutParams(cl);
            D.setText(result.getString(1));
            t.addView(D);

            TextView amount = new TextView(this);
            amount.setLayoutParams(cl);
            amount.setText(result.getString(2));
            t.addView(amount);

            TextView categroy = new TextView(this);
            categroy.setLayoutParams(cl);
            categroy.setText(result.getString(3));
            t.addView(categroy);

            history.addView(t, new TableLayout.LayoutParams());

        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
    }
}
