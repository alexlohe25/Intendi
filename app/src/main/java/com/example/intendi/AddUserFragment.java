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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class AddUserFragment extends Fragment {

    ImageView avatar;
    TextView name, dia,mes,anio, msgAdd;
    DBHandler dbHandler;
    Button createUser;
    ImageView changeAvatarButton;
    View changeAvatarMenu;
    ImageView backgroundMenu;
    ImageView delphi, sharky, iguanee, dogge, barky, kitee, parret, cheeky;
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
        dia = view.findViewById(R.id.miDiaNac);
        mes = view.findViewById(R.id.miMesNac);
        anio = view.findViewById(R.id.miAnioNac);
        createUser = view.findViewById(R.id.playButton);
        msgAdd = view.findViewById(R.id.warningAddUser);
        imageSrc = R.drawable.delphi;
        avatar.setImageResource(imageSrc);
        name.setHint("Nombre");
        dia.setHint("Día");
        mes.setHint("Mes");
        anio.setHint("Año");
        changeAvatarButton = view.findViewById(R.id.editAvatar);
        changeAvatarMenu = view.findViewById(R.id.avatarMenu);
        changeAvatarMenu.setVisibility(View.INVISIBLE);
        backgroundMenu = view.findViewById(R.id.faded_background);
        delphi = view.findViewById(R.id.Intendi);
        sharky = view.findViewById(R.id.Sharky);
        iguanee = view.findViewById(R.id.Iguanee);
        dogge = view.findViewById(R.id.Dogge);
        barky = view.findViewById(R.id.Barky);
        kitee = view.findViewById(R.id.Kitee);
        parret = view.findViewById(R.id.Parret);
        cheeky = view.findViewById(R.id.Cheeky);
        //create user method
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //button setter so it can be clicked while method is running
                createUser.setEnabled(false);
                //valid input date
                boolean isDateValid = isDateValid(dia.getText().toString(), mes.getText().toString(), anio.getText().toString());
                int lengthName = name.getText().toString().length();
                //if length of given name is not an empty string
                if (lengthName > 0){
                    if (isDateValid){
                        //create User to insert into database
                        User newUser = new User(0,"0", R.drawable.delphi, "00/00/00");
                        //flag to know if the user is added
                        boolean flagIsAdded = true;
                        //if user count in the device is smaller than 6, insert newUser into database
                        if (dbHandler.getUsersCount() < 6) {
                            String dateOfBirth = dia.getText() + "/" + mes.getText() + "/" + mes.getText();
                            newUser = dbHandler.addUser(name.getText().toString(), dateOfBirth, imageSrc);
                            msgAdd.setTextColor(getResources().getColor(R.color.marinoIntendi));
                            msgAdd.setText("¡" + name.getText().toString()+" se une al juego!");

                        }else { //otherwise, a warning message is shown
                            msgAdd.setTextColor(getResources().getColor(R.color.red_eat));
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
                                //if user is added to database, send user to the game menu
                                if (flagToMenu){
                                    miIntent = new Intent(getActivity(), BottomNavigation.class);
                                    miIntent.putExtra("User", userToSend);
                                    startActivity(miIntent);
                                    getActivity().finish();
                                }else { //if not, return the user to login menu
                                    miIntent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(miIntent);
                                    getActivity().finish();
                                }
                                //enable create button again
                                createUser.setEnabled(true);
                            }
                        }, 500);
                    }else { //otherwise, a warning message is shown
                        msgAdd.setTextColor(getResources().getColor(R.color.red_eat));
                        msgAdd.setText("Introduce una fecha válida");
                    }
                }else { //otherwise, a warning message is shown
                    msgAdd.setTextColor(getResources().getColor(R.color.red_eat));
                    msgAdd.setText("Introduce un nombre de usuario");
                }
                //hide the warning message and enable current button again
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        msgAdd.setText("");
                        createUser.setEnabled(true);
                    }
                }, 1500);
            }
        });
        //select avatar popup calling functions
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
        //set the new user avatar according to the image clicked
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
        kitee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.kitee;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        parret.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.parret;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        cheeky.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                imageSrc = R.drawable.cheeky;
                avatar.setImageResource(imageSrc);
                changeAvatarMenu.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
    //validation of given date data in user input
    public boolean isDateValid(String diaDado, String mesDado, String anioDado){
        int dayGiven,monthGiven, yearGiven;
        //convert given strings to integer, if one of them is empty input that value will be zero
        if(diaDado.length() == 0)
            dayGiven = 0;
        else
            dayGiven = Integer.parseInt(diaDado);
        if(mesDado.length() == 0)
            monthGiven = 0;
        else
            monthGiven = Integer.parseInt(mesDado);
        if(anioDado.length() == 0)
            yearGiven = 0;
        else
            yearGiven = Integer.parseInt(anioDado);
        //if the given day and month is out of the calendar limits
        if (dayGiven <= 0 || dayGiven > 31)
            return false;
        if (monthGiven <= 0 || monthGiven > 12)
            return false;
        //validate if current year is bigger than given year
        Date thisYear = new Date();
        SimpleDateFormat yf = new SimpleDateFormat("yyyy");
        String year = yf.format(thisYear);
        if (yearGiven >= Integer.parseInt(year))
            return false;
        //treemap to keep track of each month and their last day in calendar
        Map<Integer, Integer> mesyFin = new TreeMap<>();
        mesyFin.put(1,31);
        //if given year is a leap-year, the value in February will be 29 instead of 28
        if ((yearGiven % 100 == 0) && (yearGiven % 400 == 0))
            mesyFin.put(2,29);
        else if(yearGiven % 4 == 0)
            mesyFin.put(2,29);
        else
            mesyFin.put(2,28);

        mesyFin.put(3,31);
        mesyFin.put(4,30);
        mesyFin.put(5,31);
        mesyFin.put(6,30);
        mesyFin.put(7,31);
        mesyFin.put(8,31);
        mesyFin.put(9,30);
        mesyFin.put(10,31);
        mesyFin.put(11,30);
        mesyFin.put(12,31);
        //if given month is valid and given day is equal or smaller than the last day, the user input is a valid date
        if(mesyFin.containsKey(monthGiven)){
            int finDeMes = mesyFin.get(monthGiven);
            if (dayGiven <= finDeMes)
                return true;
        }
        return false;
    }
}