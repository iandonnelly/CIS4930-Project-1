package com.example.ianjavier.project1.presentation.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.domain.OnMessageReceivedListener;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment implements OnMessageReceivedListener {
    private ArrayAdapter mAdapter;
    private Handler mHandler;

    protected abstract int getLayoutResource();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<>(getActivity(),R.layout.message_log_row_layout,
                new ArrayList<String>());

        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ((AbsListView) view.findViewById(android.R.id.list)).setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onMessageReceived(final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.add(message);
            }
        });
    }
}
