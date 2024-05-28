package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

// Adapter for displaying workouts in a RecyclerView
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    // List of workouts to display
    private List<Workout> workouts;
    private FragmentManager fragmentManager;

    // Constructor to initialize the adapter with workout data and FragmentManager
    public WorkoutAdapter(List<Workout> workouts, FragmentManager fragmentManager) {
        this.workouts = workouts;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a new ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        // Binds workout data to the ViewHolder
        Workout workout = workouts.get(position);
        holder.bind(workout);
        // Set a listener to open the detail fragment when a workout is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkoutDetailFragment(workout);
            }
        });
    }

    // Returns the total number of workouts. Unused.
    @Override
    public int getItemCount() {
        return workouts.size();
    }

    // ViewHolder class for displaying workout items
    static class WorkoutViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView targetTextView;
        private ImageView imageView;

        // Constructor to initialize views
        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textWorkoutName);
            targetTextView = itemView.findViewById(R.id.textWorkoutTarget);
            imageView = itemView.findViewById(R.id.imageWorkout);
        }

        // Binds workout data to the views
        public void bind(Workout workout) {
            nameTextView.setText(workout.getName());
            targetTextView.setText("Targets: " + workout.getTarget());

            // Load image using Picasso
            Picasso.get()
                    .load(workout.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);
        }
    }

    // Handles opening of the WorkoutDetailFragment
    private void openWorkoutDetailFragment(Workout workout) {
        Fragment fragment = WorkoutDetailFragment.newInstance(workout);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
