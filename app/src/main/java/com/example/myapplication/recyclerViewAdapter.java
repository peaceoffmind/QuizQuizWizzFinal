package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.LBViewHolder> {

    private List<UserData> mUserList;

    public recyclerViewAdapter(List<UserData> userList) {
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
            displayName = v.findViewById(R.id.displayName);
            totalScore = v.findViewById(R.id.totalscore);
            accuracy = v.findViewById(R.id.accuracy);
            strikeRate = v.findViewById(R.id.strikerate);
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
        holder.displayName.setText("DISPLAY NAME");
        holder.totalScore.setText(user.getTotalScore());
        holder.accuracy.setText(String.valueOf(user.getAccuracy()));
        holder.strikeRate.setText(String.valueOf(user.getStrikeRate()));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
