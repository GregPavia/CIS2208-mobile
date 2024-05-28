package com.example.gymapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.gymapp.R;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailFragment extends Fragment {

    private static final String ARG_WORKOUT = "workout";

    private Workout workout;

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }

    // Create a new instance of the fragment with a workout object passed as an argument
    public static WorkoutDetailFragment newInstance(Workout workout) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_WORKOUT, workout);
        fragment.setArguments(args);
        return fragment;
    }

    // Retrieve the workout object from the arguments when the fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workout = getArguments().getParcelable(ARG_WORKOUT);
        }
    }

    // Inflate the layout for this fragment and display workout details
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);

        TextView titleTextView = view.findViewById(R.id.textWorkoutTitle);
        TextView targetTextView = view.findViewById(R.id.textWorkoutTarget);
        TextView descriptionTextView = view.findViewById(R.id.textWorkoutDescription);
        ImageView imageView = view.findViewById(R.id.imageWorkout);

        // Display workout details if the workout object is not null
        if (workout != null) {
            titleTextView.setText(workout.getName());
            targetTextView.setText("Target: " + workout.getTarget());
            descriptionTextView.setText(workout.getDescription());
            // Load image using Picasso library
            Picasso.get()
                    .load(workout.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);

            // Display associated exercises
            RecyclerView recyclerViewExercises = view.findViewById(R.id.recyclerViewExercises);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewExercises.setLayoutManager(layoutManager);

            // Set up the adapter to display exercises in the RecyclerView
            ExerciseAdapter exerciseAdapter = new ExerciseAdapter(workout.getExercises(), getParentFragmentManager());
            recyclerViewExercises.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerViewExercises.setAdapter(exerciseAdapter);

        }

        return view;
    }

}

