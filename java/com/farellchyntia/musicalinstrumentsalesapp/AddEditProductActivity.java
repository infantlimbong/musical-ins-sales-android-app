package com.farellchyntia.musicalinstrumentsalesapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.farellchyntia.musicalinstrumentsalesapp.api.ApiClient;
import com.farellchyntia.musicalinstrumentsalesapp.api.ApiInterface;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        EditText editTextName = findViewById(R.id.editTextProductName);
        EditText editTextDescription = findViewById(R.id.editTextProductDescription);
        EditText editTextPrice = findViewById(R.id.editTextProductPrice);
        EditText editTextImageUrl = findViewById(R.id.editTextProductImageUrl);
        Button buttonSave = findViewById(R.id.buttonSaveProduct);

        int productId = getIntent().getIntExtra("product_id", 0);

        buttonSave.setOnClickListener(view -> saveProduct(editTextName, editTextDescription, editTextPrice, editTextImageUrl, productId));
    }

    private void saveProduct(EditText editTextName, EditText editTextDescription, EditText editTextPrice, EditText editTextImageUrl, int productId) {
        String name = editTextName.getText().toString();
        String description = editTextDescription.getText().toString();
        double price = Double.parseDouble(editTextPrice.getText().toString());
        String imageUrl = editTextImageUrl.getText().toString();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call = (productId == 0) ?
                apiInterface.addProduct(name, description, price, imageUrl) :
                apiInterface.updateProduct(productId, name, description, price, imageUrl);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AddEditProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity on success
                } else {
                    Toast.makeText(AddEditProductActivity.this, "Failed to save product.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                Toast.makeText(AddEditProductActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
