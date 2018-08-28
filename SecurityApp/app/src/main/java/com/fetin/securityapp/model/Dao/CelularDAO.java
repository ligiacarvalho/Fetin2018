package com.fetin.securityapp.model.Dao;

import com.fetin.securityapp.model.Celular;
import com.fetin.securityapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CelularDAO {

    private DatabaseReference referenciaDoBanco;
    public static Celular Cel_cadastrado;


    public CelularDAO()
    {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
    }

    public void inserir(Celular novo_celular)
    {

        UsuarioDAO.user_cadastrado.setChave(UsuarioDAO.dao.buscaUmUsuarioEspecificoERetornaASuaChaveDoFireBase(UsuarioDAO.user_cadastrado.getEmail()));
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario").child(UsuarioDAO.user_cadastrado.getChave());

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
        referenciaUsuario.child(novo_celular.getImei1()).setValue(novo_celular);
    }

    public void buscarCelular() {

    }

    public void inserirRoubado(){

        //Cel_cadastrado = new Celular();

        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Celulares Roubados").child(UsuarioDAO.user_cadastrado.getChave());

        referenciaUsuario.setValue(UsuarioDAO.user_cadastrado);
        referenciaUsuario.child(Cel_cadastrado.getImei1()).setValue(Cel_cadastrado);

    }

    public void excluir()
    {


    }

}

