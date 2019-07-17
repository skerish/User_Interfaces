package com.example.ui_material_me;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder> {

    private ArrayList<Sport> mSportsData;
    private Context context;

    MainActivity mainActivity;

    private static final String TRANSITION_TYPE = "Transition Type";

    public SportsAdapter(Context context, ArrayList<Sport> mSportsData) {
        this.mSportsData = mSportsData;
        this.context = context;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Sport currentSport =mSportsData.get(position);
        // Populate the textView with data.
        viewHolder.bindTo(currentSport);
    }

    @Override
    public int getItemCount() {
        return mSportsData.size();
    }


    /**
     * Inner class for view holding.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mSportsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportsImage);

            // Sets the onClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        public void bindTo(Sport currentSport) {
            mTitleText.setText(currentSport.getTitle());
            mInfoText.setText(currentSport.getInfo());
            Glide.with(context).load(currentSport.getImageResource()).into(mSportsImage);
        }

        @Override
        public void onClick(View v) {
            Sport currentSport = mSportsData.get(getAdapterPosition());
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("title_key", currentSport.getTitle());
            detailIntent.putExtra("image_key", currentSport.getImageResource());
            detailIntent.putExtra("details_key", currentSport.getDetails());
            detailIntent.putExtra(TRANSITION_TYPE, "Slide");
            mainActivity.getWindow().setExitTransition(new Slide());
            context.startActivity(detailIntent, ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context).toBundle());
        }
    }
}
