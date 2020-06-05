package com.empresadelivery.empresadelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.empresadelivery.empresadelivery.modelos.Crudtiposdepagorealm;
import com.empresadelivery.empresadelivery.modelos.Tiposdepago;
import com.empresadelivery.empresadelivery.modelos.Tiposedepagorealm;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class Dashboardempresa extends AppCompatActivity {
    String FileName = "myfile";
    SharedPreferences prefs;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    ImageView logoempresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardempresa);
        Realm.init(getApplicationContext());

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("pedido.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);


        prefs = this.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String idempresa=prefs.getString("idempresa","");
        String razonsocialempresa=prefs.getString("razonsocialempresa","");
        String direccionempresa=prefs.getString("direccionempresa","");
        String estadoempresa=prefs.getString("estadoempresa","");
        String sloganempresa=prefs.getString("sloganempresa","");
        String nombreadministrador=prefs.getString("idempresa","");
        String idrubroempresa=prefs.getString("idrubroempresa","");
        String montominimodeventa=prefs.getString("montominimodeventa","");
        String imagenempresa=prefs.getString("imagenempresa","");
        TextView razon=(TextView) findViewById(R.id.razonsocialdashboard);
        TextView dire=(TextView) findViewById(R.id.direccionempresadashboard);

        logoempresa=(ImageView) findViewById(R.id.logoempresa);
        Picasso.get().load(imagenempresa).transform(new CropSquareTransformation()).resize(200, 200)
                .into( logoempresa);
razon.setText(razonsocialempresa);
dire.setText(direccionempresa);
//new cargartiposdepago().execute();
        CardView irapedido=(CardView) findViewById(R.id.cardirapedido);
        irapedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, MainActivity.class);
                startActivity(intent);

            }
        });
        CardView editarempresa=(CardView) findViewById(R.id.cardempresa);
        editarempresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, EditarEmpresa.class);
                startActivity(intent);

            }


    });

        CardView cardtiposdepago=(CardView) findViewById(R.id.cardtiposdepagoair);
        cardtiposdepago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, Administrartiposdepago.class);
                startActivity(intent);

            }


        });


        CardView cardcliente=(CardView) findViewById(R.id.cardclientes);
        cardcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, Manejodeusuarios.class);
                startActivity(intent);

            }


        });
        CardView carproductos=(CardView) findViewById(R.id.cardproductos);
        carproductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardempresa.this, Listaproductos.class);
                startActivity(intent);

            }


        });
    }




}