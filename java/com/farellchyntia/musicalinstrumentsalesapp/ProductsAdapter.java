package com.farellchyntia.musicalinstrumentsalesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farellchyntia.musicalinstrumentsalesapp.api.ApiClient;
import com.farellchyntia.musicalinstrumentsalesapp.api.ApiInterface;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductListResponse;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductResponse;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private final Context context;
    private List<ProductListResponse> productList;
    private final OnProductClickListener listener;

    public ProductsAdapter(Context context, List<ProductListResponse> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    public void updateData(List<ProductListResponse> newProductList) {
        productList.clear();
        productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductListResponse product = productList.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        // Replace delete button with order button functionality
        holder.orderButton.setText("Order");
        holder.orderButton.setOnClickListener(v -> {
            createOrder(product.getProductId());
        });
        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
    }

    private void createOrder(int productId) {
        SharedPreferences prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1); // Assume you store userId when user logs in
        if (userId != -1) {
            // Assume you have a method in your API to create orders
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            // Change the method signature in your ApiInterface to accept the required parameters
            apiInterface.createOrder(userId, productId, "order_date_placeholder").enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Order placed successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to place order", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrice;
        Button orderButton;  // Renamed from deleteButton

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewProductName);
            textViewPrice = itemView.findViewById(R.id.textViewProductPrice);
            orderButton = itemView.findViewById(R.id.orderButton); // Assuming you have changed the ID in the layout file
        }
    }

    public interface OnProductClickListener {
        void onProductClick(ProductListResponse product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
