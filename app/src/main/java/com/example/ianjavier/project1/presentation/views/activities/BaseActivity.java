package com.example.ianjavier.project1.presentation.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.presenters.BasePresenter;
import com.example.ianjavier.project1.presentation.views.BaseView;
import com.example.ianjavier.project1.presentation.views.OnTabChangedListener;
import com.example.ianjavier.project1.presentation.views.TabListener;
import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;
import com.example.ianjavier.project1.presentation.views.fragments.BaseTabFragment;

public abstract class BaseActivity extends ActionBarActivity implements BaseView,
        OnTabChangedListener, TabListener {
    protected BasePresenter mPresenter;
    protected BaseTabFragment mTabFragment;
    protected Menu mMenu;
    protected Handler mHandler;

    protected abstract int getLayoutResource();
    protected abstract BasePresenter getPresenter();
    protected abstract BaseTabFragment getViewFragment();
    protected abstract String getDisconnectTitle();
    protected abstract String getDisconnectMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        // Hide the keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Setup the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the tab fragment
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mTabFragment = getViewFragment();
            transaction.replace(R.id.fragment, mTabFragment);
            transaction.commit();
        }

        // Initialize presenter
        mPresenter = getPresenter();

        // Get handler
        mHandler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        mPresenter.onCreateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (mPresenter.onOptionsItemSelected(item.getItemId()) || super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void navigateToSettings() {
        //startActivity(new Intent(ClientActivity.this, SettingsActivity.this));
    }

    @Override
    public void navigateToAbout() {
        //startActivity(new Intent(ClientActivity.this, AboutActivity.this));
    }

    @Override
    public void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showDisconnectDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getDisconnectTitle())
                .setMessage(getDisconnectMessage())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.onDisconnectDialogPositiveClicked();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void showServerErrorDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(BaseActivity.this)
                        .setTitle(getString(R.string.server_error_title))
                        .setMessage(getString(R.string.server_error_message))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.onServerErrorDialogPositiveClicked();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_app_title))
                .setMessage(getString(R.string.exit_app_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.onExitDialogPositiveClicked();
                    }
                })
                .show();
    }

    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setActionBarSubtitle(String subtitle) {
        getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void addTab(final String channel) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTabFragment.addTab(channel);
            }
        });
    }

    @Override
    public void removeTab() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTabFragment.removeTab();
            }
        });
    }

    @Override
    public void onTabChanged(int position) {
        mPresenter.onTabChanged(position, mTabFragment.getTab(position));
    }

    @Override
    public void onCreateTabView() {
        mPresenter.onCreateTabView();
    }

    @Override
    public BaseFragment getCurrentTab() {
        return mTabFragment.getCurrentTab();
    }

    @Override
    public int getCurrentTabPosition() {
        return mTabFragment.getCurrentTabPosition();
    }
}
