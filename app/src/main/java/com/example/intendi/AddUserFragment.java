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
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                createUser.setEnabled(false);
                boolean isDateValid = isDateValid(dia.getText().toString(), mes.getText().toString(), anio.getText().toString());
                int lengthName = name.getText().toString().length();
                if (lengthName > 0){
                    if (isDateValid){
                        User newUser = new User(0,"0", R.drawable.delphi, "00/00/00");
                        boolean flagIsAdded = true;
                        if (dbHandler.getUsersCount() < 6) {
                            String dateOfBirth = dia.getText() + "/" + mes.getText() + "/" + mes.getText();
                            newUser = dbHandler.addUser(name.getText().toString(), dateOfBirth, imageSrc);
                            msgAdd.setTextColor(getResources().getColor(R.color.marinoIntendi));
                            msgAdd.setText("¡" + name.getText().toString()+" se une al juego!");

                        }else {
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
                                createUser.setEnabled(true);
                            }
                        }, 500);
                    }else {
                        msgAdd.setTextColor(getResources().getColor(R.color.red_eat));
                        msgAdd.setText("Introduce una fecha válida");
                    }
                }else {
                    msgAdd.setTextColor(getResources().getColor(R.color.red_eat));
                    msgAdd.setText("Introduce un nombre de usuario");
                }
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
    public boolean isDateValid(String diaDado, String mesDado, String anioDado){
        int dayGiven,monthGiven, yearGiven;
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
        System.out.println(dayGiven);
        System.out.println(monthGiven);
        System.out.println(yearGiven);
        if (dayGiven <= 0 || dayGiven > 31)
            return false;
        if (monthGiven <= 0 || monthGiven > 12)
            return false;
        Date thisYear = new Date();
        SimpleDateFormat yf = new SimpleDateFormat("yyyy");
        String year = yf.format(thisYear);
        if (yearGiven >= Integer.parseInt(year))
            return false;
        Map<Integer, Integer> mesyFin = new TreeMap<>();
        mesyFin.put(1,31);

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
        if(mesyFin.containsKey(monthGiven)){
            int finDeMes = mesyFin.get(monthGiven);
            if (dayGiven <= finDeMes)
                return true;
        }
        return false;
    }
}