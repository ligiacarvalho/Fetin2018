package com.fetin.securityapp.control.Menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fetin.securityapp.R;
import com.fetin.securityapp.control.Cadastro.Cadastro1Activity;
import com.fetin.securityapp.control.LoginActivity;
import com.fetin.securityapp.control.Menu.Fragment.CelularFragment;
import com.fetin.securityapp.control.Menu.Fragment.MapaFragment;
import com.fetin.securityapp.control.Tutorial.TutorialActivity;
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.fetin.securityapp.model.Usuario;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;

public class MenuActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth autenticacao;
    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;
    private CelularFragment celFragment;
    private MapaFragment mapaFragment;
    private FirebaseAuth usuarioAuth;

    // Variáveis do Google Maps
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private LocationRequest locationRequest;
    private SupportMapFragment mapFragment;


    // Abas
    private Button abaCelular, abaMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("SecurityApp");

        setContentView(R.layout.activity_menu);

        referenciaComponentes();


        CelularDAO daoC = new CelularDAO();
        daoC.buscarCelularesRoubado();

        //autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //viewPager = findViewById(R.id.viewPager);
        //smartTabLayout = findViewById(R.id.viewPagerTab);

        //Aplica configurações na Action Bar, para remover a sombra


     /*   FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Mapa", MapaFragment.class)
                        .add("Celulares")
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);*/


        aplicaGoogleMaps();

        // SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewPagerTab);
        // viewPagerTab.setViewPager(viewPager);


    }

    public void referenciaComponentes() {
        abaCelular = findViewById(R.id.abaCelular);
        abaMapa = findViewById(R.id.abaMapa);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMenu);

        ativandoAsAbas();

    }

    public void ativandoAsAbas() {
        abaCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToCelularFragment();
            }
        });

        abaMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToMapaFragment();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    public void aplicaGoogleMaps() {

        // Sua função é se conectar com serviços da Google, sua execução é assíncrona e o resultado desse método é
        // entregue ao método "onMapR
        // eady"
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMenu);

        mapFragment.getMapAsync(this);

        //Conexao com o Google Services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // conecta a API
        mGoogleApiClient.connect();


        // pega os serviços para uso da localização na API da Google
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // método ou função para obter a localização do celular atual
        getLastLocation();

        //configurando recurso para avisar quando o usuario mudou de posicao
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            //obtém a última localização conhecida
                            mLastLocation = task.getResult();
                            // mostrando a latitude e longitude do celular atual na telinha do APP
                            msg("Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());
                            // setando a localização atual do celular no MAPA, com o marcador
                            setMyLocation(mLastLocation);

                        } else {

                            //Não há localização conhecida ou houve uma excepção
                            //A excepção pode ser obtida com task.getException()

                            msg("errro");
                        }
                    }
                });
    }
    //feedback se a conexao com o google play foi realizada ou nao

    public void changeToCelularFragment() {

        // instanciando o fragment
        celFragment = new CelularFragment();

        // Configurar objeto para o fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Onde vou exibir, qual fragment será mostrado
        transaction.replace(R.id.fragmentMenu, celFragment);
        transaction.commit();
    }

    public void changeToMapaFragment() {

        // Configurar objeto para o fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Onde vou exibir, qual fragment será mostrado
        transaction.replace(R.id.fragmentMenu, mapFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Funções do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean sucesso = false;

        // pegando o acesso a variável DAO para fazer operações com o banco
        UsuarioDAO.getInstance();


        switch (item.getItemId()) {
            case R.id.menuSair:
                deslogarUsuario();
                break;
            case R.id.menuConfig:
                //chamar as telas de cadastro
                Intent intent1 = new Intent(this, Cadastro1Activity.class);
                startActivity(intent1);
                break;
            case R.id.menuExcluirConta:
                 sucesso = UsuarioDAO.dao.excluirUsuarioAutenticado();
                 if(sucesso == true)
                 {
                     Intent intent = new Intent(this, LoginActivity.class);

                     msg("Usuário deletado com sucesso!");

                     startActivity(intent);
                 }
                 else
                 {
                     msg("Erro ao deletar o usuário! :(");
                 }
                break;
            case R.id.menuTutorial:
                //chamar as telas de tutorial
                Intent intent2 = new Intent(this, TutorialActivity.class);
                startActivity(intent2);
                break;

            case R.id.menuCelRoubado:
                CelularDAO daoC = new CelularDAO();
                daoC.inserirRoubado();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario() {

        boolean sucesso = false;

        try {
            UsuarioDAO.getInstance();

            sucesso = UsuarioDAO.dao.deslogarUsuario();

            if(sucesso)
            {
                Intent intent = new Intent(this, LoginActivity.class);
                msg("Logout feito com sucesso!");
                startActivity(intent);

            }
            else
            {
                msg("Erro ao fazer logout!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void setMyLocation(Location location) {
        if (location != null) {
            // Recupera latitude e longitude da
            // ultima localização do usuário
            LatLng ultimaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
            // Configuração da câmera
            final CameraPosition position = new CameraPosition.Builder()
                    .target(ultimaLocalizacao)     //  Localização
                    .bearing(45)        //  Rotação da câmera
                    .tilt(90)            //   ngulo em graus
                    .zoom(17)           //  Zoom
                    .build();
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.animateCamera(update);
            // Criando um objeto do tipo MarkerOptions
            final MarkerOptions markerOptions = new MarkerOptions();
            // Configurando as propriedades do marker
            markerOptions.position(ultimaLocalizacao)    // Localização
                    .title("Minha Localização")       // Título
                    .snippet("Latitude: , Longitude:"); // Descrição

            mMap.clear();
            mMap.addMarker(markerOptions);
        }

    }


    public void msg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
