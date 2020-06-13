package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.LBViewHolder> {

    private ArrayList<UserData> mUserList;

    public recyclerViewAdapter(ArrayList<UserData> userList) {
        mUserList = userList;
    }

    public static class LBViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView displayName;
        public TextView totalScore;
        public TextView accuracy;
        public TextView strikeRate;
        public LBViewHolder(View v) {
            super(v);
            displayName = (TextView) v.findViewById(R.id.displayName);
            totalScore = (TextView) v.findViewById(R.id.totalscore);
            accuracy = (TextView) v.findViewById(R.id.accuracy);
            strikeRate = (TextView) v.findViewById(R.id.strikerate);
        }
    }

    @Override
    public recyclerViewAdapter.LBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leaderboad_layout,parent,false);
        LBViewHolder vh = new LBViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.LBViewHolder holder, int position) {
        UserData user = mUserList.get(position);
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        holder.displayName.setText(String.valueOf(position+1)+" : "+ user.getDisplayName());
        holder.totalScore.setText("Total Score : " + String.valueOf(user.getTotalScore()));
        holder.accuracy.setText("Accuracy : " + String.valueOf(df.format(user.getAccuracy())) + "%");
        holder.strikeRate.setText("Strike Rate : "+String.valueOf(df.format(user.getStrikeRate()))+ " sec/answer");
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
