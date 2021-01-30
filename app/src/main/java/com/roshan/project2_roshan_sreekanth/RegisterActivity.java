package com.roshan.project2_roshan_sreekanth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.roshan.project2_roshan_sreekanth.helpers.DatabaseHelper;
import com.roshan.project2_roshan_sreekanth.models.CustomerModel;

public class RegisterActivity extends AppCompatActivity {

    EditText registerUsername;
    EditText registerPassword;
    EditText registerAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void createCustomer(View view)
    {
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerAddress = findViewById(R.id.registerAddress);

        String username = registerUsername.getText().toString();
        String password = registerPassword.getText().toString();
        String address = registerAddress.getText().toString();

        boolean allFilled = true;

        String errorMessage = "Required Field!";

        if(TextUtils.isEmpty(username))
        {
            registerUsername.setError(errorMessage);
            allFilled = false;
        }

        if(TextUtils.isEmpty(password))
        {
            registerPassword.setError(errorMessage);
            allFilled = false;
        }

        if(TextUtils.isEmpty(address))
        {
            registerAddress.setError(errorMessage);
            allFilled = false;
        }

        if(allFilled)
        {
            CustomerModel customer = new CustomerModel(username, password, address);
            DatabaseHelper handleCustomer = new DatabaseHelper(this);

            boolean result = handleCustomer.insertCustomer(customer);
            if (result) {
                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
            }

            handleCustomer.close();
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
                startActivity(storeIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}