package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Exercise adapter handles the display of each exercise dynamically.
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises;
    private FragmentManager fragmentManager;

    //Constructor for exercise adapter
    public ExerciseAdapter(List<Exercise> exercises, FragmentManager fragmentManager) {
        this.exercises = exercises;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //On create view holder, display the item_exercise
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.bind(exercise);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle exercise item click
                openExerciseDetailFragment(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView targetTextView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialize views
            nameTextView = itemView.findViewById(R.id.textExerciseName);
            targetTextView = itemView.findViewById(R.id.textExerciseTarget);
        }

        public void bind(Exercise exercise) {
            //Initialize views
            nameTextView.setText(exercise.getName());
            targetTextView.setText("Targets: " + exercise.getTarget());
        }
    }

    private void openExerciseDetailFragment(Exercise exercise) {
        //Set the fragment to show exercise detail on click
        Fragment fragment = ExerciseDetailFragment.newInstance(exercise);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
