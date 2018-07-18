package com.fetin.securityapp.control;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.model.Celular;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Cadastro2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);
        criandoMascara();
    }

    //finalizar cadastro total
    public void finalizarCadastro(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        getDadosDosCampos2();

    }
    //voltar para cadastro pessoal
    public void voltarCadastro(View view)
    {
        Intent intent = new Intent(this, Cadastro1Activity.class);
        startActivity(intent);
    }

    public void getDadosDosCampos2()
    {
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


        //    Toast.makeText(this,"Modelo cel = "+novoCelular.getModelo(),Toast.LENGTH_LONG).show();
        //    Toast.makeText(this,"Chip 1 = "+novoCelular.getChip1(),Toast.LENGTH_LONG).show();
        //    Toast.makeText(this,"Chip 2 = "+novoCelular.getChip2(),Toast.LENGTH_LONG).show();
        //    Toast.makeText(this,"IMEI1 "+novoCelular.getImei1(),Toast.LENGTH_LONG).show();
        //    Toast.makeText(this,"IMEI2 = "+novoCelular.getImei2(),Toast.LENGTH_LONG).show();



    }

    public void criandoMascara()
    {
        // Referenciando o número o campo do número do CHIP 1, para pode manipular os dados de lá
        EditText numeroDoChipUm = findViewById(R.id.EditTextNumChip1);

        // Passando qual é o padrão de telefone que eu vou querer que o usuário digite
        SimpleMaskFormatter mascaraSimplesDeTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");

        // Passando para o objeto que vai gerenciar o formato do telefone, qual é o editText e qual é o formato que queremos
        MaskTextWatcher objetoQueVaiGerenciarOFormatoDoTelefone = new MaskTextWatcher(numeroDoChipUm,mascaraSimplesDeTelefone);

        // Qualquer alteração de texto no EditText do número do Chip1, o método abaixo vai usar botar
        // o "objetoQueVaiGerenciarOFormatoDoTelefone" para trabalhar.
        numeroDoChipUm.addTextChangedListener( objetoQueVaiGerenciarOFormatoDoTelefone );


    }


}
