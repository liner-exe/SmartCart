package com.liner_exe.smartcart.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.smartcart.R;

import java.util.List;


public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {
    public interface OnListItemActionListener {
        void onCheckbox(ListItem listItem);
    }

    private final OnListItemActionListener listener;

    private List<ListItem> listItems;

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

    public ListItemAdapter(List<ListItem> listItems, OnListItemActionListener listener) {
        this.listItems = listItems;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public void setListItems(List<ListItem> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ListItem listItem = listItems.get(position);

        viewHolder.getTextName().setText(listItem.getProduct().getName());
        viewHolder.getTextAmount().setText(listItem.getQuantity() + " шт.");
        viewHolder.getCheckBox().setChecked(listItem.isBought());

        viewHolder.getCheckBox().setOnClickListener(v -> {
            listener.onCheckbox(listItem);
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}