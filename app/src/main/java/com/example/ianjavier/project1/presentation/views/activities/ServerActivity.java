package com.example.ianjavier.project1.presentation.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.presenters.BasePresenter;
import com.example.ianjavier.project1.presentation.presenters.ServerPresenter;
import com.example.ianjavier.project1.presentation.presenters.ServerPresenterImpl;
import com.example.ianjavier.project1.presentation.views.fragments.ServerFragment;

public class ServerActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String name = getIntent().getStringExtra(getString(R.string.name));
        int port = getIntent().getIntExtra(getString(R.string.port), 0);


        ((ServerPresenter) super.mPresenter).onStart(name, port);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_server;
    }

    @Override
    protected BasePresenter getPresenter() {
        return new ServerPresenterImpl(this);
    }

    @Override
    protected Fragment getViewFragment() {
        return new ServerFragment();
    }

    @Override
    protected String getDisconnectTitle() {
        return getString(R.string.server_disconnect_title);
    }

    @Override
    protected String getDisconnectMessage() {
        return getString(R.string.server_disconnect_message);
    }


}

