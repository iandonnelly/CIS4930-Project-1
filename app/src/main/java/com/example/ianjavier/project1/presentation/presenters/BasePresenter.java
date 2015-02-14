package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public interface BasePresenter {
    public boolean onOptionsItemSelected(int id);
    public boolean onCreateOptionsMenu();
    public void onPauseFragment(int position);
    public void onResumeFragment(int position);
    public void onBackPressed();
    public void onServerErrorDialogPositiveClicked();
    public void onDisconnectDialogPositiveClicked();
    public void onExitDialogPositiveClicked();
    public void onTabChanged(int position, BaseFragment tab);
}
