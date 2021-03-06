package com.example.ianjavier.project1.presentation.views;

public interface ClientView extends BaseView {
    public void showJoinChannelDialog();
    public void showServerClosedDialog();
    public void hideLeaveChannelAction();
    public void showLeaveChannelAction();
    public void setCurrentTab(int position);
}
