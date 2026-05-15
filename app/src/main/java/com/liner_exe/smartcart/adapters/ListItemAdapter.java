package com.liner_exe.smartcart.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.ListItem;
import com.liner_exe.domain.utils.formatters.QuantityFormatter;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.ItemListBinding;

import java.util.List;


public class ListItemAdapter extends BaseAdapter<ListItem, ItemListBinding> {
    public interface OnListItemActionListener {
        void onCheckbox(ListItem listItem);
        void onEdit(ListItem listItem);
    }

    private final OnListItemActionListener listener;

    public ListItemAdapter(OnListItemActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder<ItemListBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = ItemListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new BaseViewHolder<>(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(ItemListBinding binding, ListItem item) {
        binding.textProductName.setText(item.getProduct().getName());
        binding.textQuantity.setText(QuantityFormatter.format(item.getQuantity()) + " " + item.getUnit());
        binding.checkboxItem.setChecked(item.isBought());
        binding.checkboxItem.setOnClickListener(v -> {
            listener.onCheckbox(item);
        });
    }
}