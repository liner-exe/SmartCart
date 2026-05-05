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

import java.util.List;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {
    public interface OnShoppingListActionListener {
        void onRename(ShoppingList shoppingList);

        void onDelete(ShoppingList shoppingList);
    }

    private final OnShoppingListActionListener listener;

    private List<ShoppingList> shoppingLists;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ShoppingList shoppingList, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ShoppingListsAdapter(List<ShoppingList> shoppingLists, OnShoppingListActionListener listener) {
        this.shoppingLists = shoppingLists;
        this.listener = listener;
        notifyDataSetChanged();
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_shopping_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);

        viewHolder.getListName().setText(shoppingList.getName());
        viewHolder.getButtonMore().setOnClickListener(v -> showPopupMenu(v, shoppingList));
        viewHolder.getProgressText().setText(shoppingList.getProgressString());

        viewHolder.getProgressBar().setMax(shoppingList.getTotalItems());
        viewHolder.getProgressBar().setProgress(shoppingList.getBoughtItems());

        viewHolder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(shoppingList, position);
        });
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView listName;
        private final ProgressBar progressBar;
        private final TextView progressText;
        private final ImageButton buttonMore;

        public ViewHolder(@NonNull View view) {
            super(view);

            this.listName = view.findViewById(R.id.shopping_list_name);
            this.progressBar = view.findViewById(R.id.shopping_list_progress_bar);
            this.progressText = view.findViewById(R.id.shopping_list_progress_text);
            this.buttonMore = view.findViewById(R.id.shopping_list_button_more);
        }

        public TextView getListName() {
            return listName;
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public TextView getProgressText() {
            return progressText;
        }

        public ImageButton getButtonMore() {
            return buttonMore;
        }
    }
}
