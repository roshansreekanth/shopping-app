package com.roshan.project2_roshan_sreekanth.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roshan.project2_roshan_sreekanth.R;
import com.roshan.project2_roshan_sreekanth.helpers.DatabaseHelper;
import com.roshan.project2_roshan_sreekanth.models.ProductModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    Context mContext;
    List<ProductModel> mData;

    public RecyclerViewAdapter(Context mContext, List<ProductModel> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_row, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.productNameText.setText(mData.get(position).getProductName());
        holder.productCodeText.setText(mData.get(position).getProductCode());
        holder.productPriceText.setText("€ " + mData.get(position).getProductPrice());
        int image = mContext.getResources().getIdentifier(mData.get(position).getProductCode().toLowerCase(), "drawable", mContext.getPackageName());
        holder.productImage.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView productNameText;
        private TextView productCodeText;
        private TextView productPriceText;
        private Button addButton;
        private ImageView productImage;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            productNameText = itemView.findViewById(R.id.productName);
            productCodeText = itemView.findViewById(R.id.productCode);
            productPriceText = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            addButton = itemView.findViewById(R.id.addButton);
            addButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            DatabaseHelper productHelper = new DatabaseHelper(itemView.getContext());

            String productName = productNameText.getText().toString();
            String productCode = productCodeText.getText().toString();
            String productPriceString = productPriceText.getText().toString();
            float productPrice = Float.parseFloat(productPriceString.replace("€ ", ""));

            ProductModel product = new ProductModel(productName, productCode, productPrice);
            productHelper.insertProduct(product);
            productHelper.close();
        }
    }
}
