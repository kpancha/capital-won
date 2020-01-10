package com.example.goaldigger.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.goaldigger.Goal;
import com.example.goaldigger.MainActivity;
import com.example.goaldigger.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private Goal goal;

    public static PlaceholderFragment newInstance() {
       return new PlaceholderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        MainActivity a = (MainActivity) getActivity();
        goal = a.goal;
        Log.d("Fragment screen", Double.toString(goal.getPRICE()));
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //return inflater.inflate(R.layout.fragment_main, container, false);
        //return inflater.inflate(R.layout.set_goal, container, false);

          View v =  inflater.inflate(R.layout.fragment_main, container, false);
        ProgressBar circle = v.findViewById(R.id.progress);
        circle.setProgress(50);

        ProgressBar bar = v.findViewById(R.id.determinateBar);
        bar.setProgress(20);
          return v;
    }
}