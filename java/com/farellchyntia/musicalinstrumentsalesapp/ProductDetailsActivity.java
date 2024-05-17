package com.farellchyntia.musicalinstrumentsalesapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ImageView imageView = findViewById(R.id.imageProduct);
        TextView nameTextView = findViewById(R.id.textViewProductName);
        TextView priceTextView = findViewById(R.id.textViewProductPrice);
        TextView descriptionTextView = findViewById(R.id.textViewProductDescription);

        // Retrieving data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("productName");
            String price = extras.getString("productPrice"); // Assuming price is passed as a formatted string
            String description = extras.getString("productDescription");
            String imageUrl = extras.getString("productImageUrl");

            nameTextView.setText(name);
            priceTextView.setText(price); // Directly using the string
            descriptionTextView.setText(description);

            Picasso.get().load(imageUrl).into(imageView);
        }
    }
}
