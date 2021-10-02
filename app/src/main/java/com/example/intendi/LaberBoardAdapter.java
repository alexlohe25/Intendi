package com.example.intendi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LaberBoardAdapter extends RecyclerView.Adapter<LaberBoardAdapter.ViewHolder> {

    private LaberManager laberManager;
    public static int marginSize() { return 20; }

    public LaberBoardAdapter(Context context, LaberManager laberManager) {
        this.laberManager = laberManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int cardWidth = parent.getWidth() / laberManager.getLabWidth() - (2 * marginSize());
        int cardHeight = parent.getHeight() / laberManager.getLabHeight() - (2 * marginSize());
        int cardSideLength = Math.min(cardWidth, cardHeight);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.laberintendi_card, parent, false);

        ViewGroup.LayoutParams layoutParams = v.findViewById(R.id.cardView).getLayoutParams();
        layoutParams.width = cardSideLength;
        layoutParams.height = cardSideLength;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = marginSize();
        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = marginSize();
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = marginSize();
        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = marginSize();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LaberBoardAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.laberManager.getLabSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image = itemView.findViewById(R.id.imageCard);
        private CardView card = itemView.findViewById(R.id.cardView);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            int[] board = laberManager.getLabNumbers();
            image.setImageResource(laberManager.getImageResource(board[position]));
            card.setCardBackgroundColor(Color.parseColor(laberManager.getColorResource(board[position])));
        }

    }
}
