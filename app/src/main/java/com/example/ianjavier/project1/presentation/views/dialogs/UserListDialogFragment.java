package com.example.ianjavier.project1.presentation.views.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.example.ianjavier.project1.App;
import com.example.ianjavier.project1.R;

public class UserListDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] userList = getArguments().getStringArray(App.getContext().getResources().getString(R.string.user_list));

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.user_list)
                .setItems(userList, null)
                .setPositiveButton(App.getContext().getResources().getString(R.string.ok), null)
                .create();
        return dialog;
    }
}
