package com.example.appalertas2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PantallaInicial2 extends AppCompatActivity {
    EditText txtEmail, txtContrasena;
    ImageButton btnIng, btnMenuRegistro;
    static boolean valido = false ;
    String validarLogin;
    static String ID_USUARIO;
    static String NOMBRE_USUARIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial_2);
        conectar();
        valido = false;
        timer();

        btnMenuRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaRegistrar1.class);
                startActivity(i);
            }
        });
        btnIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtEmail.getText().toString().equals("") || txtContrasena.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Ingrese el Email y la contraseña", Toast.LENGTH_LONG).show();
                } else {

                    if (validarLogin.equals("101")) {

                        Toast.makeText(getApplicationContext(), "BIENVENIDO " + NOMBRE_USUARIO, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), PantallaEmergenciaPrincipal.class);
                        i.putExtra("Id", ID_USUARIO);
                        startActivity(i);
                        valido=true;
                    } else if (validarLogin.equals("102")) {

                        Toast.makeText(getApplicationContext(), "El email y/o la contraseña son incorrectos", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    private void timer(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          validarUsuarioContraseña(txtEmail.getText().toString(), txtContrasena.getText().toString());
                                      }
                                  },
                3000, 1000);   // 1000 Millisecond  = 1 second
        if(valido){
            timer.cancel();
        }
    }
    public void validarUsuarioContraseña(final String email, final String contrasena){


        String URL_validarLogin = "http://labs.ebotero.com/servicios_ayudapp/public/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_validarLogin,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonobj = new JSONObject(response);
                            validarLogin = jsonobj.getString("status");
                            if(validarLogin.equals("101")) {
                                JSONArray list = jsonobj.optJSONArray("data");
                                jsonobj = list.getJSONObject(0);
                                ID_USUARIO = jsonobj.getString("Id");
                                NOMBRE_USUARIO = jsonobj.getString("Nombre").toUpperCase();
                            }
                        }catch (JSONException ex){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("email", email);
                parametros.put("contrasena", contrasena);
                return parametros;
            }
        };


        RequestQueue requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringRequest);
    }

    private void conectar() {

        txtContrasena = findViewById(R.id.etContraseña);
        txtEmail = findViewById(R.id.etEmail);
        btnIng = findViewById(R.id.btnIng);
        btnMenuRegistro = findViewById(R.id.btnRegistrarInicio2);
    }
}
