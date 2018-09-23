package com.fetin.securityapp.model.Dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fetin.securityapp.model.Celular;
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

    public static DatabaseReference referenciaDoBanco, referenciaDoUsuario;
    public static UsuarioDAO dao;
    public static ArrayList<Usuario> lista_de_usuarios;
    public static Usuario user_cadastrado;

    private UsuarioDAO() {

    }

    public static synchronized void getInstance() {
        if (dao == null) {
            dao = new UsuarioDAO();
            lista_de_usuarios = new ArrayList<>();
            // Pegando a referencia do nó "Pai" do FireBase
            referenciaDoBanco = FirebaseDatabase.getInstance().getReference();
            // Pegando a referencia do nó "Usuário" do FireBase
            referenciaDoUsuario = referenciaDoBanco.child("Usuario");
            user_cadastrado = new Usuario();

        }

    }

    public void inserir(Usuario novo_usuario) {

        // Adicionando um nó filho ao "Usuário", com chave única gerada randomicamente pelo push()
        // .E nela, colocando os dados dos novos usuários.
        referenciaDoUsuario.push().setValue(novo_usuario);
    }

    public void buscarUsuarios() {
        // Pegando a referencia do nó "usuários"
        referenciaDoUsuario = referenciaDoBanco.child("Usuario");

        referenciaDoUsuario.addValueEventListener(new ValueEventListener() {

            // método é sempre chamando quando um dado é alterado no nó "usuários"
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // limpar a lista antes de preenche-la novamente
                lista_de_usuarios.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    // pegando cada usuário nos nós da árvore, filhos do nó "Usuário", e armazenando 1 por 1
                    // na variável novo_usuario em cada iteração do for
                    Usuario novo_usuario = (Usuario) d.getValue(Usuario.class);

                    Celular celular = (Celular) d.child("celularP").getValue(Celular.class);

                    novo_usuario.setCelularP(celular);

                    // pegando a chave única do FireBase
                    novo_usuario.setChave(d.getKey());

                    // Armazenando os usuários encontrados no banco em uma lista
                    lista_de_usuarios.add(novo_usuario);

                }
            }

            // caso alguma alteração seja cancelada, o método abaixo é executado
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static String buscaUmUsuarioEspecificoERetornaASuaChaveDoFireBase(String email) {
        // percorrendo cada usuário da lista de usuários
        for (Usuario user : lista_de_usuarios) {
            // se o email do usuário da posição X, for igual ao email que foi passado para a função
            if (user.getEmail().equalsIgnoreCase(email)) {
                // retorno a chave dele
                return user.getChave();
            }
        }

        // se não encontrou anda, retorna null
        return null;
    }

    public boolean excluirUsuarioAutenticado() {

        boolean sucesso = false;

        // Pegando o usuário que fez o "Sign In"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Usando o método delete para deleta-lo da aunteticação
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // se der certo, faz algo
                        }
                    }
                });

        // Excluindo o usuário da DataBase do FireBase agora

        // Pegando a chave do usuário a ser deletado
        String chaveDoUsuarioASerDeletado = buscaUmUsuarioEspecificoERetornaASuaChaveDoFireBase(user.getEmail());

        if (chaveDoUsuarioASerDeletado != null) {
            // remove o nó do Usuario, com a chave encontrada pela função acima
            referenciaDoUsuario.child(chaveDoUsuarioASerDeletado).removeValue();

            sucesso = true;
        } else {
            sucesso = false;
        }

        return sucesso;
    }

    public boolean deslogarUsuario() {
        boolean sucesso = false;

        // Pegando o usuário que fez o "Sign In"
        FirebaseAuth fireAuth = FirebaseAuth.getInstance();
        fireAuth.signOut();

        sucesso = true;

        return sucesso;

    }

    public void deletarUsuarios() {


      /*  ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                System.out.println("User: " + user.getUid());
            }
            page = page.getNextPage();
        }

        page = FirebaseAuth.getInstance().listUsers(null);
        for (ExportedUserRecord user : page.iterateAll()) {
            System.out.println("User: " + user.getUid());
        }

        FirebaseAuth.getInstance().deleteUser(uid);*/
    }

}
