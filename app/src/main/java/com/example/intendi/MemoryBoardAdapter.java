package com.example.intendi;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MemoryBoardAdapter extends RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>{
    Context activityContext;
    private int numPieces;
    private CardClickListener cardClickListener;
    public static int marginSize() { return 10; }

    List<MemoryCard> cardImages;
    public MemoryBoardAdapter(Context context, int numPieces, List<MemoryCard> randomizedImages, CardClickListener cardClickListener) {
        this.numPieces = numPieces;
        this.cardImages = randomizedImages;
        this.cardClickListener = cardClickListener;
        activityContext = context;
    }
    public interface CardClickListener{
        void onCardClicked(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int cardWidth = parent.getWidth() / 2 - (2 * marginSize());
        int cardHeight = parent.getHeight() / 4 - (2 * marginSize());
        int cardSideLength = Math.min(cardWidth, cardHeight);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.memory_card, parent, false);

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numPieces;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageButton imageButton = itemView.findViewById(R.id.imageButton);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            MemoryCard curCard = cardImages.get(position);

            if(curCard.isFaceUp) {
                if (curCard.isSound) {
                    imageButton.setImageResource(R.drawable.ic_baseline_headphones_24);
                } else {
                    imageButton.setImageResource(curCard.identifier);
                }
            }
            else
                imageButton.setImageResource(R.drawable.card_square);

            if(curCard.isMatched)
                if (curCard.isSound)
                    imageButton.setImageResource(R.drawable.ic_baseline_headphones_24);
                else
                    imageButton.setImageResource(curCard.identifier);

            ColorStateList colorStateList;
            if (curCard.isMatched){
                imageButton.setAlpha(.4f);
                colorStateList = ContextCompat.getColorStateList(imageButton.getContext(), R.color.amarilloIntendi);
            }
            else{
                imageButton.setAlpha(1.0f);
                colorStateList = null;
            }
            ViewCompat.setBackgroundTintList(imageButton, colorStateList);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardClickListener.onCardClicked(position);
                }
            });
        }
    }
}
