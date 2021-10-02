package com.example.intendi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SendaBoardAdapter extends RecyclerView.Adapter<SendaBoardAdapter.ViewHolder> {

    private SendaManager sendaManager;
    public static int marginSize() { return 20; }
    private CardClickListener cardClickListener;

    interface CardClickListener{
        public default void onCardClicked(int position){

        }
    }

    public SendaBoardAdapter(Context context, SendaManager sendaManager, CardClickListener cardListener) {
        this.sendaManager = sendaManager;
        this.cardClickListener = cardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int cardWidth = parent.getWidth() / sendaManager.getBoardLen() - (2 * marginSize());
        int cardHeight = parent.getHeight() / sendaManager.getBoardLen() - (2 * marginSize());
        int cardSideLength = Math.min(cardWidth, cardHeight);

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.senda_card, parent, false);

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
    public int getItemCount()  {
        return this.sendaManager.getBoardSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textNumber = itemView.findViewById(R.id.textNumber);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int position) {
            int[] board = sendaManager.getBoardNumbers();
            if(board[position] != -1) textNumber.setText(String.valueOf(board[position] + 1));
            else{
                textNumber.setText("");
            }

            textNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardClickListener.onCardClicked(position);
                }
            });
        }
    }
}
