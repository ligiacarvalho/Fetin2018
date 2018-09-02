package com.fetin.securityapp.control.Menu.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Menu.MenuActivity;
import com.fetin.securityapp.model.Celular;
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.fetin.securityapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CelularFragment extends Fragment {

    private DatabaseReference referenciaDoBanco;
    public static Usuario user_cadastrado;
    public static Celular Cel_cadastrado;
    private ListView listaItens;


   /* private String[] itens = {

            "IMEI_1", "IMEI_2", "IMEI_3",
            "IMEI_4", "IMEI_5", "IMEI_6"
    };
    private String[] Algoquevaiserescritodentro = {
            "IMEI_1", "IMEI_2", "IMEI_3",
            "IMEI_4", "IMEI_5", "IMEI_6"
    };*/

    public CelularFragment() {
        // Required empty public constructor
    }

    private AlertDialog alerta;

    private void exemplo_simples(int codigoPosicao) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //define o titulo
        //builder.setTitle(Algoquevaiserescritodentro[codigoPosicao]);


        // saber quem ele clicou, pegar uma variavel comum lá, para fazer uma busca na lista abaixo
        // CelularDAO.lista_de_roubo
        // E retornar 1 celular pra vc a cessar os dados dele abaixo

        int dia, ano, mes;



        for (int i = 0; i < CelularDAO.lista_de_roubo.size(); i++) {

            //Log.i("LISTA", String.valueOf(i));
            if (i == codigoPosicao) {

                dia = CelularDAO.lista_de_roubo.get(i).getCelularP().getDia();
                mes = CelularDAO.lista_de_roubo.get(i).getCelularP().getMes();
                ano = CelularDAO.lista_de_roubo.get(i).getCelularP().getAno();

                builder.setMessage(
                        //define a mensagem



                        "Modelo do celular: " + CelularDAO.lista_de_roubo.get(i).getCelularP().getModelo() + "\n" +
                                "CPF do usuário: " + CelularDAO.lista_de_roubo.get(i).getCPF() + "\n" +
                                "Contato adicionado: " + CelularDAO.lista_de_roubo.get(i).getContatoProximo() + "\n" +
                                "email: " + CelularDAO.lista_de_roubo.get(i).getEmail() + "\n"+
                                "Data: "+dia+"/"+mes+"/"+ano+"\n"
                        // "Descartar informações? (ou seja, o celular foi encontrado e devolvido)\n"
                );
            }
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
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

        atualizaLista();

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

    public void atualizaLista()
    {

        if (MenuActivity.lista_temporaria.size() != 0) {
            ArrayAdapter adapter = new ArrayAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    MenuActivity.lista_temporaria
            );
            listaItens.setAdapter(adapter);

            //MenuActivity.lista_temporaria.clear();
        }
        else
        {
            ArrayAdapter adapter2 = new ArrayAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    CelularDAO.lista_de_imei

            );
            listaItens.setAdapter(adapter2);
        }
    }

    // MÉTODO AQUI REBECA


}
