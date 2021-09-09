package com.example.intendi;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuJuegosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuJuegosFragment extends Fragment {

    CardView cardWhack;
    CardView cardMem;
    CardView cardNums;
    CardView cardLaber;
    CardView cardPiano;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuJuegosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuJuegosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuJuegosFragment newInstance(String param1, String param2) {
        MenuJuegosFragment fragment = new MenuJuegosFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_juegos, container, false);

        cardWhack = view.findViewById(R.id.cardWhack);
        cardMem = view.findViewById(R.id.cardMemorama);
        cardNums = view.findViewById(R.id.cardSenda);
        cardLaber = view.findViewById(R.id.cardLaberintendi);
        cardPiano = view.findViewById(R.id.cardPiano);

        cardWhack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", "whack");
                myIntent.putExtra("imageBubble","inst_whack");
                myIntent.putExtra("imageLogo","whack_ball");
                myIntent.putExtra("textBubble","Toca los delfines con la pelota del color que te diga Intendi");
                startActivity(myIntent);
            }
        });

        cardMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", "memo");
                myIntent.putExtra("imageBubble","inst_memorama");
                myIntent.putExtra("imageLogo","memorama");
                myIntent.putExtra("textBubble","Toca 2 cartas para encontrar las parejas de objetos y sonidos");
                startActivity(myIntent);
            }
        });

        cardNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", "nums");
                myIntent.putExtra("imageBubble","inst_nums");
                myIntent.putExtra("imageLogo","senda_numerica");
                myIntent.putExtra("textBubble","Toca los recuadros que fueron iluminados según el orden que indican los números");
                startActivity(myIntent);
            }
        });

        cardLaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", "laber");
                myIntent.putExtra("imageBubble","inst_laberintendi");
                myIntent.putExtra("imageLogo","laberintendi");
                myIntent.putExtra("textBubble","Acomoda las instrucciones para comer todos los peces y llegar al recuadro indicado");
                startActivity(myIntent);
            }
        });

        cardPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", "piano");
                myIntent.putExtra("imageBubble","inst_piano");
                myIntent.putExtra("imageLogo","piano");
                myIntent.putExtra("textBubble","Presiona las teclas del piano en la secuencia correcta");
                startActivity(myIntent);
            }
        });
        return view;
    }
}