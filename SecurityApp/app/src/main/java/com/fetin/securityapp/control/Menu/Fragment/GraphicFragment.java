package com.fetin.securityapp.control.Menu.Fragment;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Menu.MenuActivity;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GraphicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GraphicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphicFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GraphicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GraphicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraphicFragment newInstance(String param1, String param2) {
        GraphicFragment fragment = new GraphicFragment();
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

       View v = inflater.inflate(R.layout.fragment_graphic, container, false);

       //Grafico
        PieChart pieChart;


        pieChart = (PieChart) v.findViewById(R.id.graficoID);
        TextView textoHoje = v.findViewById(R.id.textView_ValorHoje);
        TextView textoSemana = v.findViewById(R.id.textView_ValorSemana);
        TextView textoMes = v.findViewById(R.id.textView_ValorMes);


        int dia = MenuActivity.contDia;
        int semana = MenuActivity.contSemana + dia;
        int mes = MenuActivity.contMes;

        textoHoje.setText(Integer.toString(dia));
        textoSemana.setText(Integer.toString(semana));
        textoMes.setText(Integer.toString(mes));

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10,5,5);

        //O quanto desliza a roda quando deslizamos ela. Maior o valor, mais desliza
        pieChart.setDragDecelerationFrictionCoef(0.95f);


        pieChart.setHoleColor(android.graphics.Color.WHITE);
        //preenche a roda = false/roda vazada = true
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(60f);

       // String agosto = Integer.toString(MenuActivity.contAgosto);

        ArrayList<PieEntry> yValues = new ArrayList<>();


        if(MenuActivity.contJaneiro!=0){ yValues.add(new PieEntry(MenuActivity.contJaneiro, "Janeiro"));}
        if(MenuActivity.contFevereiro!=0){ yValues.add(new PieEntry(MenuActivity.contFevereiro, "Fevereiro"));}
        if(MenuActivity.contMarco!=0){  yValues.add(new PieEntry(MenuActivity.contMarco, "Março"));}
        if(MenuActivity.contAbril!=0){yValues.add(new PieEntry(MenuActivity.contAbril, "Abril"));}
        if(MenuActivity.contMaio!=0){yValues.add(new PieEntry(MenuActivity.contMaio, "Maio"));}
        if(MenuActivity.contJunho!=0){ yValues.add(new PieEntry(MenuActivity.contJunho, "Junho"));}
        if(MenuActivity.contJulho!=0){ yValues.add(new PieEntry(MenuActivity.contJulho, "Julho")); }
        if(MenuActivity.contAgosto!=0){ yValues.add(new PieEntry(MenuActivity.contAgosto, "Agosto"));}
        if(MenuActivity.contSetembro!=0){ yValues.add(new PieEntry(MenuActivity.contSetembro, "Setembro"));}
        if(MenuActivity.contOutubro!=0){yValues.add(new PieEntry(MenuActivity.contOutubro, "Outubro"));}
        if(MenuActivity.contNovembro!=0){yValues.add(new PieEntry(MenuActivity.contNovembro, "Novembro"));}
        if(MenuActivity.contDezembro!=0){yValues.add(new PieEntry(MenuActivity.contDezembro, "Dezembro"));}


        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");

        //espaço entre os pedaços
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(TemplateColor.NIC_COLORS);

        dataSet.setValueFormatter(new PercentFormatter());
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
