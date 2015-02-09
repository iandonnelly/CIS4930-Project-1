package com.example.ianjavier.project1.presentation.views.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ianjavier.project1.R;

public class CreateServerDialogFragment extends DialogFragment {
    public interface CreateServerDialogListener {
        public void onCreateServerDialogPositiveClicked(String name, int port);
    }

    private CreateServerDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_create_server, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.server_settings)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get input fields
                        EditText nameField = (EditText) view.findViewById(R.id.name);
                        EditText portField = (EditText) view.findViewById(R.id.port);

                        // Get inputted values
                        String name = nameField.getText().toString();
                        String port = portField.getText().toString();

                        // Ensure inputted values
                        if (TextUtils.isEmpty(name)) {
                            nameField.setError("Invalid IP address");
                            return;
                        }

                        if (TextUtils.isEmpty(port)) {
                            portField.setError("Invalid port");
                            return;
                        }

                        mListener.onCreateServerDialogPositiveClicked(name, Integer.parseInt(port));
                        dialog.dismiss();
                    }
                });
            }
        });

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (CreateServerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CreateServerDialogListener");
        }
    }
}