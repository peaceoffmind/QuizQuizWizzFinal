package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.LBViewHolder> {

    private String[] mDataset;

    public recyclerViewAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    public static class LBViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public LBViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.displayName);
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
        String data = mDataset[position];
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
