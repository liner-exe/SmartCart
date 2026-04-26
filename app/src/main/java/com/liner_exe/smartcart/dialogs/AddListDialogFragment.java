package com.liner_exe.smartcart.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.liner_exe.domain.models.ShoppingList;
import com.liner_exe.smartcart.R;

public class AddListDialogFragment extends DialogFragment {
    public interface OnListAddedListener {
        void onAdd(String name);
    }

    private OnListAddedListener listener;

    public static AddListDialogFragment newInstance(OnListAddedListener listener) {
        AddListDialogFragment fragment = new AddListDialogFragment();
        fragment.listener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        TextInputEditText editText = view.findViewById(R.id.edit_list_name);
        TextInputLayout inputLayout = view.findViewById(R.id.list_add_input_layout);

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_add_list_title)
                .setMessage(R.string.dialog_add_list_message)
                .setView(view)
                .setPositiveButton(R.string.action_add, null)
                .setNegativeButton(R.string.action_cancel, null)
                .create();

        editText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptSave(editText, inputLayout, dialog);

                return true;
            }

            return false;
        });


        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            editText.requestFocus();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputLayout.setError(null);
                inputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (button != null) {
                button.setOnClickListener(v -> {
                    attemptSave(editText, inputLayout, dialog);
                });
            }
        });

        return dialog;
    }

    private void attemptSave(TextInputEditText editText, TextInputLayout inputLayout,
                             AlertDialog dialog) {
        String name = editText.getText() != null ? editText.getText().toString().trim() : "";

        if (name.isEmpty()) {
            inputLayout.setError(getString(R.string.dialog_add_list_error_empty));
            inputLayout.setErrorEnabled(true);
        } else {
            if (listener != null) {
                listener.onAdd(name);
            }
            dialog.dismiss();
        }
    }
}
