package com.example.ianjavier.project1.presentation.views.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.ianjavier.project1.presentation.views.ViewTabs;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTabFragment extends Fragment {
    public interface OnTabChangedListener {
        public void onTabChanged(int position);
    }

    public  interface TabFactory<T extends BaseFragment> {
        public T newInstance(Bundle args);
    }

    public static class Tab {
        private final TabFactory mTabFactory;
        private final Bundle mArgs;
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;
        private BaseFragment mFragment;

        Tab(TabFactory tabFactory, Bundle args, CharSequence title,
            int indicatorColor, int dividerColor) {
            mTabFactory = tabFactory;
            mArgs = args;
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

        BaseFragment createFragment() {
            mFragment = mTabFactory.newInstance(mArgs);
            return mFragment;
        }

        public BaseFragment getFragment() { return mFragment; }

        CharSequence getTitle() {
            return mTitle;
        }

        int getIndicatorColor() {
            return mIndicatorColor;
        }

        int getDividerColor() {
            return mDividerColor;
        }
    }

    protected ViewPager mViewPager;
    protected ViewTabs mViewTabs;
    protected List<Tab> mTabs = new ArrayList<>();
    protected OnTabChangedListener mListener;

    protected class ViewPagerAdapter extends FragmentStatePagerAdapter implements
            ViewPager.OnPageChangeListener {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabs.get(position).createFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public void onPageSelected(int position) {
            mListener.onTabChanged(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
    }

    public abstract void addTab(String channel);

    public void removeTab(int position) {
        mTabs.remove(position);
        mViewPager.getAdapter().notifyDataSetChanged();
        mViewTabs.setViewPager(mViewPager);
        mViewTabs.setOnPageChangeListener((ViewPager.OnPageChangeListener) mViewPager.getAdapter());
    }

    public void setCurrentItem(int item) {
        mViewPager.setCurrentItem(item);
    }

    public BaseFragment getTab(int position) {
        return mTabs.get(position).getFragment();
    }

    public int getCurrentTabPosition() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTabChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTabChangedListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
