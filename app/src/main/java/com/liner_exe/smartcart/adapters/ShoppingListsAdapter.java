package com.liner_exe.smartcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.databinding.CardShoppingListBinding;

import java.util.List;

public class ShoppingListsAdapter extends BaseAdapter<ShoppingList, CardShoppingListBinding> {
    public interface OnShoppingListActionListener {
        void onRename(ShoppingList shoppingList);

        void onDelete(ShoppingList shoppingList);
    }

    private final OnShoppingListActionListener listener;

    public ShoppingListsAdapter(OnShoppingListActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder<CardShoppingListBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardShoppingListBinding binding = CardShoppingListBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new BaseViewHolder<>(binding);
    }

    private void showPopupMenu(View view, ShoppingList shoppingList) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.list_context_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_rename) {
                listener.onRename(shoppingList);
                return true;
            } else if (id == R.id.action_delete_list) {
                listener.onDelete(shoppingList);
                return true;
            }

            return false;
        });

        popupMenu.show();
    }

    @Override
    protected void bind(CardShoppingListBinding binding, ShoppingList list) {
        binding.shoppingListName.setText(list.getName());
        binding.shoppingListProgressText.setText(list.getProgressString());
        binding.shoppingListButtonMore.setOnClickListener(v -> showPopupMenu(v, list));

        binding.shoppingListProgressBar.setProgress(list.getBoughtItems());
        binding.shoppingListProgressBar.setMax(list.getTotalItems());
    }
}
