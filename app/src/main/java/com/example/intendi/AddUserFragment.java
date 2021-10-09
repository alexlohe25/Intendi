package com.example.intendi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddUserFragment extends Fragment {

    ImageView avatar;
    TextView name, fechaNac, msgAdd;
    DBHandler dbHandler;
    Button createUser;
    ImageView changeAvatarButton;
    View changeAvatarMenu;
    ImageView backgroundMenu;
    ImageView delphi, sharky, iguanee, dogge, barky;
    int imageSrc;
    public AddUserFragment() {
        // Required empty public constructor
    }
    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = dbHandler.getInstance(getActivity().getApplicationContext());
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_user, container, false);
        avatar = view.findViewById(R.id.userAvatar);
        name = view.findViewById(R.id.miNombre);
        fechaNac = view.findViewById(R.id.miFechaNac);
        createUser = view.findViewById(R.id.playButton);
        msgAdd = view.findViewById(R.id.warningAddUser);
        imageSrc = R.drawable.delphi;
        avatar.setImageResource(imageSrc);
        name.setText("");
        fechaNac.setText("");

        changeAvatarButton = view.findViewById(R.id.editAvatar);
        changeAvatarMenu = view.findViewById(R.id.avatarMenu);
        backgroundMenu = view.findViewById(R.id.faded_background);
        delphi = view.findViewById(R.id.Intendi);
        sharky = view.findViewById(R.id.Sharky);
        iguanee = view.findViewById(R.id.Iguanee);
        dogge = view.findViewById(R.id.Dogge);
        barky = view.findViewById(R.id.Barky);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                User newUser = new User(0,"0", R.drawable.delphi, "00/00/00");
                boolean flagIsAdded = true;
                if (dbHandler.getUsersCount() < 6) {
                    newUser = dbHandler.addUser(name.getText().toString(), fechaNac.getText().toString(), imageSrc);
                    msgAdd.setText("¡" + name.getText().toString()+" se une al juego!");
                }else {
                    msgAdd.setText("Sólo puede haber 6 jugadores en este dispositivo");
                    flagIsAdded = false;
                }
                final User userToSend = newUser;
                final boolean flagToMenu = flagIsAdded;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent miIntent;
                        if (flagToMenu){
                            miIntent = new Intent(getActivity(), BottomNavigation.class);
                            miIntent.putExtra("User", userToSend);
                            startActivity(miIntent);
                            getActivity().finish();
                        }else {
                            miIntent = new Intent(getActivity(), MainActivity.class);
                            startActivity(miIntent);
                            getActivity().finish();
                        }
                    }
                }, 2000);

            }
        });
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatarMenu.setVisibility(View.VISIBLE);
            }
        });
        backgroundMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        delphi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.delphi;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        sharky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.sharky;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        iguanee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.iguanee;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        dogge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.dogge;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        barky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.barky;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
}