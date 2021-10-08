package com.example.intendi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddUserFragment extends Fragment {

    ImageView avatar;
    TextView name, fechaNac;
    DBHandler dbHandler;
    Button createUser;
    public AddUserFragment() {
        // Required empty public constructor
    }
    public void setDbHandler(DBHandler handler){
        dbHandler = handler;
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
        dbHandler = dbHandler.getInstance(getActivity());
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
        avatar.setTag(R.drawable.delphi);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int imageSrc = (Integer)avatar.getTag();
                dbHandler.addUser(name.getText().toString(), fechaNac.getText().toString(), imageSrc);
                System.out.println("User added");
            }
        });
        return view;
    }


}