package com.roshan.project2_roshan_sreekanth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roshan.project2_roshan_sreekanth.helpers.DatabaseHelper;
import com.roshan.project2_roshan_sreekanth.models.ProductModel;
import com.roshan.project2_roshan_sreekanth.ui.main.RecyclerBasketAdapter;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView totalPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        recyclerView = findViewById(R.id.recyclerView);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<ProductModel> basketProducts = dbHelper.getProducts();
        RecyclerBasketAdapter recyclerBasketAdapter = new RecyclerBasketAdapter(this, basketProducts);

        recyclerView.setAdapter(recyclerBasketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        float totalPrice = 0;
        for(ProductModel product : basketProducts)
        {
            totalPrice += product.getProductPrice() * dbHelper.getQuantity(product);
        }

        totalPriceText = findViewById(R.id.totalPriceText);
        totalPriceText.setText("Total €" + totalPrice);
    }

    public void redirectToStore(View view)
    {
        Intent intent = new Intent(this, StoreActivity.class);
        if(getIntent().hasExtra("username"))
        {
            intent.putExtra("username", getIntent().getStringExtra("username"));
        }
        startActivity(intent);
    }

    public void redirectToPayment(View view)
    {
        if(totalPriceText.getText().toString().equals("Total €0.0"))
        {
            Toast.makeText(this, "Basket is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, PaymentActivity.class);
            if(getIntent().hasExtra("username"))
            {
                intent.putExtra("username", getIntent().getStringExtra("username"));
            }
            startActivity(intent);
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