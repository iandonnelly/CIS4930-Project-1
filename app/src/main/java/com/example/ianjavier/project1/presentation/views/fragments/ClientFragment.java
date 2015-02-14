package com.example.ianjavier.project1.presentation.views.fragments;

import android.os.Bundle;

import com.example.ianjavier.project1.R;


public class ClientFragment extends BaseFragment  {
    public final static BaseTabFragment.TabFactory TAB_FACTORY =
            new BaseTabFragment.TabFactory<ClientFragment>() {
                @Override
                public ClientFragment newInstance(Bundle args) {
                    ClientFragment fragment = new ClientFragment();
                    fragment.setArguments(args);
                    return fragment;
                }
            };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_view;
    }
}
