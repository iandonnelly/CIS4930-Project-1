package com.example.ianjavier.project1.presentation.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.views.ClientFragmentListener;


public class ClientFragment extends BaseFragment {
    private ClientFragmentListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        ImageButton sendMessage = (ImageButton) view.findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageInput = (EditText) view.findViewById(R.id.messageInput);
                String message = messageInput.getText().toString();

                if (!TextUtils.isEmpty(message)) {
                    messageInput.getText().clear();
                    mListener.onSendMessageClicked(message);
                }
            }
        });
        return view;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_client;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ClientFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ClientFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
