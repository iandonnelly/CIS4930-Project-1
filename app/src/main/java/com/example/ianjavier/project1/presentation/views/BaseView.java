package com.example.ianjavier.project1.presentation.views;

import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public interface BaseView {
    public BaseFragment getFragment();
    public void setActionBarTitle(String title);
    public void showDisconnectDialog();
    public void showServerErrorDialog();
    public void showExitDialog();
    public void navigateToHome();
    public void navigateToSettings();
    public void navigateToAbout();
    public void exitApp();
}
