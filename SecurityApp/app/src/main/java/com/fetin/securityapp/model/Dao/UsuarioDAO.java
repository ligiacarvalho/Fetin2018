package com.fetin.securityapp.model.Dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fetin.securityapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsuarioDAO {

    private DatabaseReference referenciaDoBanco;
    private static UsuarioDAO dao;

    public UsuarioDAO()
    {
        referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
    }

    public static synchronized UsuarioDAO getInstance()
    {
        if(dao == null)
        {
            dao = new UsuarioDAO();
        }

        return dao;
    }

    public void inserir(Usuario novo_usuario)
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario");

        // Adicionando um nó filho ao "usuários", com chave única gerada randomicamente
        // .E nela, colocando os dados dos novos usuários.
       // referenciaUsuario.child(novo_usuario.getEmail()).setValue(novo_usuario);
    }

    public void buscarUsuarios()
    {
        // Pegando a referencia do nó "usuários"
        DatabaseReference referenciaUsuario = referenciaDoBanco.child("Usuario");

        final ArrayList<String> lista_de_chaves = new ArrayList<>();

        referenciaUsuario.addValueEventListener(new ValueEventListener() {

            // método é sempre chamando quando um dado é alterado no nó "usuários"
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("Teste", String.valueOf(dataSnapshot.getChildrenCount()));


                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    //Usuario user = d.getValue(Usuario.class);
                    Log.i("Teste", d.getValue(Usuario.class).getNome());
                    lista_de_chaves.add(d.getRef().getKey());

                }
            }
            // caso alguma alteração seja cancelada, o método abaixo é executado
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for(String chave : lista_de_chaves)
        {
          // Usuario user = referenciaUsuario.child(chave).getValue(Usuario.class);

        }

    }

    public void excluirUsuarioAutenticado()
    {

        // excluindo da autenticação
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // se der certo, faz algo
                        }
                    }
                });

    }

    public void atualizar()
    {

    }

}
