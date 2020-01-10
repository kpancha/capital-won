package com.example.goaldigger.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goaldigger.Goal;
import com.example.goaldigger.MainActivity;
import com.example.goaldigger.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SpendTrendsFragment extends Fragment {

    Goal goal;

    public static SpendTrendsFragment newInstance() {
        return new SpendTrendsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity a = (MainActivity) getActivity();
        goal = a.goal;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spendtrends, container, false);

        String mostSpent = String.format("1. %s", goal.getFreqList().get(0));
        ((TextView) v.findViewById(R.id.textView3)).setText(mostSpent);

        String spent2 = String.format("2. %s", goal.getFreqList().get(1));
        ((TextView) v.findViewById(R.id.textView4)).setText(spent2);

        String spent3 = String.format("3. %s", goal.getFreqList().get(2));
        ((TextView) v.findViewById(R.id.textView5)).setText(spent3);
        return v;
    }
}
