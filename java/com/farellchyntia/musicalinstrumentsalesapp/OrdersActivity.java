package com.farellchyntia.musicalinstrumentsalesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farellchyntia.musicalinstrumentsalesapp.api.ApiClient;
import com.farellchyntia.musicalinstrumentsalesapp.api.ApiInterface;
import com.farellchyntia.musicalinstrumentsalesapp.model.OrderResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView ordersRecyclerView;
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersAdapter = new OrdersAdapter(this, new ArrayList<>());
        ordersRecyclerView.setAdapter(ordersAdapter);

        loadOrders();
    }

    private void loadOrders() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);

        if (userId != -1) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<OrderResponse>> call = apiInterface.getUserOrders(userId);
            call.enqueue(new Callback<List<OrderResponse>>() {
                @Override
                public void onResponse(Call<List<OrderResponse>> call, Response<List<OrderResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ordersAdapter.updateData(response.body());
                    } else {
                        Toast.makeText(OrdersActivity.this, "Failed to retrieve orders.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<OrderResponse>> call, Throwable t) {
                    Toast.makeText(OrdersActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "User ID not found. Please login again.", Toast.LENGTH_LONG).show();
        }
    }
}
