package com.roshan.project2_roshan_sreekanth.ui.main;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.roshan.project2_roshan_sreekanth.BasketActivity;
import com.roshan.project2_roshan_sreekanth.R;
import com.roshan.project2_roshan_sreekanth.helpers.DatabaseHelper;
import com.roshan.project2_roshan_sreekanth.models.ProductModel;

import java.util.ArrayList;

public class RecyclerBasketAdapter extends RecyclerView.Adapter<RecyclerBasketAdapter.MyViewHolder>
{
    Context context;
    ArrayList<ProductModel> basketProducts;
    DatabaseHelper dbHelper;

    public RecyclerBasketAdapter(Context ct, ArrayList<ProductModel> basketProducts)
    {
        this.context = ct;
        this.basketProducts = basketProducts;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public RecyclerBasketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.basket_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBasketAdapter.MyViewHolder holder, int position) {
        holder.productNameText.setText(basketProducts.get(position).getProductName());
        holder.productCodeText.setText(basketProducts.get(position).getProductCode());

        int quantity = dbHelper.getQuantity(basketProducts.get(position));
        System.out.println("QUANTITY IS " + quantity);
        holder.productQuantityText.setText(" x" + Integer.toString(quantity));

        float price = quantity * basketProducts.get(position).getProductPrice();
        holder.productPriceText.setText(Float.toString(price));

        int image = context.getResources().getIdentifier(basketProducts.get(position).getProductCode().toLowerCase(), "drawable", context.getPackageName());
        holder.productImage.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return basketProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {
        private TextView productNameText;
        private TextView productCodeText;
        private TextView productPriceText;
        private TextView productQuantityText;
        private Button clearButton;
        private ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameText =  itemView.findViewById(R.id.productName);
            productCodeText = itemView.findViewById(R.id.productCode);
            productPriceText = itemView.findViewById(R.id.productPrice);
            productQuantityText = itemView.findViewById(R.id.productQuantity);
            productImage = itemView.findViewById(R.id.productImage);

            itemView.setOnCreateContextMenuListener(this);
            clearButton = ((Activity)context).findViewById(R.id.clearButton);
            clearButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    dbHelper.deleteAllItems();
                    basketProducts.clear();
                    notifyDataSetChanged();
                    notifyItemChanged(getAdapterPosition());
                    updatePrice();
                    Toast.makeText(context, "Basket cleared!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem myActionItem = contextMenu.add("Delete");
            myActionItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            dbHelper.deleteItem(productCodeText.getText().toString());
            basketProducts.remove(getAdapterPosition());
            notifyDataSetChanged();
            notifyItemChanged(getAdapterPosition());
            updatePrice();
            return true;
        }

        public void updatePrice()
        {
            TextView totalPriceText = ((Activity)context).findViewById(R.id.totalPriceText);
            float totalPrice = 0;
            for(ProductModel product:basketProducts)
            {
                totalPrice += product.getProductPrice() * dbHelper.getQuantity(product);
            }
            String totalPriceString = "Total €" + totalPrice;
            totalPriceText.setText(totalPriceString);
        }
    }
}
