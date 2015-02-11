package com.example.ianjavier.project1.presentation.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.presenters.BasePresenter;
import com.example.ianjavier.project1.presentation.views.BaseView;
import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public abstract class BaseActivity extends ActionBarActivity implements BaseView {
    protected BasePresenter mPresenter;
    protected Fragment mFragment;

    protected abstract int getLayoutResource();
    protected abstract BasePresenter getPresenter();
    protected abstract Fragment getViewFragment();
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

        // Add the client fragment
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mFragment = getViewFragment();
            transaction.replace(R.id.fragment, mFragment);
            transaction.commit();
        }

        // Initialize presenter
        mPresenter = getPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mPresenter.onOptionsItemSelected(item.getItemId())) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
        new AlertDialog.Builder(this)
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
    public BaseFragment getFragment() {
        return (BaseFragment) mFragment;
    }

    @Override
    public void setActionBarTitle(String title) {
        setTitle(title);
    }
}
