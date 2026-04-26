package com.liner_exe.smartcart.fragments;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.smartcart.R;
import com.liner_exe.smartcart.adapters.ShoppingListsAdapter;
import com.liner_exe.smartcart.databinding.FragmentHomeBinding;
import com.liner_exe.smartcart.viewmodel.ShoppingViewModel;

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentHome extends Fragment {
    private FragmentHomeBinding binding;
    private ShoppingListsAdapter adapter;
    private ShoppingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ShoppingViewModel.class);

        RecyclerView recyclerView = binding.recyclerView;
        adapter = new ShoppingListsAdapter(Collections.emptyList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((shoppingList, position) -> {
            NavController navController = Navigation.findNavController(requireActivity(),
                    R.id.main_nav_host);

            Bundle bundle = new Bundle();
            bundle.putString("listName", shoppingList.getName());
            navController.navigate(R.id.action_mainFragment_to_fragmentList, bundle);
        });

        viewModel.shoppingLists.observe(getViewLifecycleOwner(), newLists -> {
            if (newLists != null) {
                adapter.setShoppingLists(newLists);
            }
        });

        bindDialog();
    }

    private void bindDialog() {
        binding.fab.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
            TextInputEditText editText = dialogView.findViewById(R.id.edit_list_name);
            TextInputLayout inputLayout = (TextInputLayout) editText.getParent().getParent();

            editText.requestFocus();

            AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Новый список")
                    .setMessage("Введите название списка")
                    .setView(dialogView)
                    .setPositiveButton("Добавить", null)
                    .setNegativeButton("Отмена", null)
                    .create();

            if (dialog.getWindow() != null) {
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }

            dialog.show();

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    inputLayout.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {}

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            });


            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                attemptSave(editText, inputLayout, dialog);
            });

            editText.setOnEditorActionListener((textView, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptSave(editText, inputLayout, dialog);
                    return true;
                }
                return false;
            });
        });
    }

    private void attemptSave(TextInputEditText editText, TextInputLayout inputLayout,
                             AlertDialog dialog) {
        if (editText.getText() != null) {
            String name = editText.getText().toString().trim();

            if (name.isEmpty()) {
                inputLayout.setError("Название не может быть пустым!");
                inputLayout.setErrorEnabled(true);
            } else {
                inputLayout.setError(null);
                inputLayout.setErrorEnabled(false);
                viewModel.addList(new ShoppingList(name));
                dialog.dismiss();
            }
        }
    }
}