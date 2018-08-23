package com.fetin.securityapp.control.Menu.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fetin.securityapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CelularFragment extends Fragment {


    private ListView listaItens;
    private String[] itens = {
            "IMEI_1", "IMEI_2", "IMEI_3",
            "IMEI_4", "IMEI_5", "IMEI_6"
    };
    private String[] Algoquevaiserescritodentro = {
            "IMEI_1", "IMEI_2", "IMEI_3",
            "IMEI_4", "IMEI_5", "IMEI_6"
    };

    public CelularFragment() {
        // Required empty public constructor
    }
    private AlertDialog alerta;

    private void exemplo_simples(int codigoPosicao) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //define o titulo
        builder.setTitle(Algoquevaiserescritodentro[codigoPosicao]);
        //define a mensagem
        builder.setMessage("Dono: Fulano\n" +
                "Modelo do celular: Modelo1\n"+
                "CPF do dono: 000000\n"+
                "Contato adiciondo: 000000\n"+
                "Descartar informações? (ou seja, o celular foi encontrado e devolvido)\n");
        //builder.setMessage("Modelo do celular: Modelo1");
        //builder.setMessage("CPF do dono: 000000");
        //builder.setMessage("Contato adiciondo: 000000");
        //define um botão como positivo
        //builder.setMessage("Descartar informações? (ou seja, o celular foi encontrado e devolvido)");
        builder.setPositiveButton("Positivo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(MainActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // CÓDIGO AQUI REBECA
        View view = inflater.inflate(R.layout.fragment_celular, container, false);

        listaItens = view.findViewById(R.id.listViewId);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        listaItens.setAdapter(adapter);
        listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                  int codigoPosicao = position;
                                                  //String valorClicado = listaItens.getItemAtPosition(codigoPosicao).toString();
                                                  // Toast.makeText(getActivity(), Algoquevaiserescritodentro[codigoPosicao], Toast.LENGTH_SHORT).show();
                                                  //buscar o imei clicado "valor da posição" acessar os dados daquele imei e abir uma nova pagina com as informaçõs daquele imei.
                                                  //abrir nova tela e passar o imei como referencia e dentro dessa nova tela pegar as informações e mostrar.
                                                  exemplo_simples(codigoPosicao);
                                              }
                                          }

        );


        return view;
    }

    // MÉTODO AQUI REBECA



}
