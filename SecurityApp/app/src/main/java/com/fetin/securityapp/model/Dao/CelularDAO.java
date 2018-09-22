package com.fetin.securityapp.model.Dao;

import android.media.MediaPlayer;
import android.util.Log;

import com.fetin.securityapp.R;
import com.fetin.securityapp.model.Celular;
import com.fetin.securityapp.model.FunctionalityManager;
import com.fetin.securityapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CelularDAO {

    private DatabaseReference referenciaDoBanco;
    public static Celular Cel_cadastrado;

    public static ArrayList<Usuario> lista_de_roubo;
    public static ArrayList<String> lista_de_imei;
    private Random random;


    public CelularDAO() {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
        lista_de_imei = new ArrayList<>();
        //lista todos os celulare roubados
        lista_de_roubo = new ArrayList<>();
    }

    public void buscarCelularesRoubado() {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference celularReferencia = referenciaDoBanco.child("Celulares Roubados");


        celularReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Usuario busca_usuario = (Usuario) d.getValue(Usuario.class);

                    Celular celular_roubado = d.child("celularP").getValue(Celular.class);

                    busca_usuario.setCelularP(celular_roubado);

                    // pegando a chave única do FireBase
                    busca_usuario.setChave(d.getKey());

                    // Armazenando os usuários encontrados no banco em uma lista
                    lista_de_roubo.add(busca_usuario);

                    if (celular_roubado != null && busca_usuario != null)
                        lista_de_imei.add(busca_usuario.getCelularP().getImei1());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void inserir(Celular novo_celular) {


        UsuarioDAO.user_cadastrado.setChave(UsuarioDAO.dao.buscaUmUsuarioEspecificoERetornaASuaChaveDoFireBase(UsuarioDAO.user_cadastrado.getEmail()));



        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario").child(UsuarioDAO.user_cadastrado.getChave());

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
        referenciaUsuario.child("celularP").setValue(novo_celular);
    }


    public void inserirRoubado(double latitude, double longitude) {


        DatabaseReference referenciaCelular = referenciaDoBanco.child("Celulares Roubados");

        if(UsuarioDAO.user_cadastrado.getCelularP() == null)
        {
            return;
        }
         UsuarioDAO.user_cadastrado.getCelularP().setCoordenadaLong(longitude);
         UsuarioDAO.user_cadastrado.getCelularP().setCoordenadaLat(latitude);

         Date data = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH); //ATUALIZAR
        int year = cal.get(Calendar.YEAR);
        //dia = Calendar.getInstance().getTime().getDay();

        UsuarioDAO.user_cadastrado.getCelularP().setDia(day);
        UsuarioDAO.user_cadastrado.getCelularP().setMes(month);
        UsuarioDAO.user_cadastrado.getCelularP().setAno(year);

        //Criando código para o contato pré-dfinido e incluido no banco de dados
        random = new Random();
        int valor = random.nextInt(60000);
        UsuarioDAO.user_cadastrado.getCelularP().setCodigo(valor);

        //Passando número do contato próximo para ativar a função de segurança
        ativarFuncionalidadesDeSeguranca(UsuarioDAO.user_cadastrado.getContatoProximo());


        //comando para inserir no banco
        referenciaCelular.child(UsuarioDAO.user_cadastrado.getChave()).setValue(UsuarioDAO.user_cadastrado);

    }

    public void ativarFuncionalidadesDeSeguranca(String contato_proximo)
    {

        FunctionalityManager sms = new FunctionalityManager();
        sms.sendSms(contato_proximo,UsuarioDAO.user_cadastrado.getCelularP().getCodigo());

    }



}

