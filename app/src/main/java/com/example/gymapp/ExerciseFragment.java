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

// Displays a list of exercises using a RecyclerView.
public class ExerciseFragment extends Fragment {

    private ExerciseDatabaseHelper databaseHelper;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    // Method called when the fragment's view is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment + initialize RecycleView
        View view = inflater.inflate(R.layout.exercisefragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Initialize database helper
        databaseHelper = new ExerciseDatabaseHelper(requireContext());

        // Retrieve all exercises from the database
        List<Exercise> exercises = databaseHelper.getAllExercises();

        // Initialize and set adapter for RecyclerView
        ExerciseAdapter adapter = new ExerciseAdapter(exercises, getParentFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Method called when the fragment's view is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close database helper to release resources
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}


