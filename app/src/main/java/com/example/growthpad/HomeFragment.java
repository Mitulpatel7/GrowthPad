package com.example.growthpad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    Button task_btn, habit_btn, goal_btn;
    Animation fabopen1, fabopen2, fabopen3, fabclose1, fabclose2, fabclose3, rotate_forward, rotate_backward;
    boolean isOpen = false;
    TabLayout tabLayout;
    ViewPager viewPager;
    RelativeLayout relativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewpager);
        floatingActionButton = view.findViewById(R.id.floating_btn);
        task_btn = view.findViewById(R.id.add_task);
        habit_btn = view.findViewById(R.id.add_habit);
        goal_btn = view.findViewById(R.id.add_goal);

        relativeLayout = view.findViewById(R.id.home_tab_layout);

        fabopen1 = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open1);
        fabopen2 = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open2);
        fabopen3 = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open3);
        fabclose1 = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close1);
        fabclose2 = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close2);
        fabclose3 = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close3);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

        // floating button click events
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABanimation();
            }
        });

        task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABanimation();

                Intent intent = new Intent(getActivity().getApplication(), Add_task_activity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        habit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FABanimation();
                Intent intent = new Intent(getActivity().getApplication(), Add_habit_activity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        goal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FABanimation();
                Intent intent = new Intent(getActivity().getApplication(), Add_goal_activity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


        // tab layout code
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        tabadapter adapter = new tabadapter(fragmentManager);
        viewPager.setAdapter(adapter);


        // Inflate the layout for this fragment
        return view;
    }

    private void FABanimation() {

        if (isOpen) {
            floatingActionButton.startAnimation(rotate_backward);
            task_btn.startAnimation(fabclose1);
            habit_btn.startAnimation(fabclose2);
            goal_btn.startAnimation(fabclose3);
            task_btn.setClickable(false);
            habit_btn.setClickable(false);
            goal_btn.setClickable(false);
            isOpen = false;
        } else {
            floatingActionButton.startAnimation(rotate_forward);
            task_btn.startAnimation(fabopen1);
            habit_btn.startAnimation(fabopen2);
            goal_btn.startAnimation(fabopen3);
            task_btn.setClickable(true);
            habit_btn.setClickable(true);
            goal_btn.setClickable(true);
            isOpen = true;
        }
    }


    private class tabadapter extends FragmentStatePagerAdapter {
        public tabadapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Task";

                case 1:
                    return "Habits";

                case 2:
                    return "Goals";
            }
            return null;
        }

        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    return new TaskFragment();

                case 1:
                    return new HabitFragment();

                case 2:
                    return new GoalFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
