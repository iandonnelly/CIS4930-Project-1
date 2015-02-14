package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.views.BaseView;

public abstract class BasePresenterImpl implements BasePresenter {
    protected BaseView mView;

    public BasePresenterImpl(BaseView view) {
        mView = view;
    }

    @Override
    public boolean onOptionsItemSelected(int id) {
        if (id == R.id.action_disconnect) {
            mView.showDisconnectDialog();
        } else if (id == R.id.action_settings) {
            mView.navigateToSettings();
            return true;
        } else if (id == R.id.action_about) {
            mView.navigateToAbout();
            return true;
        } else if (id == R.id.action_exit) {
            mView.showExitDialog();
            return true;
        }

        return false;
    }

    @Override
    public void onServerErrorDialogPositiveClicked() {
        mView.navigateToHome();
    }

    @Override
    public void onBackPressed() {
        mView.showDisconnectDialog();
    }

}
