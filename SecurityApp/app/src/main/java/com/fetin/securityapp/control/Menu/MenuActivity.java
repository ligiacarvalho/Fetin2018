package com.fetin.securityapp.control.Menu;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Cadastro1Activity;
import com.fetin.securityapp.control.LoginActivity;
import com.fetin.securityapp.control.Menu.Fragment.CelularFragment;
import com.fetin.securityapp.control.Menu.Fragment.MapaFragment;
import com.fetin.securityapp.model.Celular;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        viewPager = findViewById(R.id.viewPager);
        smartTabLayout = findViewById(R.id.viewPagerTab);

        //Aplica configurações na Action Bar
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("SecurityApp");

        //Configurar abas
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Mapa",MapaFragment.class)
                        .add("Celulares", CelularFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menuSair:
                deslogarUsuario();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){
        try
        {
            //autenticacao.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }






}
