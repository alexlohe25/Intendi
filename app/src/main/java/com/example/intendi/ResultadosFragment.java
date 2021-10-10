package com.example.intendi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Typeface tf;
    CardView cardWhack, cardMem, cardNums, cardLaber, cardPiano;
    TextView resultDetail, result1, result2, result1DateLabel, result2DateLabel;
    ImageView objectIntendi;
    User currentUser;
    DBHandler dbHandler;
    String currentGame;
    public ResultadosFragment() {
        // Required empty public constructor
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static ResultadosFragment newInstance(String param1, String param2) {
        ResultadosFragment fragment = new ResultadosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHandler = dbHandler.getInstance(getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_resultados, container, false);
        cardWhack = view.findViewById(R.id.cardWhack);
        cardMem = view.findViewById(R.id.cardMemorama);
        cardNums = view.findViewById(R.id.cardSenda);
        cardLaber = view.findViewById(R.id.cardLaberintendi);
        cardPiano = view.findViewById(R.id.cardPiano);
        resultDetail = (TextView) view.findViewById(R.id.tendenciaResults);
        result1DateLabel = (TextView) view.findViewById(R.id.topDate);
        result1 = (TextView) view.findViewById(R.id.firstResult);
        result2DateLabel = (TextView) view.findViewById(R.id.lastDate);
        result2 = (TextView) view.findViewById(R.id.secondResult);

        objectIntendi = view.findViewById(R.id.objectIntendi);

       // miChart = (LineChart) view.findViewById(R.id.idChart);
        tf = ResourcesCompat.getFont(getActivity(), R.font.nunito_semibold);
        //generaChart("Whack-A-Ball", view);
        currentGame = "Whack-A-Ball";

        //Iniciar con Whack
        generaChart("Whack-A-Ball", view);
        currentGame = "Whack-A-Ball";

        cardWhack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentGame != "Puntaje en Whack-A-Ball"){
                    generaChart("Whack-A-Ball", view);
                    currentGame = "Whack-A-Ball";
                    objectIntendi.setImageResource(R.drawable.yellow_ball);
                }

            }
        });
        cardMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentGame != "Puntaje en Memorama"){
                    generaChart("Memorama", view);
                    currentGame = "Memorama";
                    objectIntendi.setImageResource(R.drawable.mem_card);
                }
            }
        });
        cardNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentGame != "Puntaje en Senda numérica"){
                    generaChart("Senda numérica", view);
                    currentGame = "Senda numérica";
                    objectIntendi.setImageResource(R.drawable.num_card);
                }
            }
        });
        cardLaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentGame != "Puntaje en Laberintendi"){
                    generaChart("Laberintendi", view);
                    currentGame = "Laberintendi";
                    objectIntendi.setImageResource(R.drawable.lab_card);
                }
            }
        });
        cardPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentGame != "Puntaje en Piano"){
                    generaChart("Piano", view);
                    currentGame = "Piano";
                    objectIntendi.setImageResource(R.drawable.piano_card);
                }
            }
        });

        return view;
    }
    public void generaChart(String game, View view){
        ArrayList<Result> resultsFromGame = dbHandler.getResultsFromGame(currentUser.getUser_id(),game);
        if (resultsFromGame.size() == 0){
            result1DateLabel.setText("Mejor puntaje\n");
            result1.setText("");
            result2DateLabel.setText("Mejor puntaje\n");
            result2.setText("");
            resultDetail.setText("Parece que aún no has jugado " + game);
        }else if (resultsFromGame.size() == 1){
            int result1Score = resultsFromGame.get(0).getScore();
            String result1Date = resultsFromGame.get(0).getDateOfGame();
            result1DateLabel.setText("Mejor puntaje "+ result1Date);
            result1.setText(String.valueOf(result1Score));
            result2DateLabel.setText("Mejor puntaje \n");
            result2.setText("");
            resultDetail.setText("Te falta 1 juego de " + game + " para ver tu avance");
        }else{
            int result1Score = resultsFromGame.get(0).getScore();
            String result1Date = resultsFromGame.get(0).getDateOfGame();
            int result2Score = resultsFromGame.get(1).getScore();
            String result2Date = resultsFromGame.get(1).getDateOfGame();
            result1.setText(String.valueOf(result1Score));
            result1DateLabel.setText("Mejor puntaje "+ result1Date);
            result2.setText(String.valueOf(result2Score));
            result2DateLabel.setText("Último puntaje "+ result2Date);

            String tendencia = "¡Otro récord en " + game + "!";
            if(result2Score < result1Score)
                tendencia = "¡Buen intento "+ currentUser.getUsername() + "!";
            resultDetail.setText(tendencia);
        }
    }
}
