package com.fetin.securityapp.model.Dao;

import com.fetin.securityapp.model.Celular;
import com.fetin.securityapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CelularDAO {

    private DatabaseReference referenciaDoBanco;
    public static Celular Cel_cadastrado;

    public static ArrayList<Usuario> lista_de_roubo;
    public static ArrayList<String> lista_de_imei;


    public CelularDAO()
    {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
        lista_de_imei = new ArrayList<>();
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

                    Celular celular_roubado = d.child("001").getValue(Celular.class);

                    busca_usuario.setCelularP(celular_roubado);

                    // pegando a chave única do FireBase
                    busca_usuario.setChave(d.getKey());

                    // Armazenando os usuários encontrados no banco em uma lista
                    lista_de_roubo.add(busca_usuario);
                    lista_de_imei.add(busca_usuario.getCelularP().getImei1());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void inserir(Celular novo_celular)
    {

        UsuarioDAO.user_cadastrado.setChave(UsuarioDAO.dao.buscaUmUsuarioEspecificoERetornaASuaChaveDoFireBase(UsuarioDAO.user_cadastrado.getEmail()));
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario").child(UsuarioDAO.user_cadastrado.getChave());

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
        referenciaUsuario.child("001").setValue(novo_celular);
    }

    public void buscarCelular() {

    }

    public void inserirRoubado(){

        //Cel_cadastrado = new Celular();

        DatabaseReference referenciaCelular = referenciaDoBanco.child("Celulares Roubados");

        referenciaCelular.child(UsuarioDAO.user_cadastrado.getChave()).setValue(UsuarioDAO.user_cadastrado);


        referenciaCelular.child(UsuarioDAO.user_cadastrado.getChave()).child("001").setValue(Cel_cadastrado);

    }

    public void excluir()
    {


    }

}

