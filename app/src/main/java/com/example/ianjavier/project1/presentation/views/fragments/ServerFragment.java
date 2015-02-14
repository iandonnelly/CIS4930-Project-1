package com.example.ianjavier.project1.presentation.views.fragments;

import android.os.Bundle;

import com.example.ianjavier.project1.R;


public class ServerFragment extends BaseFragment {
    public final static BaseTabFragment.TabFactory TAB_FACTORY =
            new BaseTabFragment.TabFactory<ServerFragment>() {
                @Override
                public ServerFragment newInstance(Bundle args) {
                    ServerFragment fragment = new ServerFragment();
                    fragment.setArguments(args);
                    return fragment;
                }
            };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_view;
    }
}
