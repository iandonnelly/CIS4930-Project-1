package com.example.ianjavier.project1.presentation.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.example.ianjavier.project1.R;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class BaseFragment extends Fragment implements Observer {
    private ArrayAdapter mAdapter;

    protected abstract int getLayoutResource();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<>(getActivity(),R.layout.message_log_row_layout,
                new ArrayList<String>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ((AbsListView) view.findViewById(android.R.id.list)).setAdapter(mAdapter);
        return view;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            String message = (String) data;
            mAdapter.add(message);
        }
    }
}
