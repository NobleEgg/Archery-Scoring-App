package com.f126219.recordarcheryscoring;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recordsRecyclerViewAdapter extends RecyclerView.Adapter<recordsRecyclerViewAdapter.MyViewHolder>{

    Context context;
    ArrayList<roundSummaryModel> roundSummaryModels;

    //OVERVIEW: Creates recyclerView containing a card for each record store in the local database
    public recordsRecyclerViewAdapter(Context context, ArrayList<roundSummaryModel> roundSummaryModels){
        this.context = context;
        this.roundSummaryModels = roundSummaryModels;
    }

    @NonNull
    @Override
    public recordsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflates and creates each row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.round_summary_card, parent, false);
        return new recordsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recordsRecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigns values to each row

        holder.round.setText(roundSummaryModels.get(position).getRound());
        holder.bowStyle.setText(roundSummaryModels.get(position).getBowStyle());
        holder.additionalInfo.setText(" " + roundSummaryModels.get(position).getLocation() + " | " + roundSummaryModels.get(position).getDate());
        holder.score.setText(roundSummaryModels.get(position).getScore());

        if (roundSummaryModels.get(position).getRecordStatus().equals("1")){
            holder.trophy.setVisibility(View.VISIBLE);
        }else{
            holder.trophy.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        //returns the number of items to be displayed
        for (int i=0; i < roundSummaryModels.size(); i++) {
        }
        return roundSummaryModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //takes variables for each row to be created

        TextView round, bowStyle, additionalInfo, score;
        ImageView trophy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            round = itemView.findViewById(R.id.round);
            bowStyle = itemView.findViewById(R.id.bowStyle);
            additionalInfo = itemView.findViewById(R.id.additionalRoundInfo);
            score = itemView.findViewById(R.id.score);
            trophy = itemView.findViewById(R.id.trophy);
        }
    }

}
