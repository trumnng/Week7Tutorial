package com.example.week7tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.week7tutorial.Entities.Coin;
import com.example.week7tutorial.Entities.CoinLoreResponse;
import com.google.gson.Gson;

import java.util.List;



public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "au.edu.unsw.infs3634.beers";
    private boolean bigScreen;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        bigScreen = setBigScreen(); //Sets true or false depending on whether there is a detail_container

        CoinAdapter.RecyclerViewClickListener listener = new CoinAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (bigScreen) {
                    //Refer to tutorial slides
                    FragmentManager myManager = getSupportFragmentManager();
                    FragmentTransaction myTransaction = myManager.beginTransaction();
                    Fragment myFragment = new DetailFragment();
                    Bundle arguments = new Bundle();
                    arguments.putInt("pos", position);
                    myFragment.setArguments(arguments);
                    myTransaction.replace(R.id.detailContainer, myFragment);
                    myTransaction.commit();
                }
                else {
                    launchDetailActivity(position);
                }
            }
        };

        Gson gson = new Gson();
        CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        List<Coin> list = response.getData();

        mAdapter = new CoinAdapter(list, listener);
        mRecyclerView.setAdapter(mAdapter);

    }


    private boolean setBigScreen() {
        if(findViewById(R.id.detailContainer)  ==  null) {
            return false;
        }
        else {
            return true;
        }
    }


    private void launchDetailActivity(int message) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}
