package com.example.ianjavier.project1.presentation.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.presenters.BasePresenter;
import com.example.ianjavier.project1.presentation.presenters.ClientPresenter;
import com.example.ianjavier.project1.presentation.presenters.ClientPresenterImpl;
import com.example.ianjavier.project1.presentation.views.ClientFragmentListener;
import com.example.ianjavier.project1.presentation.views.fragments.ClientFragment;

public class ClientActivity extends BaseActivity implements
        ClientFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String address = getIntent().getStringExtra(getString(R.string.address));
        int port = getIntent().getIntExtra(getString(R.string.port), 0);
        String nickname = getIntent().getStringExtra(getString(R.string.nickname));

        ((ClientPresenter) super.mPresenter).onStart(address, port, nickname);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_client;
    }

    @Override
    protected BasePresenter getPresenter() {
        return new ClientPresenterImpl(this);
    }

    @Override
    protected Fragment getViewFragment() {
        return new ClientFragment();
    }

    @Override
    protected String getDisconnectTitle() {
        return getString(R.string.client_disconnect_title);
    }

    @Override
    protected String getDisconnectMessage() {
        return getString(R.string.client_disconnect_message);
    }

    @Override
    public void onSendMessageClicked(String message) {
        ((ClientPresenter) super.mPresenter).onSendMessageClicked(message);
    }

}
