package com.liner_exe.smartcart.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.liner_exe.domain.models.Category;
import com.liner_exe.smartcart.databinding.ItemCategoryBinding;

public class CategoriesManagementAdapter extends BaseAdapter<Category, ItemCategoryBinding> {
    @Override
    protected void bind(ItemCategoryBinding binding, Category category) {
        binding.categoryItemTitle.setText(category.getName());
        binding.categoryItemEmoji.setText(category.getEmoji());
    }

    @NonNull
    @Override
    public BaseViewHolder<ItemCategoryBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new BaseViewHolder<>(binding);
    }
}