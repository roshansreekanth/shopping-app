package com.roshan.project2_roshan_sreekanth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {
    EditText cardNumberText;
    MotionLayout motionEditor;
    TextView confirmationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    public void getPaymentDetails(View view)
    {
        cardNumberText = findViewById(R.id.cardNumberText);
        motionEditor = findViewById(R.id.motionEditor);
        confirmationText = findViewById(R.id.confirmationText);

        String cardNumber = cardNumberText.getText().toString();
        String errorMessage = "Required Field!";

        if(TextUtils.isEmpty(cardNumber))
        {
            cardNumberText.setError(errorMessage);
        }
        else
        {
            motionEditor.transitionToEnd();
            confirmationText.setText("Order confirmed!");
            cardNumberText.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.login_item:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                return true;

            case R.id.basket_item:
                Intent basketIntent = new Intent(this, BasketActivity.class);
                startActivity(basketIntent);
                return true;
            case R.id.home_item:
                Intent storeIntent = new Intent(this, StoreActivity.class);
                if(getIntent().hasExtra("username"))
                {
                    storeIntent.putExtra("username", getIntent().getStringExtra("username"));
                }
                startActivity(storeIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}