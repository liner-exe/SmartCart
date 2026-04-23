package com.liner_exe.smartcart.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.liner_exe.smartcart.R;
import com.liner_exe.domain.models.Product;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Product[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textAmount;
        private final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);

            textName = (TextView) view.findViewById(R.id.text_product_name);
            textAmount = (TextView) view.findViewById(R.id.text_quantity);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox_item);
        }

        public TextView getTextName() {
            return textName;
        }

        public TextView getTextAmount() {
            return textAmount;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }

    public ProductAdapter(Product[] products) {
        localDataSet = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getTextName().setText(localDataSet[position].getName());
        viewHolder.getTextAmount().setText(Integer.toString(localDataSet[position].getAmount()) + " шт.");
        viewHolder.getCheckBox().setChecked(localDataSet[position].isChecked());
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}