package com.farellchyntia.musicalinstrumentsalesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farellchyntia.musicalinstrumentsalesapp.api.ApiClient;
import com.farellchyntia.musicalinstrumentsalesapp.api.ApiInterface;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(view -> startAddEditActivityForNewProduct());

        loadProducts();
    }

    private void startAddEditActivityForNewProduct() {
        Intent intent = new Intent(this, AddEditProductActivity.class);
        startActivity(intent);
    }

    private void loadProducts() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ProductListResponse>> call = apiInterface.getProducts();
        call.enqueue(new Callback<List<ProductListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductListResponse>> call, @NonNull Response<List<ProductListResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new ProductsAdapter(ProductListActivity.this, response.body(), ProductListActivity.this::onProductSelected);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ProductListActivity.this, "Failed to retrieve products.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductListResponse>> call, @NonNull Throwable t) {
                Toast.makeText(ProductListActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onProductSelected(ProductListResponse product) {
        Intent intent = new Intent(this, AddEditProductActivity.class);
        intent.putExtra("product_id", product.getProductId());
        startActivity(intent);
    }
}

