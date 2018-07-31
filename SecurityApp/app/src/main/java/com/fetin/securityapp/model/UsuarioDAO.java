package com.fetin.securityapp.model;

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
        referenciaUsuario.push().setValue(novo_usuario);
    }

    public void buscar()
    {

    }

    public void excluir()
    {

    }

    public void atualizar()
    {

    }

}
