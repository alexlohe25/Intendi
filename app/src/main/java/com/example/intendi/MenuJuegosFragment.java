package com.example.intendi;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

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
    CardView cardRandom;

    //Arrays with arguments for intent extra
    String game[] = new String[]{"whack", "memo", "nums", "laber", "piano"};
    String imageBub[] = new String[]{"inst_whack", "inst_memorama", "inst_nums", "inst_laberintendi", "inst_piano"};
    String imageLog[] = new String[]{"whack_ball", "memorama", "senda_numerica", "laberintendi", "piano"};
    String textBub[] = new String[]{
            "Toca los delfines con la pelota del color que te diga Intendi",
            "Toca 2 cartas para encontrar las parejas de objetos y sonidos",
            "Toca los recuadros que fueron iluminados según el orden que indican los números",
            "Acomoda las instrucciones para comer todos los peces y llegar al recuadro indicado",
            "Presiona las teclas del piano en la secuencia correcta"};

    User currentUser;
    DBHandler dbHandler;
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

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

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
        dbHandler = dbHandler.getInstance(getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_juegos, container, false);

        cardWhack = view.findViewById(R.id.cardWhack);
        cardMem = view.findViewById(R.id.cardMemorama);
        cardNums = view.findViewById(R.id.cardSenda);
        cardLaber = view.findViewById(R.id.cardLaberintendi);
        cardPiano = view.findViewById(R.id.cardPiano);
        cardRandom = view.findViewById(R.id.cardRandom);

        cardWhack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", game[0]);
                myIntent.putExtra("imageBubble",imageBub[0]);
                myIntent.putExtra("imageLogo",imageLog[0]);
                myIntent.putExtra("textBubble",textBub[0]);
                myIntent.putExtra("User", currentUser);
                startActivity(myIntent);
            }
        });

        cardWhack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent gameIntent = new Intent(getActivity(), WhackaGameActivity.class);
                gameIntent.putExtra("User", currentUser);
                startActivity(gameIntent);
                return true;
            }
        });

        cardMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", game[1]);
                myIntent.putExtra("imageBubble",imageBub[1]);
                myIntent.putExtra("imageLogo",imageLog[1]);
                myIntent.putExtra("textBubble",textBub[1]);
                myIntent.putExtra("User", currentUser);
                startActivity(myIntent);
            }
        });

        cardMem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent gameIntent = new Intent(getActivity(), MemoryGame.class);
                gameIntent.putExtra("User", currentUser);
                startActivity(gameIntent);
                return true;
            }
        });

        cardNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", game[2]);
                myIntent.putExtra("imageBubble",imageBub[2]);
                myIntent.putExtra("imageLogo",imageLog[2]);
                myIntent.putExtra("textBubble",textBub[2]);
                myIntent.putExtra("User", currentUser);
                startActivity(myIntent);
            }
        });

        cardNums.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent gameIntent = new Intent(getActivity(), SendaGame.class);
                gameIntent.putExtra("User", currentUser);
                startActivity(gameIntent);
                return true;
            }
        });

        cardLaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", game[3]);
                myIntent.putExtra("imageBubble",imageBub[3]);
                myIntent.putExtra("imageLogo",imageLog[3]);
                myIntent.putExtra("textBubble",textBub[3]);
                myIntent.putExtra("User", currentUser);
                startActivity(myIntent);
            }
        });

        cardLaber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent gameIntent = new Intent(getActivity(), LaberintendiGame.class);
                gameIntent.putExtra("User", currentUser);
                startActivity(gameIntent);
                return true;
            }
        });

        cardPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);
                myIntent.putExtra("game", game[4]);
                myIntent.putExtra("imageBubble",imageBub[4]);
                myIntent.putExtra("imageLogo",imageLog[4]);
                myIntent.putExtra("textBubble",textBub[4]);
                myIntent.putExtra("User", currentUser);
                startActivity(myIntent);
            }
        });

        cardPiano.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent gameIntent = new Intent(getActivity(), PianoGame.class);
                gameIntent.putExtra("User", currentUser);
                startActivity(gameIntent);
                return true;
            }
        });

        cardRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random(); //instance of random class
                int num_random = rand.nextInt(5);
                Intent myIntent = new Intent(getActivity(), PregameTemplate.class);

                myIntent.putExtra("game", game[num_random]);
                myIntent.putExtra("imageBubble",imageBub[num_random]);
                myIntent.putExtra("imageLogo",imageLog[num_random]);
                myIntent.putExtra("textBubble",textBub[num_random]);
                myIntent.putExtra("User", currentUser);
                startActivity(myIntent);
            }
        });

        cardRandom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Random rand = new Random(); //instance of random class
                int num_random = rand.nextInt(5);

                if(num_random == 0){
                    Intent gameIntent = new Intent(getActivity(), MemoryGame.class);
                    gameIntent.putExtra("User", currentUser);
                    startActivity(gameIntent);
                }else if(num_random == 1){
                    Intent gameIntent = new Intent(getActivity(), SendaGame.class);
                    gameIntent.putExtra("User", currentUser);
                    startActivity(gameIntent);
                }else if(num_random == 2){
                    Intent gameIntent = new Intent(getActivity(), LaberintendiGame.class);
                    gameIntent.putExtra("User", currentUser);
                    startActivity(gameIntent);
                }else if(num_random == 3){
                    Intent gameIntent = new Intent(getActivity(), WhackaGameActivity.class);
                    gameIntent.putExtra("User", currentUser);
                    startActivity(gameIntent);
                }else if(num_random == 4){
                    Intent gameIntent = new Intent(getActivity(), PianoGame.class);
                    gameIntent.putExtra("User", currentUser);
                    startActivity(gameIntent);
                }

                return true;
            }
        });

        return view;
    }
}