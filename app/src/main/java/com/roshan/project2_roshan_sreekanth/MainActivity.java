package com.roshan.project2_roshan_sreekanth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void redirectToLogin(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
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
}