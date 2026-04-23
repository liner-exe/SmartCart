package com.liner_exe.smartcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.Product;
import com.liner_exe.smartcart.R;

import java.util.List;

public class ProductsManagementAdapter extends RecyclerView.Adapter<ProductsManagementAdapter.ViewHolder> {
    private List<Product> products;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView productName;

        public ViewHolder(@NonNull View view) {
            super(view);

            this.productName = view.findViewById(R.id.product_item_title);
        }

        public TextView getProductName() {
            return productName;
        }
    }

    public ProductsManagementAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new ProductsManagementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getProductName().setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
