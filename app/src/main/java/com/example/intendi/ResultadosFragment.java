package com.example.intendi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Typeface tf;
    LineChart miChart;
    CardView cardWhack, cardMem, cardNums, cardLaber, cardPiano;
    TextView resultDetail;
    User currentUser;
    DBHandler dbHandler;

    public ResultadosFragment() {
        // Required empty public constructor
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static ResultadosFragment newInstance(String param1, String param2) {
        ResultadosFragment fragment = new ResultadosFragment();
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
        View view =  inflater.inflate(R.layout.fragment_resultados, container, false);
        cardWhack = view.findViewById(R.id.cardWhack);
        cardMem = view.findViewById(R.id.cardMemorama);
        cardNums = view.findViewById(R.id.cardSenda);
        cardLaber = view.findViewById(R.id.cardLaberintendi);
        cardPiano = view.findViewById(R.id.cardPiano);
        resultDetail = (TextView) view.findViewById(R.id.tendenciaResults);
        miChart = (LineChart) view.findViewById(R.id.idChart);
        tf = ResourcesCompat.getFont(getActivity(), R.font.nunito_semibold);
        generaChart("Whack-A-Ball", view);

        cardWhack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(miChart.getDescription().getText() != "Puntaje en Whack-A-Ball"){
                    generaChart("Whack-A-Ball", view);
                }

            }
        });
        cardMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(miChart.getDescription().getText() != "Puntaje en Memorama"){
                    generaChart("Memorama", view);
                }
            }
        });
        cardNums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(miChart.getDescription().getText() != "Puntaje en Senda numérica"){
                    generaChart("Senda numérica", view);
                }
            }
        });
        cardLaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(miChart.getDescription().getText() != "Puntaje en Laberintendi"){
                    generaChart("Laberintendi", view);
                }
            }
        });
        cardPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(miChart.getDescription().getText() != "Puntaje en Piano"){
                    generaChart("Piano", view);
                }
            }
        });

        return view;
    }
    public void generaChart(String game, View view){
        ArrayList<String> misValoresX = obtenValoresEnX();
        ArrayList<Entry> misValoresY = obtenValoresEnY();
        int YArraySize = misValoresY.size();

        LineDataSet set1 = new LineDataSet(misValoresY, "Score del juego");
        if( YArraySize == 0){
            resultDetail.setText("Aún no has jugado a "+ game);
        }else{
            if(misValoresY.get(YArraySize - 1).getY() >= misValoresY.get(YArraySize - 2).getY()){
                resultDetail.setText("¡Juegas increíble al Intendi!");
            }else{
                resultDetail.setText("¡No te desanimes "+ currentUser.getUsername() +"! La próxima vez será mejor ");
            }
        }
        set1.setDrawCircles(true); //para que los puntos esten llenos y bonitos
        set1.setDrawCircleHole(false);
        set1.setLineWidth(5f);
        set1.setCircleRadius(12f);
        set1.setValueTextSize(12f);
        set1.setCircleColor(Color.parseColor("#023047"));
        set1.setColor(Color.parseColor("#023047"));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>(); // metes el set de valores en Y en otro array
        dataSets.add(set1);

        LineData data = new LineData(dataSets); // incias la data del grafico con el arreglo que llenaste un paso antes
        data.setValueTypeface(tf);
        data.setValueTextColor(Color.parseColor("#FB8500"));
        data.setValueFormatter(new ValueFormatter() {
            public String getFormattedValue(float value) {
                return "" + ((int) value);
            }
        });

        Description chartDesc = new Description();
        chartDesc.setText("Puntaje en " + game );
        chartDesc.setTypeface(tf);
        miChart.setDescription(chartDesc);
        miChart.getAxisLeft().setDrawGridLines(false);
        miChart.getXAxis().setDrawGridLines(false);
        miChart.setPinchZoom(false);//para que no se mueva la grafica (drag ni zoom)
        miChart.setScaleEnabled(false);
        miChart.setTouchEnabled(false);
        miChart.animateY(500);//animar la entrada
        miChart.getAxisLeft().setTypeface(tf);

        XAxis xAxis = miChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setAxisMinimum(0);//seteas el rango en X que debe ser de 0 a listaValoresX().size
        xAxis.setAxisMaximum(YArraySize);
        xAxis.setGranularity(1f); //granuralidad de el eje X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setLabelRotationAngle(45);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() { //vaciar los datos del arreglo de valores en X al label del eje X
            @Override //si, un override
            public String getAxisLabel(float value, AxisBase axis){
                if(value >= 0 && value <= misValoresX.size() - 1)
                    return misValoresX.get((int) value);
                return "";
            }
        });
        miChart.getAxisRight().setEnabled(false); // que el label del eje Y se vea solo en la izquierda
        miChart.setData(data); //setea la data que guardaste de valores en Y al grafico
        miChart.invalidate();
    }
    ArrayList<String> obtenValoresEnX(){
        ArrayList<String> misValoresX = new ArrayList<>();
        misValoresX.add("01/09/21"); //empieza en i = 0
        misValoresX.add("02/09/21");
        misValoresX.add("03/09/21");
        misValoresX.add("04/09/21");
        misValoresX.add("04/09/21");
        misValoresX.add("04/09/21");// i = 5
        return misValoresX;
    }
    ArrayList<Entry> obtenValoresEnY(){
        ArrayList<Entry> misValoresY = new ArrayList<>();
        misValoresY.add(new Entry(0.5f,20)); //la posicion en x es i + 0.5 para el diseño
        misValoresY.add(new Entry(1.5f,15));
        misValoresY.add(new Entry(2.5f,18));
        misValoresY.add(new Entry(3.5f,17));
        misValoresY.add(new Entry(4.5f,21));
        misValoresY.add(new Entry(5.5f,19));
        return misValoresY;
    }
}
