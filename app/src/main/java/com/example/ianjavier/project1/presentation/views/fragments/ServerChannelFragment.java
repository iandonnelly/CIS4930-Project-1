package com.example.ianjavier.project1.presentation.views.fragments;

import android.os.Bundle;

import com.example.ianjavier.project1.R;

public class ServerChannelFragment extends BaseFragment {
    public final static BaseTabFragment.TabFactory TAB_FACTORY =
            new BaseTabFragment.TabFactory<ServerChannelFragment>() {
                @Override
                public ServerChannelFragment newInstance(Bundle args) {
                    ServerChannelFragment fragment = new ServerChannelFragment();
                    fragment.setArguments(args);
                    return fragment;
                }
            };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_view;
    }
}
