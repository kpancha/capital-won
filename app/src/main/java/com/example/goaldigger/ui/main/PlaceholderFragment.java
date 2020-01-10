package com.example.goaldigger.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //return inflater.inflate(R.layout.fragment_main, container, false);
        //return inflater.inflate(R.layout.set_goal, container, false);

          View v =  inflater.inflate(R.layout.fragment_main, container, false);
        ProgressBar circle = v.findViewById(R.id.progress);
        Double num = goal.getPercentSaved() * 100;
        circle.setProgress(Math.toIntExact(Math.round(num)));


        ProgressBar bar = v.findViewById(R.id.determinateBar);
        Double percent = goal.getThisWeekSpending() / goal.calcWeeklyGoal() * 100;
////        if (percent <= 50) {
////            ((ProgressBar) v.findViewById(R.id.determinateBar)).setProgressTintList();
////        }
        Log.d("percent", Double.toString(percent));
        bar.setProgress(Math.toIntExact(Math.round(percent)));
        Log.d("thisWeekSpending", Double.toString(goal.getThisWeekSpending()));
        Log.d("weekly goal", Double.toString(goal.calcWeeklyGoal()));


        /*
        String message = String.format("To afford a %s in %s weeks, you should spend %s a week." +
                "Based on your average spending habits, you will be saving %s ", goal.getItemName(), Integer.toString(goal.getNumWeeksRem()),
                Double.toString(goal.calcWeeklyGoal()), Double.toString(goal.getWeeklySavings()));

         */
        String message = String.format("To afford a %s in %s weeks, you should save this much money weekly. " +
                        "Based on your average spending habits, you should cut down your weekly spending by $amount. ", goal.getItemName(), Integer.toString(goal.getNumWeeksRem()));
        ((TextView) v.findViewById(R.id.heading)).setText(message);


        String amtSpent = String.format("You have spent $%s of your $%s budget this week.", 10, 20 /*SPENTTHISWEEK, WEEKLYBUDGET*/);
        ((TextView) v.findViewById(R.id.spent)).setText(amtSpent);

        String savings = String.format("$%s saved", 5 /*AMOUNTSAVED*/);
        ((TextView) v.findViewById(R.id.saved)).setText(savings);

        String savedp = String.format("%s%% of goal", 8/*PERCENTOFTOTAL*/);
        ((TextView) v.findViewById(R.id.savedpercent)).setText(savedp);

        String remaining = String.format("$%s remaining", 10 /*REMAININGMONEY */);
        ((TextView) v.findViewById(R.id.left)).setText(remaining);

        String end = String.format("to reach $%s", 20/*GOALCOST*/);
        ((TextView) v.findViewById(R.id.ending)).setText(end);

        return v;
        }

    }