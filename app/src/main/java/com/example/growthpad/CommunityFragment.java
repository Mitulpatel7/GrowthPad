package com.example.growthpad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


public class CommunityFragment extends Fragment {

    TabLayout tab_layout_community;

    ViewPager view_pager_community;
    SharedPreferences sp;



     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_community, container, false);
        sp = getActivity().getSharedPreferences(ConstantURL.PREFERENCE, Context.MODE_PRIVATE);
        tab_layout_community = view.findViewById(R.id.tab_layout_community);
        view_pager_community = view.findViewById(R.id.view_pager_community);

        // tab layout code
         tab_layout_community.post(new Runnable() {
             @Override
             public void run() {
                 tab_layout_community.setupWithViewPager(view_pager_community);
             }
         });

        /*tab_layout_community.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

         FragmentManager fragmentManager = getFragmentManager();
         tabAdapter adapter = new tabAdapter(fragmentManager);
         view_pager_community.setAdapter(adapter);

         view_pager_community.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                 if(position==0){
                     sp.edit().putString(ConstantURL.COMMUNITY_SELECT_ID,"All").commit();
                 }
                 if(position==1){
                     sp.edit().putString(ConstantURL.COMMUNITY_SELECT_ID,"Personal").commit();
                 }
                 if(position==2){
                     sp.edit().putString(ConstantURL.COMMUNITY_SELECT_ID,"Health").commit();
                 }
                 if(position==3){
                     sp.edit().putString(ConstantURL.COMMUNITY_SELECT_ID,"Work").commit();
                 }
                 if(position==4){
                     sp.edit().putString(ConstantURL.COMMUNITY_SELECT_ID,"Study").commit();
                 }
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });

         return view;


    }

    private class tabAdapter extends FragmentStatePagerAdapter {
        public tabAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return "All";
                case 1:
                    return "Personal";
                case 2:
                    return "Health";
                case 3 :
                    return "Work";
                case 4:
                    return "Study";
            }
            return null;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {
                case 0:
                    return new All_Community_Fragment();

                case 1:
                    return new Personal_Community_Fragment();

                case 2:
                    return new Health_Community_Fragment();

                case 3:
                    return new Work_Community_Fragment();

                case 4:
                    return new Study_Community_Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
