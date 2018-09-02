package com.fetin.securityapp.control;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Menu.Fragment.TemplateColor;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

// Activity para travar a Tela do celular
public class TestActivity extends AppCompatActivity{

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Grafico
        pieChart = (PieChart) findViewById(R.id.graficoID);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10,5,5);

        //O quanto desliza a roda quando deslizamos ela. Maior o valor, mais desliza
        pieChart.setDragDecelerationFrictionCoef(0.95f);

       /*
        pieChart.setCenterText("4Security");
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.BLACK);
        /*/

        pieChart.setHoleColor(android.graphics.Color.WHITE);
        //preenche a roda = false/roda vazada = true
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(60f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(18.5f, "Hoje"));
        yValues.add(new PieEntry(26f, "Mês Passado"));
        yValues.add(new PieEntry(24.0f, "Mês Retrasado"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Celulares Roubados");

        //espaço entre os pedaços
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(TemplateColor.NIC_COLORS);

        dataSet.setValueFormatter(new PercentFormatter());
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);



    }

}
