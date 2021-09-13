package com.example.intendi;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class UserLoginFragment extends Fragment {

    TableLayout usersGrid;
    LayoutInflater inflater;
    View  layout;
    View  userCard;
    TableRow.LayoutParams vp;
    TextView name;
    ImageView profilePic;

    public UserLoginFragment() {
        // Required empty public constructor
    }


    public static UserLoginFragment newInstance() {
        UserLoginFragment fragment = new UserLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_login, container, false);
        usersGrid = (TableLayout) v.findViewById(R.id.usersGrid);
        fillUsers(inflater);
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    public void fillUsers(LayoutInflater inflater){

        User newU = new User("Sahid", R.drawable.dogge);
        User newU1 = new User("Alex", R.drawable.delphi);
        User newU2 = new User("David", R.drawable.iguanee);
        User newU3 = new User("Pablo", R.drawable.sharky);
        User[] users = {newU,newU1,newU2,newU3};

        int userCount = 0;
        for (int i=0;i<2;i++){
            TableRow line = (TableRow)usersGrid.getChildAt(i);
            for(int j=0;j<2;j++)
            {


                userCard = inflater.inflate(R.layout.user_login_space, null);;
                vp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                vp.weight = 1;

                name = (TextView) userCard.findViewById(R.id.username);
                name.setText(users[userCount].getUsername());
                profilePic = (ImageView) userCard.findViewById(R.id.userAvatar);
                profilePic.setImageDrawable(ContextCompat.getDrawable(requireContext(), users[userCount].getImageSource()));

                userCard.setLayoutParams(vp);
                line.addView(userCard,j);
                userCount+=1;
            }
        }
    }


}