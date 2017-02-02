package com.akshaybaweja.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static int tabPosition;

    public static RecyclerViewFragment newInstance(int position) {
        RecyclerViewFragment f = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        tabPosition = getArguments().getInt("position",-1);

        switch(tabPosition){
            case 0: sendRequest("http://akshaybaweja.com/market/databro.php?parameter=getCategory&category=electronics");
                break;
            case 1: sendRequest("http://akshaybaweja.com/market/databro.php?parameter=getCategory&category=hardware");
                break;
            case 2: sendRequest("http://akshaybaweja.com/market/databro.php?parameter=getCategory&category=tools");
                break;
            case 3: sendRequest("http://akshaybaweja.com/market/databro.php?parameter=getCategory&category=service");
                break;
            case 4: sendRequest("http://akshaybaweja.com/market/databro.php?parameter=getCategory&category=miscellaneous");
                break;
        }
    }

    private void sendRequest(String JSON_URL){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.toString());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        List<ArrayList> mContentItems = new ArrayList<>();
        for(int i=0;i<ParseJSON.nshops;i++){
            ArrayList<String> myarray = new ArrayList<>();
            myarray.add(ParseJSON.names[i]);
            myarray.add(ParseJSON.addresses[i]);
            myarray.add(ParseJSON.contacts[i]);
            myarray.add(ParseJSON.closedOn[i]);
            myarray.add(ParseJSON.rates[i]);
            myarray.add(ParseJSON.remarks[i]);
            mContentItems.add(myarray);
        }
        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}