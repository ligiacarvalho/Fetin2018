package com.fetin.securityapp.control.Cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.LoginActivity;
import com.fetin.securityapp.control.Tutorial.Tutorial2Activity;
import com.fetin.securityapp.control.Tutorial.TutorialActivity;
import com.fetin.securityapp.model.Celular;
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.fetin.securityapp.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.ArrayList;

public class Cadastro2Activity extends AppCompatActivity {

    EditText campoModeloCelular;
    RadioButton campoNumeroChip1;
    RadioButton campoNumeroChip2;
    EditText numeroDoChip1;
    EditText numeroDoChip2;
    EditText IMEI1;
    EditText IMEI2;


    public static Celular cel_cadastrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);
        referenciaComponentes();
        MascaraChip1();
        MascaraChip2();
        MascaraIMEI();

        UsuarioDAO.getInstance();

        //deixar Edit text invisivel
        campoNumeroChip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campoNumeroChip1.isChecked()) {

                    IMEI2.setVisibility(View.GONE);
                    numeroDoChip2.setVisibility(View.GONE);
                }
            }

        });

        //deixar Edit text visivel
        campoNumeroChip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campoNumeroChip2.isChecked()) {

                    IMEI2.setVisibility(View.VISIBLE);
                    numeroDoChip2.setVisibility(View.VISIBLE);
                }
            }

        });

        metodoParaTeste();

    }

    public void referenciaComponentes() {

        // Referenciando os componentes da interface
        campoModeloCelular = findViewById(R.id.CampoModeloCelular);
        campoNumeroChip1 = findViewById(R.id.radioButtonNumChip1);
        campoNumeroChip2 = findViewById(R.id.radioButtonNumChip2);
        numeroDoChip1 = findViewById(R.id.EditTextNumChip1);
        numeroDoChip2 = findViewById(R.id.EditTextNumChip2);
        IMEI1 = findViewById(R.id.campoIMEI1);
        IMEI2 = findViewById(R.id.campoIMEI2);

        // iniciando alguns componentes invisiveis
        IMEI2.setVisibility(View.GONE);
        numeroDoChip2.setVisibility(View.GONE);
    }

    public void metodoParaTeste() {
        campoModeloCelular.setText("123");
        numeroDoChip1.setText("123");
        numeroDoChip2.setText("123");
        IMEI1.setText("123123");
        IMEI2.setText("123123");

    }

    public boolean verificaEntradaDeDados()

    {
        for (int i = 0; i < CelularDAO.lista_de_imei.size(); i++) {
            if (IMEI1.getText().toString().equals(CelularDAO.lista_de_imei.get(i)) || IMEI2.getText().toString().equals(CelularDAO.lista_de_imei.get(i))) {
                Toast.makeText(getApplicationContext(), "Este IMEI já esta cadastrado", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (campoModeloCelular.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Campo sem dados!", Toast.LENGTH_LONG).show();
            return false;
        }


        if (campoNumeroChip1.isChecked()) {

            if (numeroDoChip1.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Campo sem dados!", Toast.LENGTH_LONG).show();
                return false;
            }

            if (IMEI1.getText().toString().equals("") || IMEI1.getText().toString().length() != 15) {
                Toast.makeText(getApplicationContext(), "O campo IMEI deve conter 15 caracteres!", Toast.LENGTH_LONG).show();
                return false;
            }

        } else if (campoNumeroChip2.isChecked()) {

            if (numeroDoChip2.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Campo sem dados!", Toast.LENGTH_LONG).show();
                return false;
            }

            if (IMEI2.getText().toString().equals("") || IMEI2.getText().toString().length() != 15) {
                Toast.makeText(getApplicationContext(), "O campo IMEI deve conter 15 caracteres!", Toast.LENGTH_LONG).show();
                return false;
            }

            if (numeroDoChip1.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Campo sem dados!", Toast.LENGTH_LONG).show();
                return false;
            }

            if (IMEI1.getText().toString().equals("") || IMEI1.getText().toString().length() != 15) {
                Toast.makeText(getApplicationContext(), "Campo sem dados ou dados insuficientes!", Toast.LENGTH_LONG).show();
                return false;
            }

        }

        return true;
    }

    //finalizar cadastro total
    public void finalizarCadastro(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        boolean resp = verificaEntradaDeDados();


        if(resp){
            getDadosDosCampos2();
            finish();
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Cadastro feito com sucesso!",Toast.LENGTH_LONG).show();
        }

    }

    //voltar para cadastro pessoal
    public void voltarCadastro(View view) {
        Intent intent = new Intent(this, Cadastro1Activity.class);
        finish();
        startActivity(intent);
    }

    public void getDadosDosCampos2() {
        // Referenciando os componentes da interface
        EditText campoModeloCelular = findViewById(R.id.CampoModeloCelular);
        RadioButton campoNumeroChip1 = findViewById(R.id.radioButtonNumChip1);
        RadioButton campoNumeroChip2 = findViewById(R.id.radioButtonNumChip2);
        EditText numeroDoChip1 = findViewById(R.id.EditTextNumChip1);
        EditText numeroDoChip2 = findViewById(R.id.EditTextNumChip2);
        EditText campoIMEI1 = findViewById(R.id.campoIMEI1);
        EditText campoIMEI2 = findViewById(R.id.campoIMEI2);

        //instanciando o celular
        Celular novoCelular = new Celular();

        novoCelular.setModelo(campoModeloCelular.getText().toString());
        novoCelular.setChip1(campoNumeroChip1.getText().toString());
        novoCelular.setChip2(campoNumeroChip2.getText().toString());
        novoCelular.setImei1(campoIMEI1.getText().toString());
        novoCelular.setImei2(campoIMEI2.getText().toString());

        CelularDAO daoC = new CelularDAO();

        CelularDAO.Cel_cadastrado = novoCelular;

        UsuarioDAO.user_cadastrado.setCelularP(novoCelular);

        UsuarioDAO.dao.inserir(UsuarioDAO.user_cadastrado);



    }

  
    //criando mascaras
    public void MascaraChip2() {
        // Referenciando o número o campo do número do CHIP 1, para pode manipular os dados de lá
        EditText numeroDoChipDois = findViewById(R.id.EditTextNumChip2);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(numeroDoChipDois, mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroDoChipDois.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoTelefone);


    }

    public void MascaraChip1() {
        // Referenciando o número o campo do número do CHIP 1, para pode manipular os dados de lá
        EditText numeroDoChipUm = findViewById(R.id.EditTextNumChip1);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(numeroDoChipUm, mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroDoChipUm.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoTelefone);
    }

    public void MascaraIMEI() {

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("NNNNNNNNNNNNNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(IMEI1, mascaraSimplesDeTelefone);
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone2 = new MaskTextWatcher(IMEI2, mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        IMEI1.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoTelefone);
        IMEI2.addTextChangedListener(objetoQueVaiGerenciarOFormatoDoTelefone2);
    }
}
