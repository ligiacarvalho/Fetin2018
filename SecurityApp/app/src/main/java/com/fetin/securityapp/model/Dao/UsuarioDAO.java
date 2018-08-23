package com.fetin.securityapp.model.Dao;

import com.fetin.securityapp.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioDAO {

    private DatabaseReference referenciaDoBanco;

    public UsuarioDAO()
    {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
    }


    public void inserir(Usuario novo_usuario)
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario");

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
       // referenciaUsuario.child(novo_usuario.getEmail()).setValue(novo_usuario);
    }

    public void buscar()
    {

    }

    public void excluir()
    {
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario");

        referenciaUsuario.child("1").removeValue();

    }

    public void atualizar()
    {

    }

}
