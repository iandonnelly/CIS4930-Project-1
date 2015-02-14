package com.example.ianjavier.project1.presentation.views.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.presenters.BasePresenter;
import com.example.ianjavier.project1.presentation.presenters.ClientPresenter;
import com.example.ianjavier.project1.presentation.presenters.ClientPresenterImpl;
import com.example.ianjavier.project1.presentation.views.ClientView;
import com.example.ianjavier.project1.presentation.views.dialogs.JoinChannelDialogFragment;
import com.example.ianjavier.project1.presentation.views.fragments.BaseTabFragment;
import com.example.ianjavier.project1.presentation.views.fragments.ClientChannelFragment;
import com.example.ianjavier.project1.presentation.views.fragments.ClientTabsFragment;

public class ClientActivity extends BaseActivity implements ClientView,
        JoinChannelDialogFragment.JoinChannelDialogListener, ClientChannelFragment.ClientChannelListener {

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

        ((ClientPresenter) mPresenter).onStart(address, port, nickname);
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
    protected BaseTabFragment getViewFragment() {
        return ClientTabsFragment.newInstance(getIntent().getStringExtra(getString(R.string.address)));
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
    public void showJoinChannelDialog() {
        DialogFragment dialog = new JoinChannelDialogFragment();
        dialog.show(getFragmentManager(), "JoinChannelDialogFragment");
    }

    @Override
    public void showServerClosedDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(ClientActivity.this)
                        .setTitle(getString(R.string.server_closed_title))
                        .setMessage(getString(R.string.server_closed_message))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ((ClientPresenter) mPresenter).onServerClosedDialogPositiveClicked();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false)
                        .show();
            }
        });
    }

    @Override
    public void hideLeaveChannelAction() {;
        mMenu.setGroupVisible(R.id.leave_channel_group, false);
    }

    @Override
    public void showLeaveChannelAction() {
        mMenu.setGroupVisible(R.id.leave_channel_group, true);
    }

    @Override
    public void setCurrentTab(int position) {
        mTabFragment.setCurrentItem(position);
    }

    @Override
    public void onJoinChannelDialogPositiveClicked(String channel) {
        ((ClientPresenter) mPresenter).onJoinChannelDialogPositiveClicked(channel);
    }

    @Override
    public void onSendMessageClicked(String message) {
        ((ClientPresenter) mPresenter).onSendMessageClicked(message);
    }
}
