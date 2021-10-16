package com.example.intendi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AjustesFragment extends Fragment {

    ImageView changeAvatarButton;
    View changeAvatarMenu, popUpDelete;
    ImageView backgroundMenu, backgroundMenuD;
    ImageView delphi, sharky, iguanee, dogge, barky,kitee, parret, cheeky;
    Button logOutButton, updateButton,deleteButton;
    Button deleteBtn, cancelBtn;
    User currentUser;
    int originalSrc, newAvatar;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView username, msgUpdate, confirmUserEditText, deleteMsg;
    ImageView userAvatar;
    DBHandler dbHandler;
    public AjustesFragment() {
        // Required empty public constructor
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public static AjustesFragment newInstance(String param1, String param2) {
        AjustesFragment fragment = new AjustesFragment();
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
         View view = inflater.inflate(R.layout.fragment_ajustes, container, false);
         username = view.findViewById(R.id.usernameAjustes);
         userAvatar = view.findViewById(R.id.avatarAjustes);
         msgUpdate = view.findViewById(R.id.msgUpdate);

         updateButton = view.findViewById(R.id.updateButton);
         logOutButton= view.findViewById(R.id.playButton);
         deleteButton = view.findViewById(R.id.deleteButton);
         changeAvatarButton = view.findViewById(R.id.editAvatar);
         changeAvatarMenu = view.findViewById(R.id.avatarMenu);
         changeAvatarMenu.setVisibility(View.INVISIBLE);
         popUpDelete = view.findViewById(R.id.confirmDelete);
         delphi = view.findViewById(R.id.Intendi);
         sharky = view.findViewById(R.id.Sharky);
         iguanee = view.findViewById(R.id.Iguanee);
         dogge = view.findViewById(R.id.Dogge);
         barky = view.findViewById(R.id.Barky);
         kitee = view.findViewById(R.id.Kitee);
         parret = view.findViewById(R.id.Parret);
         cheeky = view.findViewById(R.id.Cheeky);
         deleteBtn = view.findViewById(R.id.deleteUser);
         cancelBtn = view.findViewById(R.id.cancelDelete);
         confirmUserEditText = view.findViewById(R.id.confirmUser);
         deleteMsg = view.findViewById(R.id.msgDelete);
         backgroundMenu = view.findViewById(R.id.faded_background);
         backgroundMenuD = view.findViewById(R.id.faded_backgroundD);

         username.setText(currentUser.getUsername());
         userAvatar.setImageResource(currentUser.getImageSource());

         originalSrc = currentUser.getImageSource();
         newAvatar = currentUser.getImageSource();
         msgUpdate.setText("");
         logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logOut();
                }
            });
         deleteButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 popUpDelete.setVisibility(View.VISIBLE);
             }
         });
        backgroundMenuD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDelete.setVisibility(View.INVISIBLE);
                confirmUserEditText.setText("");
                deleteMsg.setText("");
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToDelete = confirmUserEditText.getText().toString();
                if(userToDelete.length() > 0){
                    if(userToDelete.equals(currentUser.getUsername())){
                        dbHandler.deleteUser(currentUser.getUser_id());
                        Intent miIntent = new Intent( getActivity(), MainActivity.class);
                        startActivity(miIntent);
                        getActivity().finish();
                    }else{
                        confirmUserEditText.setText("");
                        deleteMsg.setText("No se pudo borrar: El nombre no coincide");
                    }
                }else{
                    confirmUserEditText.setText("");
                    deleteMsg.setText("Introduzca un nombre");
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDelete.setVisibility(View.INVISIBLE);
                confirmUserEditText.setText("");
                deleteMsg.setText("");
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
                newAvatar = R.drawable.delphi;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        sharky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.sharky;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        iguanee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.iguanee;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        dogge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.dogge;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        barky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.barky;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        kitee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.kitee;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        parret.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.parret;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        cheeky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newAvatar = R.drawable.cheeky;
                userAvatar.setImageResource(newAvatar);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

         return view;
    }
    public void updateUser(){
        updateButton.setEnabled(false);
        if(username.getText().toString().length() > 0){
            String newName = username.getText().toString();
            System.out.println(currentUser.getUsername()+ " " + newName);
            System.out.println(currentUser.getImageSource() + " " + originalSrc);
            boolean areInputsEqual = (originalSrc == newAvatar) && (newName.equals(currentUser.getUsername()));
            System.out.println(areInputsEqual);
            if (!areInputsEqual){
                currentUser.setUsername(newName);
                currentUser.setImageSource(newAvatar);
                dbHandler.updateCurrentUser(currentUser);
                username.setText(currentUser.getUsername());
                originalSrc = currentUser.getImageSource();
                newAvatar = currentUser.getImageSource();
                msgUpdate.setTextColor(getResources().getColor(R.color.marinoIntendi));
                msgUpdate.setText("¡Usuario actualizado!");
            }else {
                msgUpdate.setText("No hubo nuevos cambios del usuario");
                msgUpdate.setTextColor(getResources().getColor(R.color.red_eat));
            }

        }else {
            msgUpdate.setText("Introduce un nombre de usuario");
            msgUpdate.setTextColor(getResources().getColor(R.color.red_eat));
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                msgUpdate.setText("");
                updateButton.setEnabled(true);
            }
        }, 2000);
        //userAvatar.setImageResource(currentUser.getImageSource());
    }
    public void logOut(){
        Intent miIntent = new Intent( getActivity(), MainActivity.class);
        startActivity(miIntent);
        getActivity().finish();
    }

}