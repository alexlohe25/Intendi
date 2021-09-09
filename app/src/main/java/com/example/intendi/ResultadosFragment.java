package com.example.intendi;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    HorizontalBarChart miChart;
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

        miChart = (HorizontalBarChart) view.findViewById(R.id.userResults);
        Description chartDesc = new Description();
        chartDesc.setText("Puntaje en Whack-A-Ball");
        miChart.setDescription(chartDesc);
        //miChart.setExtraOffsets(5,10,5,5);

        ArrayList<String> misValoresX = new ArrayList<>();
        //misValoresX.add("01/09/21");
        //misValoresX.add("02/09/21");
        //misValoresX.add("03/09/21");
        ArrayList<BarEntry> misValoresY = new ArrayList<>();
        misValoresY.add(new BarEntry(0.25f,10));
        misValoresY.add(new BarEntry(1.25f,20));
        misValoresY.add(new BarEntry(2.25f,15));
        BarDataSet dataBar = new BarDataSet(misValoresY, "Score");
        dataBar.setColor(Color.parseColor("#2A9D8F"));
        BarData data = new BarData((dataBar));
        data.setBarWidth(0.6f);
        miChart.getXAxis().setAvoidFirstLastClipping(false);
        miChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(misValoresX));
        miChart.getXAxis().setCenterAxisLabels(true);
        miChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        miChart.animateXY(750,750);
        miChart.setFitBars(true);
        miChart.setPinchZoom(false);
        miChart.setScaleEnabled(false);
        miChart.setData(data);
        miChart.invalidate();
        return view;
    }
}