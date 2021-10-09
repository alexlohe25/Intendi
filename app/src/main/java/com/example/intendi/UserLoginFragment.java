package com.example.intendi;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class UserLoginFragment extends Fragment {
    DBHandler dbHandler;
    TableLayout usersGrid;
    LayoutInflater inflater;
    View  layout;
    View  userCard;
    TableRow.LayoutParams vp;
    TextView name;
    ImageView profilePic;
    ArrayList<User> users;
    public UserLoginFragment() {
        // Required empty public constructor
    }
    public void setDbHandler(DBHandler handler){
        dbHandler = handler;
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
        //dbHandler = dbHandler.getInstance(getActivity());
        if (getArguments() != null) {
        }
    }
    public void fillUsers(LayoutInflater inflater){
        users = new ArrayList<>();
        users = dbHandler.getAllUsers();
        int numberOfUsers = users.size();
        if( numberOfUsers > 0){
            int userCount = 0;
            int numberLines = numberOfUsers / 2;
            if (numberOfUsers % 2 == 1) numberLines++;
            int usersToShow = numberOfUsers, viewsPerLine;
            for (int i=0;i<numberLines;i++){
                TableRow line = (TableRow)usersGrid.getChildAt(i);
                if (usersToShow % 2 == 0){
                    viewsPerLine = 2;
                    usersToShow -= 2;
                }else{
                    viewsPerLine = 1;
                    usersToShow --;
                }
                for(int j=0;j<viewsPerLine;j++)
                {
                    userCard = inflater.inflate(R.layout.user_login_space, null);
                    vp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    vp.weight = 1;

                    name = (TextView) userCard.findViewById(R.id.username);
                    name.setText(users.get(userCount).getUsername());
                    profilePic = (ImageView) userCard.findViewById(R.id.userAvatar);
                    profilePic.setImageDrawable(ContextCompat.getDrawable(requireContext(), users.get(userCount).getImageSource()));

                    userCard.setLayoutParams(vp);
                    line.addView(userCard,j);
                    final int currentUser = userCount;
                    userCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            Intent myIntent = new Intent(getActivity(), BottomNavigation.class);
                            myIntent.putExtra("User", users.get(currentUser));
                            startActivity(myIntent);
                            getActivity().finish();
                        }
                    });
                    userCount+=1;
                }
            }
        }
    }

}