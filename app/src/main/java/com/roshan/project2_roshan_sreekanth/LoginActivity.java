package com.roshan.project2_roshan_sreekanth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.roshan.project2_roshan_sreekanth.helpers.DatabaseHelper;
import com.roshan.project2_roshan_sreekanth.models.CustomerModel;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername;
    EditText loginPassword;
    CustomerModel verifiedCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void authenticateUser(View view)
    {
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        String username = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();

        DatabaseHelper handleCustomer = new DatabaseHelper(this);
        ArrayList<CustomerModel> customerList = handleCustomer.getUsers(username, password);
        for(CustomerModel customer : customerList)
        {
            if(customer.getUsername().equals(username) && customer.getPassword().equals(password))
            {
                verifiedCustomer = customer;
                break;
            }
        }

        if(verifiedCustomer != null)
        {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, StoreActivity.class);
            intent.putExtra("username", loginUsername.getText().toString());
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Login Unsuccessful :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void redirectToRegister(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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
                loginIntent.putExtra("username", "Guest");
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

    public void redirectGuest(View view)
    {
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra("username", "Guest");
        startActivity(intent);
    }
}