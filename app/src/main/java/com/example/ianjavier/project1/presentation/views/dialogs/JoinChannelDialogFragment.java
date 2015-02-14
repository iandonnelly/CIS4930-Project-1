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

public class JoinChannelDialogFragment extends DialogFragment {
    public interface JoinChannelDialogListener {
        public void onJoinChannelDialogPositiveClicked(String channel);
    }

    private JoinChannelDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_join_channel, null);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.join_channel_title)
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
                        EditText channelField = (EditText) view.findViewById(R.id.channel);

                        // Get inputted values
                        String channel = channelField.getText().toString();

                        // Ensure inputted values
                        if (TextUtils.isEmpty(channel)) {
                            channelField.setError("Invalid channel");
                            return;
                        }

                        mListener.onJoinChannelDialogPositiveClicked(channel);
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
            mListener = (JoinChannelDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement JoinChannelDialogListener");
        }
    }
}
