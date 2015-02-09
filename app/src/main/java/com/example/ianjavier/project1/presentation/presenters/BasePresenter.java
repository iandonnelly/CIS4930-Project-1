package com.example.ianjavier.project1.presentation.presenters;

public interface BasePresenter {
    public boolean onOptionsItemSelected(int id);
    public void onBackPressed();
    public void onDisconnectDialogPositiveClicked();
    public void onServerErrorDialogPositiveClicked();
    public void onExitDialogPositiveClicked();
}
