package com.math_riddles.screen.challenges;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.math_riddles.R;
import com.math_riddles.core.model.Challenge;
import com.math_riddles.core.repository.ScoreRepository;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardView> {

    private List<Challenge> list;
    private ScoreRepository scoreRepository;

    public class CardView extends RecyclerView.ViewHolder {

        public TextView textView;

        public CardView(View view) {
            super(view);

            textView = view.findViewById(R.id.tv_card);
            scoreRepository = new ScoreRepository();
        }
    }

    public RecyclerViewAdapter(List<Challenge> ls) {
        list = ls;
    }

    @Override
    public CardView onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardView(card);
    }

    @Override
    public void onBindViewHolder(final CardView holder, final int position) {
        initCardList(holder, position);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    private void initCardList(final CardView holder, final int position) {
        Challenge challenge = list.get(position);
        holder.textView.setText(challenge.getQuestion());
        initCardListStyle(challenge, holder);
    }

    private void initCardListStyle(Challenge challenge, final CardView holder) {
        boolean isCardActive = isCardActive(challenge);

        if (isCardActive) {
            initActiveCardStyle(holder);
        } else {
            initInactiveCardStyle(holder);
        }
    }

    private Context getHolderContext(final CardView holder) {
        return holder.itemView.getContext();
    }

    private android.support.v7.widget.CardView getCardView(final CardView holder) {
        return (android.support.v7.widget.CardView) holder.itemView;
    }

    private boolean isCardActive(Challenge challenge) {
        return challenge.getId() != -1 && (challenge.getId() <= scoreRepository.getFirst().getChallengeId());
    }

    private void initActiveCardStyle(final CardView holder) {
        android.support.v7.widget.CardView cardView = getCardView(holder);
        Context context = getHolderContext(holder);

        cardView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_card_active));
        holder.textView.setTextColor(Color.rgb(255,255,255));
    }

    private void initInactiveCardStyle( final CardView holder) {
        android.support.v7.widget.CardView cardView = getCardView(holder);
        Context context = getHolderContext(holder);

        cardView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_card_inactive));
        holder.textView.setTextColor(Color.rgb(255,0,0));
    }
}
