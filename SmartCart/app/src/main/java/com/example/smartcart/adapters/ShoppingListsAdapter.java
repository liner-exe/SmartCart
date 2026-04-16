package com.example.smartcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcart.R;
import com.example.domain.models.ShoppingList;

import java.util.List;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView listName;
        private final ProgressBar progressBar;
        private final TextView progressText;

        public ViewHolder(@NonNull View view) {
            super(view);

            this.listName = view.findViewById(R.id.shopping_list_name);
            this.progressBar = view.findViewById(R.id.shopping_list_progress_bar);
            this.progressText = view.findViewById(R.id.shopping_list_progress_text);
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
    }

    public ShoppingListsAdapter(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopping_list_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getListName().setText(shoppingLists.get(position).getName());
        viewHolder.getProgressBar().setProgress(shoppingLists.get(position).getProgress());
        viewHolder.getProgressText().setText(String.format("%d/%d",
                shoppingLists.get(position).getChecked(),
                shoppingLists.get(position).getProducts().size()));
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }
}
