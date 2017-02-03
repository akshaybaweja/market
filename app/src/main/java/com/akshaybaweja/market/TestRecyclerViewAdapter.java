package com.akshaybaweja.market;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ArrayList> contents;

    public TestRecyclerViewAdapter(List<ArrayList> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView shop_name = (TextView) holder.itemView.findViewById(R.id.shop_name);
        TextView shop_address = (TextView) holder.itemView.findViewById(R.id.shop_address);
        TextView shop_contact = (TextView) holder.itemView.findViewById(R.id.shop_contact);
        TextView closed_on = (TextView) holder.itemView.findViewById(R.id.closedOn);
        TextView remarks = (TextView) holder.itemView.findViewById(R.id.remarks);

        ArrayList details = contents.get(position);
        shop_name.setText(String.valueOf(details.get(0)));
        shop_address.setText(String.valueOf(details.get(1)));
        shop_contact.setText(String.valueOf(details.get(2)));
        closed_on.setText("Closed on ".concat(String.valueOf(details.get(3))));

        if(String.valueOf(details.get(5))!="null") {
            remarks.setText(String.valueOf(details.get(5)));
        } else {
            remarks.setVisibility(TextView.INVISIBLE);
            TextView label = (TextView) holder.itemView.findViewById(R.id.remarkslabel);
            label.setText("No Data Available");
            RelativeLayout rv1 = (RelativeLayout) holder.itemView.findViewById(R.id.relativeLayout1);
            RelativeLayout rv2 = (RelativeLayout) holder.itemView.findViewById(R.id.relativeLayout2);
            rv2.setVisibility(RelativeLayout.INVISIBLE);
            rv1.setVisibility(RelativeLayout.VISIBLE);
        }
    }
}