package com.example.ianjavier.project1.presentation.views;

import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public interface BaseView {
    public void setActionBarTitle(String title);
    public void setActionBarSubtitle(String subtitle);
    public BaseFragment getTab(int position);
    public void addTab(String channel);
    public void removeTab(int position);
    public int getCurrentTabPosition();
    public void showUserListDialog(String[] userList);
    public void showDisconnectDialog();
    public void showServerErrorDialog();
    public void showExitDialog();
    public void navigateToHome();
    public void navigateToSettings();
    public void navigateToAbout();
    public void exitApp();
}
