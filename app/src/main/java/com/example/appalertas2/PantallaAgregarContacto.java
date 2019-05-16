package com.example.appalertas2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PantallaAgregarContacto extends AppCompatActivity {

    /*
   Nombres, teléfonos de contacto (celular), tipo de relación con esa persona(1:familiar), prioridad( 1: Alta - 2: Media - 3:Baja)
    */
    ImageButton btnIngresarContacto, exit;
    EditText txtNombres,  txtTelefonoCelular;
    Spinner spnTipoContacto, spnPrioridad, spnParentesco;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaParentesco = new ArrayList<>();
    ArrayList<String> listaTipoContacto = new ArrayList<>();
    ArrayList<String> listaPrioridad = new ArrayList<>();
    String ID_USUARIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_contacto);
        conectar();
        //Aca traigo el IdUsuario
        Bundle b = getIntent().getExtras();
        if (b != null) {
            ID_USUARIO=b.getString("Id");
        }

        llenarSpnParentesco();
        llenarSpnPrioridad();
        llenarSpnTipoContacto();


        btnIngresarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombres.getText().toString().equals("") || !txtTelefonoCelular.getText().toString().equals("")  ){

                    agregarContacto(txtNombres.getText().toString(),txtTelefonoCelular.getText().toString(),String.valueOf(spnTipoContacto.getSelectedItemPosition()+1),
                            String.valueOf(spnParentesco.getSelectedItemPosition()+1),String.valueOf(spnPrioridad.getSelectedItemPosition()+1),ID_USUARIO);

                    Toast.makeText(getApplicationContext(), "Registro Exitoso!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), PantallaMisContactos.class).putExtra("Id",ID_USUARIO);
                    startActivity(i);

                }else
                {
                    Toast.makeText(getApplicationContext(), "Favor llenar todos los campos", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    private void agregarContacto(final String nombres, final String telefonoCelular, final String idTipoContacto,final String idParentesco, final String idPrioridad , final String idUsuario){
        String URL_RegistroUsuario = "http://labs.ebotero.com/servicios_ayudapp/public/crear_contacto";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RegistroUsuario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Usuario registrado con éxito", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("Nombre", nombres);
                parametros.put("IdParentesco", idParentesco);
                parametros.put("Celular", telefonoCelular);
                parametros.put("IdTipoContacto", idTipoContacto);
                parametros.put("Prioridad", idPrioridad);
                parametros.put("IdUsuario", idUsuario);
                return parametros;
            }
        };

        RequestQueue requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringRequest);
    }


    private void llenarSpnParentesco(){
        listaParentesco.clear();
        listaParentesco.add("1: Padre");
        listaParentesco.add("2: Hijo");
        listaParentesco.add("3: Abuelo");
        listaParentesco.add("4: Nieto");
        listaParentesco.add("5: Tio");
        listaParentesco.add("6: Sobrino");
        listaParentesco.add("7: Primo");
        listaParentesco.add("8: Otro");
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listaParentesco);
        spnParentesco.setAdapter(adapter);
    }


    private void llenarSpnTipoContacto(){
        listaTipoContacto.clear();
        listaTipoContacto.add("1: Familiar");
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listaTipoContacto);
        spnTipoContacto.setAdapter(adapter);
    }


    private void llenarSpnPrioridad(){
        listaPrioridad.clear();
        listaPrioridad.add("1: Alta");
        listaPrioridad.add("2: Media");
        listaPrioridad.add("3: Baja");
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listaPrioridad);
        spnPrioridad.setAdapter(adapter);
    }

    private void conectar(){

        spnParentesco = (Spinner)findViewById(R.id.spinnerTipoParentesco);
        spnPrioridad = (Spinner)findViewById(R.id.spinnerTipoPrioridad);
        spnTipoContacto = (Spinner)findViewById(R.id.spinnerTipoDeContacto);
        txtNombres = (EditText)findViewById(R.id.editNombre);
        txtTelefonoCelular = (EditText)findViewById(R.id.editCelular);
        btnIngresarContacto = (ImageButton)findViewById(R.id.btnValidar);
        exit = (ImageButton)findViewById(R.id.btnBack);

    }
}

