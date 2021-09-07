package com.example.intendi;

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

import java.util.Objects;

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
        int users = 0;
        for (int i=0;i<3;i++){
            TableRow line = (TableRow)usersGrid.getChildAt(i);
            for(int j=0;j<2;j++)
            {
                users+=1;
                userCard = inflater.inflate(R.layout.user_login_space, null);;
                vp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                vp.weight = 1;

                name = (TextView) userCard.findViewById(R.id.username);
                name.setText("user " + users);
                profilePic = (ImageView) userCard.findViewById(R.id.userAvatar);
                profilePic.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dogge));

                userCard.setLayoutParams(vp);
                line.addView(userCard,j);
            }
        }
    }

}