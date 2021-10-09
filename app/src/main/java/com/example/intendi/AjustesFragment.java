package com.example.intendi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AjustesFragment extends Fragment {

    ImageView changeAvatarButton;
    View changeAvatarMenu;
    ImageView backgroundMenu;

    Button logOutButton, updateButton;
    User currentUser;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView username;
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
         userAvatar.setTag(R.drawable.delphi);
         updateButton = view.findViewById(R.id.updateButton);
         logOutButton= view.findViewById(R.id.playButton);
         changeAvatarButton = view.findViewById(R.id.editAvatar);
         changeAvatarMenu = view.findViewById(R.id.avatarMenu);
         backgroundMenu = view.findViewById(R.id.faded_background);

         username.setText(currentUser.getUsername());
         userAvatar.setImageResource(currentUser.getImageSource());
         updateButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 updateUser();
             }
         });
         logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logOut();
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

         return view;
    }
    public void updateUser(){
        currentUser.setUsername(username.getText().toString());
        currentUser.setImageSource((Integer)userAvatar.getTag());
        dbHandler.updateCurrentUser(currentUser);
        username.setText(currentUser.getUsername());
        userAvatar.setImageResource(currentUser.getImageSource());
    }
    public void logOut(){
        Intent miIntent = new Intent( getActivity(), MainActivity.class);
        startActivity(miIntent);
        getActivity().finish();
    }
}