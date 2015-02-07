package com.example.ianjavier.project1.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ianjavier.project1.R;

public class JoinServerDialogFragment extends DialogFragment {
    public interface JoinServerDialogListener {
        public void onJoinServerDialogPositiveClicked(String address, int port, String nickname);
    }

    private JoinServerDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_join_server, null);

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
                        EditText addressField = (EditText) view.findViewById(R.id.address);
                        EditText portField = (EditText) view.findViewById(R.id.port);
                        EditText nicknameField = (EditText) view.findViewById(R.id.nickname);

                        // Get inputted values
                        String address = addressField.getText().toString();
                        String port = portField.getText().toString();
                        String nickname = nicknameField.getText().toString();

                        // Ensure inputted values
                        if (address.trim().length() == 0) {
                            addressField.setError("Invalid IP address");
                            return;
                        }

                        if (port.trim().length() == 0) {
                            portField.setError("Invalid port");
                            return;
                        }

                        if (nickname.trim().length() == 0) {
                            nicknameField.setError("Invalid nickname");
                            return;
                        }

                        mListener.onJoinServerDialogPositiveClicked(address,
                                Integer.parseInt(port), nickname);
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
            mListener = (JoinServerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement JoinServerDialogListener");
        }
    }
}
