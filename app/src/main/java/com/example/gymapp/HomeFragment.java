package com.example.gymapp;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.gymapp.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// This fragment displays a list of workouts on home screen
public class HomeFragment extends Fragment {

    private WorkoutDatabaseHelper databaseHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and init. RecycleView
        View view = inflater.inflate(R.layout.homefragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize database helper
        databaseHelper = new WorkoutDatabaseHelper(requireContext());

        // Retrieve all workouts
        List<Workout> workouts = databaseHelper.getAllWorkouts();

        // Set adapter for RecyclerView
        WorkoutAdapter adapter = new WorkoutAdapter(workouts, getParentFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close database helper
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}

