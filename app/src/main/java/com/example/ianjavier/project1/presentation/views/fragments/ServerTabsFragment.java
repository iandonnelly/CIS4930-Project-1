package com.example.ianjavier.project1.presentation.views.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ianjavier.project1.App;
import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.views.ViewTabs;

public class ServerTabsFragment extends BaseTabFragment {
    public static ServerTabsFragment newInstance(String serverName) {
        ServerTabsFragment fragment = new ServerTabsFragment();

        Bundle args = new Bundle();
        args.putString(App.getContext().getResources().getString(R.string.server_name), serverName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add the primary server tab
        mTabs.add(new Tab(
                ServerFragment.TAB_FACTORY,
                null,
                getArguments().getString(getString(R.string.server_name)),
                getResources().getColor(R.color.primary),
                Color.GRAY
        ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set up the view pager
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        // Set up the tabs
        mViewTabs = (ViewTabs) view.findViewById(R.id.view_tabs);
        mViewTabs.setTitleColor(Color.BLACK);
        mViewTabs.setFittingChildren(true);
        mViewTabs.setTabType(ViewTabs.TabType.TEXT);
        mViewTabs.setViewPager(mViewPager);
        mViewTabs.setOnPageChangeListener((ViewPager.OnPageChangeListener) mViewPager.getAdapter());
        mViewTabs.setCustomTabColorizer(new ViewTabs.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }
        });
    }

    @Override
    public void addTab(String channel) {
        mTabs.add(new Tab(
                ServerChannelFragment.TAB_FACTORY,
                null,
                channel,
                getResources().getColor(R.color.primary),
                Color.GRAY
        ));
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewTabs.setViewPager(mViewPager);
        mViewTabs.setOnPageChangeListener((ViewPager.OnPageChangeListener) mViewPager.getAdapter());
        mViewPager.setCurrentItem(mTabs.size() - 1);
    }
}
