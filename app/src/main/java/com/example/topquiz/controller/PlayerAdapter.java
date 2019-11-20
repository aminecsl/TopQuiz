package com.example.topquiz.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.topquiz.R;
import com.example.topquiz.model.User;
import com.example.topquiz.view.RankingPlayerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amine K. on 18/11/19.
 */
public class PlayerAdapter extends RecyclerView.Adapter<RankingPlayerViewHolder> {

    // FOR DATA
    private List<User> mPlayers;

    // CONSTRUCTOR
    public PlayerAdapter(List<User> mPlayers) {

        this.mPlayers = mPlayers;
    }

    @Override
    public RankingPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_players_ranking_item, parent, false);

        return new RankingPlayerViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(RankingPlayerViewHolder viewHolder, int position) {
        viewHolder.updateWithPlayer(this.mPlayers.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mPlayers.size();
    }

    public void refreshData(ArrayList<User> users){
        mPlayers = users;
        notifyDataSetChanged();
    }

}
