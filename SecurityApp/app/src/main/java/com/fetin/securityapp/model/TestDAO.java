package com.fetin.securityapp.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TestDAO {

    // Objeto para gerenciar o banco de dados
    private DatabaseReference referenciaDoBanco;
    // Objeto para gerenciar os usuários
    private FirebaseAuth usuario;

    public TestDAO(){

        /*
            1 - Pegando a referencia do nó raiz da arvore gerada pelo firebase
            obs: caso quisesse mudar a referencia, só colocar .getReference("nomeDoNóDeReferencia");
        */
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();

        /*
            1 - Pegando a referencia do objeto que permite manipular os usuários
        */
        usuario = FirebaseAuth.getInstance();

    }

    public boolean inserirUsuario(Usuario novoUsuario)
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("usuarios");

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
        referenciaUsuario.push().setValue(novoUsuario);

        return true;
    }

    public void recuperarUsuarios()
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("usuarios");

        referenciaUsuario.addValueEventListener(new ValueEventListener() {

            // método é sempre chamando quando um dado é alterado no nó "usuários"
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE",dataSnapshot.getValue().toString());
            }
            // caso alguma alteração seja cancelada, o método abaixo é executado
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean verificaSeOUsuarioEstaLogado()
    {
        /*

        */

        if( usuario.getCurrentUser() != null)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public Usuario pesquisaUsuarioPorNome(String nome)
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("usuarios");

        // Ordenandos os usuários pelo atributo "nome", e depois fazendo a pesquisa por nome
        Query usuarioEncontrado = referenciaUsuario.orderByChild("nome").equalTo(nome);

        // Criando um objeto para armazenar o usuario encontrado na busca
        final Usuario[] dadosUsuario = {null};

        usuarioEncontrado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // pegando os dados do usuário encontrado
                dadosUsuario[0] = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return dadosUsuario[0];
    }

    /*

        Getters And Setters

    */

    public DatabaseReference getReferenciaDoBanco() { return referenciaDoBanco;  }

    public void setReferenciaDoBanco(DatabaseReference referenciaDoBanco) {
        this.referenciaDoBanco = referenciaDoBanco;
    }

    public FirebaseAuth getUsuario() {  return usuario;  }

    public void setUsuario(FirebaseAuth usuario) {
        this.usuario = usuario;
    }
}
