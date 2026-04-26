package com.liner_exe.smartcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.Product;
import com.liner_exe.smartcart.R;

import java.util.List;

public class ProductsManagementAdapter
        extends RecyclerView.Adapter<ProductsManagementAdapter.ProductsManagementViewHolder> {
    public interface OnProductActionListener {
        void onEdit(Product product);

        void onDelete(Product product);
    }

    private final OnProductActionListener listener;

    private List<Product> products;

    public ProductsManagementAdapter(List<Product> products, OnProductActionListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    @NonNull
    @Override
    public ProductsManagementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_product, viewGroup, false);
        return new ProductsManagementAdapter.ProductsManagementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsManagementViewHolder viewHolder, int position) {
        Product product = products.get(position);
        viewHolder.getProductName().setText(product.getName());

        viewHolder.getButtonMore().setOnClickListener(v -> {
            showPopupMenu(v, product);
        });
    }

    private void showPopupMenu(View view, Product product) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.product_context_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.action_edit) {
                listener.onEdit(product);
                return true;
            } else if (id == R.id.action_delete_product) {
                listener.onDelete(product);
                return true;
            }

            return false;
        });

        popupMenu.show();
    }

    public static class ProductsManagementViewHolder extends RecyclerView.ViewHolder {
        private final TextView productName;
        private final ImageButton buttonMore;

        public ProductsManagementViewHolder(@NonNull View view) {
            super(view);

            this.productName = view.findViewById(R.id.product_item_title);
            this.buttonMore = view.findViewById(R.id.product_item_button_more);
        }

        public TextView getProductName() {
            return productName;
        }

        public ImageButton getButtonMore() {
            return buttonMore;
        }
    }
}
