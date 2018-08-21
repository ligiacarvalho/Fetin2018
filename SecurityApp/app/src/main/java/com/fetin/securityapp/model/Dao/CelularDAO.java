package com.fetin.securityapp.model.Dao;

import com.fetin.securityapp.model.Celular;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CelularDAO {

    private DatabaseReference referenciaDoBanco;

    public CelularDAO()
    {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
    }

    public void inserir(Celular novo_celular)
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Celular");

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
        referenciaUsuario.child(novo_celular.getImei1()).setValue(novo_celular);
    }

    public void excluir()
    {
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Celular");

        referenciaUsuario.child("1").removeValue();

    }

}

