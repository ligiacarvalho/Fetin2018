package com.fetin.securityapp.control.Menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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
import com.fetin.securityapp.control.Menu.Fragment.GraphicFragment;
import com.fetin.securityapp.control.Menu.Fragment.MapaFragment;
import com.fetin.securityapp.control.SegundoPlano.ArduinoService;
import com.fetin.securityapp.control.SegundoPlano.BloqueioService;
import com.fetin.securityapp.control.Tutorial.TutorialActivity;
import com.fetin.securityapp.model.Celular;
import com.fetin.securityapp.model.Dao.CelularDAO;
import com.fetin.securityapp.model.Dao.UsuarioDAO;
import com.fetin.securityapp.model.Usuario;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
//import com.ogaclejapan.smarttablayout.SmartTabLayout;

//search
import android.app.SearchManager;
import android.content.Context;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.Calendar;


public class MenuActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private FirebaseAuth autenticacao;
    private ViewPager viewPager;
    //private SmartTabLayout smartTabLayout;
    private CelularFragment celFragment;
    private MapaFragment mapaFragment;
    private FirebaseAuth usuarioAuth;
    private GraphicFragment graphicFragment;

    // Variáveis do Google Maps
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    public static Location mLastLocation;
    private LocationRequest locationRequest;
    private SupportMapFragment mapFragment;
    private SearchView.OnQueryTextListener searchQueryListener;
    private FragmentTransaction transaction;
    public static MediaPlayer somAlarm;
    public static Celular Cel_cadastrado;
    private Button botaoPlay;
    private Button botaoStop;


    public static ArrayList<String> lista_temporaria;


    // Abas
    private Button abaCelular, abaMapa, abaGrafico;
    public int aba;

    //contadores para criar o grafico
    public static int contDia;
    public static int contSemana;
    public static int contMes;
    public static int contAno;

    //contadores dos meses grafico
    public static int contJaneiro;
    public static int contFevereiro;
    public static int contMarco;
    public static int contAbril;
    public static int contMaio;
    public static int contJunho;
    public static int contJulho;
    public static int contAgosto;
    public static int contSetembro;
    public static int contOutubro;
    public static int contNovembro;
    public static int contDezembro;

    public static int esta_logado = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        celFragment = new CelularFragment();

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("4 S.A");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        inicio();

        UsuarioDAO.user_cadastrado = buscarUsuarioLogado();

/*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

       /* if(esta_logado == 0) {
            // ativa o bloqueio
            Intent intent = new Intent(this, ArduinoService.class);
            startService(intent);
            esta_logado = 1;

        }*/
    }


    public Usuario buscarUsuarioLogado() {
        FirebaseUser usuario_logado = FirebaseAuth.getInstance().getCurrentUser();
        String email_user_logado = usuario_logado.getEmail();

        // uma busca na lista, querendo o usuario que está logado
        Usuario usuario_encontrado = null;

        for (int i = 0; i < UsuarioDAO.lista_de_usuarios.size(); i++) {

            if (UsuarioDAO.lista_de_usuarios.get(i).getEmail().equals(email_user_logado)) {

                usuario_encontrado = UsuarioDAO.lista_de_usuarios.get(i);

                usuario_encontrado.setCelularP(UsuarioDAO.lista_de_usuarios.get(i).getCelularP());

                return usuario_encontrado;

            }
        }

        return usuario_encontrado;
    }

    //se está na aba mapa
    public void estaNoMapa() {
        aba = 0;
    }

    //se está na aba celular
    public void estaNoCel() {
        aba = 1;
    }

    //lógica de search no mapa ou cel
    public void funcao(String textoDePesquisa) {
        // lógica do if
        //estaNoMapa();
        //estaNoCel();

        lista_temporaria.clear();

        boolean achou = false;

        if (aba == 1) {

            //msg("Aba celular");
            // na aba celular buscar pelo IMEI
            for (int i = 0; i < CelularDAO.lista_de_roubo.size(); i++) {

                if (CelularDAO.lista_de_roubo.get(i).getCelularP().getImei1().equals(textoDePesquisa)) {
                    lista_temporaria.add(CelularDAO.lista_de_roubo.get(i).getCelularP().getImei1());
                    achou = true;
                    celFragment.atualizaLista();

                    break;
                }
            }
            if (achou == false) {
                msg("IMEI não encontrado");
            }
        }
        //na aba mapa buscar pela localizacao do codigo
        else if (aba == 0) {
            //msg("Aba mapa");

            {
                for (int i = 0; i < CelularDAO.lista_de_roubo.size(); i++) {
                    String cc = Integer.toString(CelularDAO.lista_de_roubo.get(i).getCelularP().getCodigo());

                    if (cc.contains(textoDePesquisa)) {
                        double latitude_temporaria = CelularDAO.lista_de_roubo.get(i).getCelularP().getCoordenadaLat();
                        double longitude_temporaria = CelularDAO.lista_de_roubo.get(i).getCelularP().getCoordenadaLong();
                        Location localizacao_temporaria_cel = new Location("123123");
                        setMyLocation_usuario(localizacao_temporaria_cel, latitude_temporaria, longitude_temporaria);
                        achou = true;
                    }

                }
                if (achou == false)
                    msg("Código não encontrado");
            }
        } else
            msg("Aba grafico");
    }

    public void referenciaComponentes() {
        abaCelular = findViewById(R.id.abaCelular);
        abaMapa = findViewById(R.id.abaMapa);
        abaGrafico = findViewById(R.id.abaGrafico);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentMenu);
        ativandoAsAbas();
    }

    public void ativandoAsAbas() {
        abaCelular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aba = 1;
                changeToCelularFragment();
            }
        });
        abaMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aba = 0;

                changeToMapaFragment();

                inserindoMarcadores();

            }
        });
        abaGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aba = 2;
                changeToGraficoFragment();
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    // cópia do getLastLocation para o banco de dadosit
    @SuppressLint("MissingPermission")
    private Location getUltimoLocal() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            //obtém a última localização conhecida
                            mLastLocation = task.getResult();
                            // mostrando a latitude e longitude do celular atual na telinha do APP
                            //msg("Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());

                        } else {

                            //Não há localização conhecida ou houve uma excepção
                            //A excepção pode ser obtida com task.getException()

                            //msg("Erro");
                        }
                    }
                });
        return mLastLocation;
    }

    public void changeToGraficoFragment() {
        graphicFragment = new GraphicFragment();

        // Configurar objeto para o fragment
        transaction = getSupportFragmentManager().beginTransaction();
        // Onde vou exibir, qual fragment será mostrado
        transaction.replace(R.id.fragmentMenu, graphicFragment);
        transaction.commit();

    }

    public void changeToCelularFragment() {

        // instanciando o fragment
        celFragment = new CelularFragment();

        // Configurar objeto para o fragment
        transaction = getSupportFragmentManager().beginTransaction();
        // Onde vou exibir, qual fragment será mostrado
        transaction.replace(R.id.fragmentMenu, celFragment);
        transaction.commit();
    }

    public void changeToMapaFragment() {

        // Configurar objeto para o fragment
        transaction = getSupportFragmentManager().beginTransaction();

        // Onde vou exibir, qual fragment será mostrado
        transaction.replace(R.id.fragmentMenu, mapFragment);


        transaction.commit();

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


        //transaction.detach(celFragment);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //  searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(searchQueryListener);
        searchView.setIconifiedByDefault(false);

        return super.onCreateOptionsMenu(menu);
    }

    //funçao searchO
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    //funcao search
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            Toast.makeText(getApplicationContext(), "Texto = " + query, Toast.LENGTH_LONG).show();
            //doMySearch(query);
        }
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
                if (sucesso == true) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    msg("Usuário deletado com sucesso!");
                    startActivity(intent);
                } else {
                    msg("Erro ao deletar o usuário! :(");
                }
                break;
            case R.id.menuTutorial:
                //chamar as telas de tutorial
                Intent intent2 = new Intent(this, TutorialActivity.class);
                startActivity(intent2);
                break;

            case R.id.menuCelRoubado:

                playMusic();

                CelularDAO daoC = new CelularDAO();


                UsuarioDAO.user_cadastrado = buscarUsuarioLogado();

                getLastLocation();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mLastLocation==null){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (mLastLocation == null)
                    msg("Sem localizacao");
                else
                    daoC.inserirRoubado(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                sendSms(UsuarioDAO.user_cadastrado.getContatoProximo(),UsuarioDAO.user_cadastrado.getCelularP().getCodigo());

                // ativa o bloqueio
               Intent intent = new Intent(this, BloqueioService.class);
               startService(intent);



                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // método para enviar SMS
    public void sendSms(String contato, int cod) {
        String usuario = UsuarioDAO.user_cadastrado.getNome();
        String senha = Integer.toString(cod);
        Intent intenet = new Intent();
        String msg = "S.O.S!\n" + usuario + " foi roubado(a)!\n" + "Código para localização: \n" + senha;


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contato, null, msg, null, null);
            //  Toast.makeText(getApplicationContext(), "SMS enviado!.", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha ao enviar! Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void playMusic() {
        // Referenciando o "somAlarm" com a música que está na pasta RAW
        somAlarm = MediaPlayer.create(this, R.raw.alarme_roubo);
        somAlarm.start();

    }

    public void deslogarUsuario()
    {
        // parando o serviço de bloueio do arduino
        Intent intent_arduino = new Intent(this, ArduinoService.class);
        stopService(intent_arduino);


        MenuActivity.esta_logado = 0;

        boolean sucesso = false;

        try {
            UsuarioDAO.getInstance();

            sucesso = UsuarioDAO.dao.deslogarUsuario();

            if (sucesso) {
                Intent intent = new Intent(this, LoginActivity.class);
                msg("Logout feito com sucesso!");
                startActivity(intent);

            } else {
                msg("Erro ao fazer logout!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //========== *GOOGLE MAPS* ===================================== *GOOGLE MAPS* ===========================================//
    //Insere marcadores entre 1 dia, 1 semana e 1 mês
    public void inserindoMarcadores() {
        /*
           MARCADOR AZUL - dentro de 30 dias
           MARCADOR ROXO - dentro de 7 dias
           MARCADOR VERMELHO - mesmo dia

         */

        contAno = contMes = contSemana = contDia = 0;

        int dia, mes, ano, dia_atual = 0, mes_atual = 0, ano_atual = 0;

        Location localizacao_cel_roubado = new Location("123123");

        Calendar calendario = Calendar.getInstance();
        dia_atual = calendario.get(Calendar.DATE);
        mes_atual = calendario.get(Calendar.MONTH);
        ano_atual = calendario.get(Calendar.YEAR);
        mes_atual = mes_atual + 1;

        contJaneiro = contAbril = contAgosto = contFevereiro = contMarco = contMaio = contJunho = contJulho = contSetembro = contOutubro = contNovembro = contDezembro = 0;

        for (int i = 0; i < CelularDAO.lista_de_roubo.size(); i++) {
            dia = CelularDAO.lista_de_roubo.get(i).getCelularP().getDia();
            mes = CelularDAO.lista_de_roubo.get(i).getCelularP().getMes();
            ano = CelularDAO.lista_de_roubo.get(i).getCelularP().getAno();
            localizacao_cel_roubado.setLongitude(CelularDAO.lista_de_roubo.get(i).getCelularP().getCoordenadaLong());
            localizacao_cel_roubado.setLatitude(CelularDAO.lista_de_roubo.get(i).getCelularP().getCoordenadaLat());


            verificaMes(mes,mes_atual, ano, ano_atual, dia, dia_atual);

            if (ano == ano_atual) {
                if (mes == mes_atual) {
                    if (dia == dia_atual) {
                        setMyLocationWithColor(localizacao_cel_roubado, "vermelho");

                    } else if (dia_atual - dia <= 7) {
                        setMyLocationWithColor(localizacao_cel_roubado, "roxo");
                        contSemana++;
                    } else {
                        setMyLocationWithColor(localizacao_cel_roubado, "azul");
                    }
                } else if (mes_atual - mes == 1) {
                    if (dia_atual == dia) {
                        setMyLocationWithColor(localizacao_cel_roubado, "azul");
                    } else if (dia == 31) {
                        dia = dia_atual;
                        if (dia <= 7) {
                            setMyLocationWithColor(localizacao_cel_roubado, "roxo");
                            contSemana++;
                        } else {
                            setMyLocationWithColor(localizacao_cel_roubado, "vermelho");
                            //contDia++;
                        }
                    } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {

                        if (dia == 30)
                            dia = dia_atual;
                        else
                            dia = 30 - dia + dia_atual;

                        if (dia <= 7) {
                            setMyLocationWithColor(localizacao_cel_roubado, "roxo");
                            contSemana++;
                        } else {
                            setMyLocationWithColor(localizacao_cel_roubado, "vermelho");
                            //contDia++;
                        }
                    } else if (mes == 2) {
                        if (dia == 28 || dia == 29) {
                            dia = dia_atual;
                        } else
                            dia = 28 - dia + dia_atual;

                        if (dia <= 7) {
                            setMyLocationWithColor(localizacao_cel_roubado, "roxo");
                            contSemana++;
                        } else {
                            setMyLocationWithColor(localizacao_cel_roubado, "azul");
                        }
                    } else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                        dia = dia_atual + 1;
                        if (dia <= 7) {
                            setMyLocationWithColor(localizacao_cel_roubado, "roxo");
                            contSemana++;
                        } else
                            setMyLocationWithColor(localizacao_cel_roubado, "azul");
                    }


                } else {
                    Log.i("Mes", "Diferença de mês do roubo maior que 1");
                }
            } else
                Log.i("Ano", "Ano Diferente!");
        }
    }


    public void verificaMes(int mes, int mesatual, int ano, int anoatual, int dia, int diaatual)
    {


        if(ano == anoatual) {

            contAno ++;

            if (mes == mesatual) {
                contMes++;
                if (dia == diaatual)
                    contDia++;
            }

            switch (mes) {
                case 1:
                    contJaneiro++;
                    break;
                case 2:
                    contFevereiro++;
                    break;
                case 3:
                    contMarco++;
                    break;
                case 4:
                    contAbril++;
                    break;
                case 5:
                    contMaio++;
                    break;
                case 6:
                    contJunho++;
                    break;
                case 7:
                    contJulho++;
                    break;
                case 8:
                    contAgosto++;
                    break;
                case 9:
                    contSetembro++;
                    break;
                case 10:
                    contOutubro++;
                    break;
                case 11:
                    contNovembro++;
                    break;
                case 12:
                    contDezembro++;
                    break;
            }
        }
    }

    //Coloca a tela do mapa
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

    //Pega a ultima localização
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
                            //setMyLocation(mLastLocation);

                        } else {

                            //Não há localização conhecida ou houve uma excepção
                            //A excepção pode ser obtida com task.getException()

                            //msg("Erro");
                        }
                    }
                });
    }

    //Conecta e desconecta com o Google Play Services
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Iniciando o monitoramento do GPS
        startLocationUpdates(); // Inicia o GPS
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //Verifica se a conexão com o Google Play services
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    // Método responsável por ativar o monitoramento do GPS
    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        for (Location location : locationResult.getLocations()) {
                            if(ArduinoService.verificando_roubo == 1) {
                                mLastLocation = location;

                                UsuarioDAO.user_cadastrado = buscarUsuarioLogado();

                                UsuarioDAO.user_cadastrado.getCelularP().setCoordenadaLat(location.getLatitude());
                                UsuarioDAO.user_cadastrado.getCelularP().setCoordenadaLong(location.getLongitude());

                                UsuarioDAO.getInstance();

                                UsuarioDAO.referenciaDoUsuario.child(UsuarioDAO.user_cadastrado.getChave()).setValue(UsuarioDAO.user_cadastrado);

                                msg("Longitude: " + location.getLongitude() + " Latitude: " + location.getLatitude());

                                DatabaseReference banco = UsuarioDAO.referenciaDoBanco.child("Celulares Roubados");

                                banco.child(UsuarioDAO.user_cadastrado.getChave()).setValue(UsuarioDAO.user_cadastrado);
                                //setMyLocation_usuario();


                                //setMyLocation(location);
                            }
                        }
                    }
                }
                , null);
    }

    // Método responsável por desativar o monitoramento do GPS
    protected void stopLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(new LocationCallback());
    }

    //Paralisa a localização em tempo real
    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates(); // Para o GPS
    }

    //Pega a última localização e coloca marcador
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
                    .snippet("Latitude: , Longitude:") // Descrição
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));


            mMap.clear();
            mMap.addMarker(markerOptions);

        }

    }

    //Mostra uma mensagem na tela com a latitude e longitude
    public void msg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    //Inserindo marcadores coloridos com a localização do celular roubado
    public void setMyLocationWithColor(Location location, String cor) {
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

            if (cor.equalsIgnoreCase("azul")) {
                // Configurando as propriedades do marker
                markerOptions.position(ultimaLocalizacao)    // Localização
                        .title("Minha Localização")       // Título
                        .snippet("Latitude: , Longitude:") // Descrição
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            } else if (cor.equalsIgnoreCase("roxo")) {
                // Configurando as propriedades do marker
                markerOptions.position(ultimaLocalizacao)    // Localização
                        .title("Minha Localização")       // Título
                        .snippet("Latitude: , Longitude:") // Descrição
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

            } else if (cor.equalsIgnoreCase("vermelho")) {
                // Configurando as propriedades do marker
                markerOptions.position(ultimaLocalizacao)    // Localização
                        .title("Minha Localização")       // Título
                        .snippet("Latitude: , Longitude:") // Descrição
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            }
            //mMap.clear();
            mMap.addMarker(markerOptions);

        }

    }

    //cópia do onCreate para recarregar os marcadores coloridos
    public void inicio() {


        celFragment = new CelularFragment();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("SecurityApp");


        // verificando se a variável "dao" já foi criada
        UsuarioDAO.getInstance();
        referenciaComponentes();


        CelularDAO.lista_de_roubo.clear();
        CelularDAO daoC = new CelularDAO();
        daoC.buscarCelularesRoubado();


        UsuarioDAO.lista_de_usuarios.clear();

        // fazendo a busca dos usuários no FireBase, e armazenando em uma lista chamada: "lista_de_usuarios"
        UsuarioDAO.dao.buscarUsuarios();

        referenciaComponentes();


        //autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        lista_temporaria = new ArrayList<>();


        searchQueryListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                funcao(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if (newText.equals("")) {
                    lista_temporaria.clear();

                    if (aba == 1)
                        celFragment.atualizaLista();

                }

                return true;
            }

        };

        aplicaGoogleMaps();

        // SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewPagerTab);
        // viewPagerTab.setViewPager(viewPager);
    }


    //Pega a última localização e coloca marcador para o usuário que teve o celular roubado
    public void setMyLocation_usuario(Location location, double latitude, double longitude) {
        if (location != null) {
            // Recupera latitude e longitude da
            // ultima localização do usuário
            LatLng ultimaLocalizacao = new LatLng(latitude, longitude);
            // LatLng ultimaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
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
                    .snippet("Latitude: , Longitude:") // Descrição
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.clear();
            mMap.addMarker(markerOptions);

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //setMyLocation_usuario(location);
        msg("mudou localizacao");
    }

}
