package com.example.intendi;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    //HorizontalBarChart miChart;
    LineChart miChart;
    TextView resultDetail;
    public ResultadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadosFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_resultados, container, false);
        ArrayList<String> misValoresX = new ArrayList<>();
        misValoresX.add("01/09/21"); //empieza en i = 0
        misValoresX.add("02/09/21");
        misValoresX.add("03/09/21");
        misValoresX.add("04/09/21"); // i = 3
        ArrayList<Entry> misValoresY = new ArrayList<>();
        //misValoresY.add(new Entry(0f,10));
        misValoresY.add(new Entry(0.5f,20)); //la posicion en x es i + 0.5 para el diseño
        misValoresY.add(new Entry(1.5f,15));
        misValoresY.add(new Entry(2.5f,18));
        misValoresY.add(new Entry(3.5f,25));
        LineDataSet set1 = new LineDataSet(misValoresY, "Score del juego");
        set1.setDrawCircles(true); //para que los puntos esten llenos y bonitos
        set1.setDrawCircleHole(false);
        set1.setLineWidth(10f);
        set1.setCircleRadius(12f);
        set1.setValueTextSize(12f);
        set1.setCircleColor(Color.parseColor("#023047"));
        set1.setColor(Color.parseColor("#023047"));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>(); // metes el set de valores en Y en otro array
        dataSets.add(set1);
        LineData data = new LineData(dataSets); // incias la data del grafico con el arreglo que llenaste un paso antes

        miChart = (LineChart) view.findViewById(R.id.idChart);
        Description chartDesc = new Description();
        chartDesc.setText("Puntaje en Whack-A-Ball");
        miChart.setDescription(chartDesc);
        miChart.getAxisLeft().setDrawGridLines(false);
        miChart.getXAxis().setDrawGridLines(false);
        miChart.setPinchZoom(false);//para que no se mueva la grafica (drag ni zoom)
        miChart.setScaleEnabled(false);
        miChart.setTouchEnabled(true);
        miChart.animateY(500);//animar la entrada
        XAxis xAxis = miChart.getXAxis();
        xAxis.setAxisMinimum(0);//seteas el rango en X que debe ser de 0 a listaValoresX().size
        xAxis.setAxisMaximum(4);
        xAxis.setGranularity(1f); //granuralidad de el eje X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
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
        miChart.invalidate(); //actualiza el grafico
        return view;
    }
}