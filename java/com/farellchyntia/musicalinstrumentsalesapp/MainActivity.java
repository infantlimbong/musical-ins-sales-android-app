package com.farellchyntia.musicalinstrumentsalesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farellchyntia.musicalinstrumentsalesapp.api.ApiClient;
import com.farellchyntia.musicalinstrumentsalesapp.api.ApiInterface;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        if (!isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductsAdapter(MainActivity.this, new ArrayList<>(), this::onProductSelected);
        recyclerView.setAdapter(adapter);

        loadProducts();

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> logout());

        findViewById(R.id.fabAddProduct).setOnClickListener(this::onAddProductClick);
        // Floating Action Button for viewing orders
        findViewById(R.id.fabViewOrders).setOnClickListener(this::viewOrders);
    }

    private void loadProducts() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListResponse>> call = apiInterface.getProducts();
        call.enqueue(new Callback<List<ProductListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductListResponse>> call, @NonNull Response<List<ProductListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve products.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductListResponse>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onAddProductClick(View view) {
        startActivity(new Intent(this, AddEditProductActivity.class));
    }

    private void onProductSelected(ProductListResponse product) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("productId", product.getProductId());
        intent.putExtra("productName", product.getName());
        intent.putExtra("productPrice", product.getPrice());
        intent.putExtra("productDescription", product.getDescription());
        startActivity(intent);
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void viewOrders(View view) {
        // Intent to start an activity that shows orders
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
    }

    public void onViewOrdersClick(View view) {
        startActivity(new Intent(this, OrdersActivity.class));
    }
}
