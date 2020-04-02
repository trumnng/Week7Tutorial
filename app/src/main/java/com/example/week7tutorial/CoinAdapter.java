package com.example.week7tutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import com.example.week7tutorial.Entities.Coin;


public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private List<Coin> mCoins;
    private RecyclerViewClickListener mListener;


//Create a Coin Constructor for the Adapter
    public CoinAdapter(List<Coin> coins, RecyclerViewClickListener listener) {
        mCoins = coins;
        mListener = listener;
    }


//Initialise the interface
    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }


//Create a CoinViewHolder to be used
    public static class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, value, change;
        private RecyclerViewClickListener mListener;
        // Create Constructor for CoinViewHolder
        public CoinViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            mListener = listener;
            view.setOnClickListener(this);
            name = view.findViewById(R.id.tvName);
            value = view.findViewById(R.id.tvValue);
            change = view.findViewById(R.id.tvChange);
        }

        //onClick Method for the interface
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }


    //Create CoinViewHolder ready for binding
    @Override
    public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
        return new CoinViewHolder(v, mListener);
    }


    //Bind elements to CoinViewHolder from CoinAdapter
    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        Coin coin = mCoins.get(position);
        holder.name.setText(coin.getName());
        holder.value.setText("$" + coin.getPriceUsd());
        holder.change.setText(coin.getPercentChange1h() + "%");
    }


    //Necessary RecyclerView component
    @Override
    public int getItemCount() {
        return mCoins.size();
    }
}

