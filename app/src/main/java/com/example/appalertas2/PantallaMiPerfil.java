package com.example.appalertas2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PantallaMiPerfil extends AppCompatActivity {
    ImageButton btnDatosGrles, btnInfoAdicional, btnRMiPerfil, btnMisContactos;
    ImageButton btnModificar, btnAgregar;
    String ID_USUARIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_mi_perfil);
        Bundle b = getIntent().getExtras();
        if(b != null){
            ID_USUARIO = b.getString("Id");
        }
        Conectar();

        btnDatosGrles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaDatosGenerales.class).putExtra("Id",ID_USUARIO);;
                startActivity(i);
            }
        });

        btnRMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaEmergenciaPrincipal.class).putExtra("Id",ID_USUARIO);;
                startActivity(i);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaDatosGenerales.class).putExtra("Id",ID_USUARIO);;
                startActivity(i);
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaDatosGenerales.class).putExtra("Id",ID_USUARIO);;
                startActivity(i);
            }
        });
    }


    private void Conectar() {
        btnDatosGrles = findViewById(R.id.btnDatosGenerales);
        btnInfoAdicional = findViewById(R.id.btnInformacionAdicional);
        btnRMiPerfil = findViewById(R.id.btnRMiPerfil);
        btnModificar = findViewById(R.id.btnModificar);
        btnAgregar = findViewById(R.id.btnAgregar);
        //btnMisContactos = (ImageButton)findViewById(R.id.btnMisContactosPrincipal);

    }
}
