package com.liner_exe.smartcart.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.liner_exe.domain.models.DiffIdentifiable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T extends DiffIdentifiable, VB extends ViewBinding>
        extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>> {
    protected List<T> items = new ArrayList<>();
    protected OnItemClickListener<T> onItemClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VB> holder, int position) {
        T item = items.get(position);
        bind(holder.binding, item);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item, position);
            }
        });
    }

    protected abstract void bind(VB binding, T item);

    public static class BaseViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {
        public final VB binding;

        public BaseViewHolder(@NonNull VB binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public final DiffUtil.ItemCallback<T> DIFF_CALLBACK = new DiffUtil.ItemCallback<T>() {
        @Override
        public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
            return oldItem.isContentTheSame(newItem);
        }
    };
}
