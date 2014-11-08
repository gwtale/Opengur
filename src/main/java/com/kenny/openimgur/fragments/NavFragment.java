package com.kenny.openimgur.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kenny.openimgur.R;
import com.kenny.openimgur.adapters.NavAdapter;
import com.kenny.openimgur.classes.ImgurUser;
import com.kenny.openimgur.util.ViewUtils;

/**
 * Created by kcampagna on 10/19/14.
 */
public class NavFragment extends BaseFragment implements ListView.OnItemClickListener {
    private ListView mListView;

    private ActionBarDrawerToggle mToggle;

    private NavigationListener mListener;

    private NavAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NavigationListener) mListener = (NavigationListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view;
        String[] navItems = getResources().getStringArray(R.array.nav_items);
        mListView.setAdapter(mAdapter = new NavAdapter(getActivity(), navItems, app.getUser()));
        mListView.setOnItemClickListener(this);
        mListView.setPadding(0, ViewUtils.getStatusBarHeight(getActivity()), 0, 0);
    }

    @Override
    public void onDestroyView() {
        mListView = null;
        mToggle = null;
        super.onDestroyView();
    }

    /**
     * Updates the Logged in user
     *
     * @param user The newly logged in user
     */
    public void onUsernameChange(ImgurUser user) {
        if (mAdapter != null) mAdapter.onUsernameChange(user);
    }

    /**
     * Sets up the DrawerLayout
     *
     * @param drawerLayout
     */
    public void configDrawerLayout(DrawerLayout drawerLayout) {
        mToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.nav_open, R.string.nav_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (mListener != null) mListener.onDrawerToggle(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                if (mListener != null) mListener.onDrawerToggle(false);
            }
        };

        mToggle.syncState();
        drawerLayout.setDrawerListener(mToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mListener != null) mListener.onNavigationItemSelected(i);
        setSelectedPage(i);
    }

    /**
     * Sets the currently selected position in the navigation list
     *
     * @param position
     */
    public void setSelectedPage(int position) {
        mAdapter.setSelectedPosition(position);
    }

    public static interface NavigationListener {
        void onNavigationItemSelected(int position);

        void onDrawerToggle(boolean isOpen);
    }
}
