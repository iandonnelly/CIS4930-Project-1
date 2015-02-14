package com.example.ianjavier.project1.presentation.views.activities;

import android.os.Bundle;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.presenters.BasePresenter;
import com.example.ianjavier.project1.presentation.presenters.ServerPresenter;
import com.example.ianjavier.project1.presentation.presenters.ServerPresenterImpl;
import com.example.ianjavier.project1.presentation.views.ServerView;
import com.example.ianjavier.project1.presentation.views.fragments.BaseTabFragment;
import com.example.ianjavier.project1.presentation.views.fragments.ServerTabsFragment;

public class ServerActivity extends BaseActivity implements ServerView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String name = getIntent().getStringExtra(getString(R.string.server_name));
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
    protected BaseTabFragment getViewFragment() {
        return ServerTabsFragment.newInstance(getIntent().getStringExtra(getString(R.string.server_name)));
    }

    @Override
    protected String getDisconnectTitle() {
        return getString(R.string.server_disconnect_title);
    }

    @Override
    protected String getDisconnectMessage() {
        return getString(R.string.server_disconnect_message);
    }

    @Override
    public void hideJoinChannelAction() {
        mMenu.setGroupVisible(R.id.leave_channel_group, false);
    }

    @Override
    public void hideLeaveChannelAction() {
        mMenu.setGroupVisible(R.id.join_channel_group, false);
    }
}

