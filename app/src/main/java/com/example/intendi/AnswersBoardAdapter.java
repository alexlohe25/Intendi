package com.example.intendi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnswersBoardAdapter extends RecyclerView.Adapter<AnswersBoardAdapter.ViewHolder> {

    private LaberManager laberManager;
    public static int marginSize() { return 10; }

    public AnswersBoardAdapter(Context context, LaberManager laberManager) {
        this.laberManager = laberManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int cardWidth = parent.getWidth() / 5 - (2 * marginSize());
        int cardHeight = parent.getHeight() / 2 - (2 * marginSize());
        int cardSideLength = Math.min(cardWidth, cardHeight);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.laberintendi_card, parent, false);

        ViewGroup.LayoutParams layoutParams = v.findViewById(R.id.cardView).getLayoutParams();
        layoutParams.width = cardSideLength;
        layoutParams.height = cardSideLength;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = marginSize();
        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = marginSize();
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = marginSize();
        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = marginSize();

        return new AnswersBoardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersBoardAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return this.laberManager.getAnswerUser().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image = itemView.findViewById(R.id.imageCard);
        private CardView card = itemView.findViewById(R.id.cardView);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            ArrayList<Integer> answers = laberManager.getAnswerUser();
            if(answers.get(position) == 1){ //Move
                image.setImageResource(R.drawable.move);
                card.setCardBackgroundColor(Color.parseColor("#3DC38E"));
            }else if(answers.get(position) == 2){ //Turn left
                image.setImageResource(R.drawable.turn_left);
                card.setCardBackgroundColor(Color.parseColor("#3997B4"));
            }else if(answers.get(position) == 3){ //Turn right
                image.setImageResource(R.drawable.turn_right);
                card.setCardBackgroundColor(Color.parseColor("#FFB754"));
            }else if(answers.get(position) == 4){ //Eat
                image.setImageResource(R.drawable.eat);
                card.setCardBackgroundColor(Color.parseColor("#D94245"));
            }

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    laberManager.deleteElementAnswer(position);
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),laberManager.getAnswerUser().size());
                }
            });
        }

    }
}
