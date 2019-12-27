package com.math_riddles.screen.level;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.math_riddles.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardView> {

    private List<String> list;

    public class CardView extends RecyclerView.ViewHolder {

        public TextView textView;

        public CardView(View view) {
            super(view);

            textView = view.findViewById(R.id.tv_card);
        }
    }

    public RecyclerViewAdapter(List<String> ls) {
        list = ls;
    }

    @Override
    public CardView onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new CardView(card);
    }

    @Override
    public void onBindViewHolder(final CardView holder, final int position) {

        holder.textView.setText(list.get(position));

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

}
