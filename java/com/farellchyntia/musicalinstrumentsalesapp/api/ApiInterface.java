package com.farellchyntia.musicalinstrumentsalesapp.api;

import com.farellchyntia.musicalinstrumentsalesapp.model.UserResponse;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductResponse;
import com.farellchyntia.musicalinstrumentsalesapp.model.ProductListResponse;
import com.farellchyntia.musicalinstrumentsalesapp.model.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    // Existing user methods
    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> loginUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<UserResponse> registerUser(@Field("username") String username, @Field("password") String password, @Field("email") String email);

    // Product methods
    @FormUrlEncoded
    @POST("add_product.php")
    Call<ProductResponse> addProduct(@Field("name") String name,
                                     @Field("description") String description,
                                     @Field("price") double price,
                                     @Field("image_url") String imageUrl);

    @GET("get_products.php")
    Call<List<ProductListResponse>> getProducts();

    @FormUrlEncoded
    @POST("update_product.php")
    Call<ProductResponse> updateProduct(@Field("product_id") int productId,
                                        @Field("name") String name,
                                        @Field("description") String description,
                                        @Field("price") double price,
                                        @Field("image_url") String imageUrl);

    @FormUrlEncoded
    @POST("delete_product.php")
    Call<ProductResponse> deleteProduct(@Field("product_id") int productId);

    // Order methods
    @FormUrlEncoded
    @POST("create_order.php")
    Call<OrderResponse> createOrder(
            @Field("user_id") int userId,
            @Field("product_id") int productId,
            @Field("order_date") String orderDate
    );

    @FormUrlEncoded
    @POST("get_orders.php")
    Call<List<OrderResponse>> getUserOrders(@Field("user_id") int userId);
}
