package com.farellchyntia.musicalinstrumentsalesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.farellchyntia.musicalinstrumentsalesapp.model.OrderResponse;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private Context context;
    private List<OrderResponse> ordersList;

    public OrdersAdapter(Context context, List<OrderResponse> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderResponse order = ordersList.get(position);
        holder.textViewOrderId.setText(String.format("Order ID: %d", order.getOrderId()));
        holder.textViewUserId.setText(String.format("User ID: %d", order.getUserId()));
        holder.textViewProductId.setText(String.format("Product ID: %d", order.getProductId()));
        holder.textViewOrderDate.setText(String.format("Order Date: %s", order.getOrderDate()));
    }

    @Override
    public int getItemCount() {
        return ordersList != null ? ordersList.size() : 0;
    }

    public void updateData(List<OrderResponse> newOrdersList) {
        this.ordersList.clear();
        this.ordersList.addAll(newOrdersList);
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId, textViewUserId, textViewProductId, textViewOrderDate;

        public OrderViewHolder(View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewUserId = itemView.findViewById(R.id.textViewUserId);
            textViewProductId = itemView.findViewById(R.id.textViewProductId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
        }
    }
}
