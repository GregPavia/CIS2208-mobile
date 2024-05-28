package com.example.gymapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// This fragment displays details of a specific exercise, including a countdown timer.
public class ExerciseDetailFragment extends Fragment {

    private static final String ARG_EXERCISE = "exercise";
    private Exercise exercise;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 60000;

    public ExerciseDetailFragment() {
    }

    // method to create a new instance of this fragment
    public static ExerciseDetailFragment newInstance(Exercise exercise) {
        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    // Method called when the fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercise = getArguments().getParcelable(ARG_EXERCISE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // create the view for the fragment
        View view = inflater.inflate(R.layout.fragment_exercise_detail, container, false);

        // Initialize views from xml
        TextView titleTextView = view.findViewById(R.id.textExerciseTitle);
        TextView targetTextView = view.findViewById(R.id.textExerciseTarget);
        TextView descriptionTextView = view.findViewById(R.id.textExerciseDescription);
        TextView timerTextView = view.findViewById(R.id.timerTextView);
        Button timerButton = view.findViewById(R.id.timerButton);
        Button addTimeButton = view.findViewById(R.id.addTimeButton);
        Button subtractTimeButton = view.findViewById(R.id.subtractTimeButton);

        // Populate elements with exercise details
        if (exercise != null) {
            titleTextView.setText(exercise.getName());
            targetTextView.setText("Target: " + exercise.getTarget());
            descriptionTextView.setText(exercise.getDescription());
        }

        // Set listeners for buttons
        view.findViewById(R.id.backButton).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        timerButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
                timerButton.setText("Start");
            } else {
                startTimer(timerTextView, timerButton);
                timerButton.setText("Pause");
            }
        });

        addTimeButton.setOnClickListener(v -> {
            if (!isTimerRunning) {
                timeLeftInMillis += 15000;
                updateTimerText(timerTextView);
            }
        });

        subtractTimeButton.setOnClickListener(v -> {
            if (!isTimerRunning) {
                timeLeftInMillis = Math.max(0, timeLeftInMillis - 15000);
                updateTimerText(timerTextView);
            }
        });

        return view;
    }

    // Method to start the countdown timer
    private void startTimer(TextView timerTextView, Button timerButton) {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText(timerTextView);
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                timerButton.setText("Start");
            }
        }.start();
        isTimerRunning = true;
    }

    // Method to pause the countdown timer
    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    // Method to update the timer text with remaining time
    private void updateTimerText(TextView timerTextView) {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }
}
