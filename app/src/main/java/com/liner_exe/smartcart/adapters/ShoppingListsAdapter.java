package com.liner_exe.smartcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liner_exe.smartcart.R;
import com.liner_exe.domain.models.ShoppingList;

import java.util.List;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder> {
    private List<ShoppingList> shoppingLists;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ShoppingList shoppingList, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

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
                .inflate(R.layout.card_shopping_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);

        viewHolder.getListName().setText(shoppingList.getName());
        viewHolder.getProgressBar().setProgress(shoppingList.getProgress());
        viewHolder.getProgressText().setText(String.format("%d/%d",
                shoppingList.getChecked(),
                shoppingList.getProducts().size()));

        viewHolder.itemView.setOnClickListener(v ->{
            onItemClickListener.onItemClick(shoppingList, position);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }
}
