package com.example.intendi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MemoryBoardAdapter extends RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>{

    private int numPieces;
    public static int marginSize() { return 10; }

    public MemoryBoardAdapter(Context context, int numPieces) {
        this.numPieces = numPieces;
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
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Click on pos: " + position);
                }
            });
        }
    }
}
