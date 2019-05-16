package com.example.appalertas2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cache.Session;

public class PantallaMisContactos extends AppCompatActivity {

    ListView lvContactos;
    ImageButton btnAgregarContacto;
    String ID_USUARIO;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaContactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_mis_contactos);
        conecta();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            ID_USUARIO=b.getString("Id");
            Log.d("OAAA",ID_USUARIO);

        }

        cargarContactos(ID_USUARIO);


        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PantallaAgregarContacto.class);
                i.putExtra("Id", ID_USUARIO);
                startActivity(i);
            }
        });
    }


    private void conecta(){
        lvContactos = findViewById(R.id.lvContactos);
        btnAgregarContacto = findViewById(R.id.btnAgregarContacto);

    }

    private void cargarContactos(final String idUsuario)
    {
        String URL_CARGAR_CONTACTOS = "http://labs.ebotero.com/servicios_ayudapp/public/contacto_usuario";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CARGAR_CONTACTOS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonobj = new JSONObject(response);
                            llenarListaContactos(jsonobj);

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
                parametros.put("IdUsuario", idUsuario);
                return parametros;
            }
        };


        RequestQueue requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringRequest);
    }




    private void llenarListaContactos(JSONObject obj){
        try {
            JSONArray list = obj.optJSONArray("data");

            if (list.length() != 0) {
                for (int i = 0; i < list.length(); i++) {
                    JSONObject json_data = list.getJSONObject(i);
                    String contenido = json_data.getString("Nombre") +" - "+json_data.getString("Celular");
                    listaContactos.add(contenido);

                }
                adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, listaContactos);
                lvContactos.setAdapter(adapter);

            }else
            {
                Toast.makeText(getApplicationContext(),"NO TIENE CONTACTOS REGISTRADOS", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex){
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }finally{
        }

    }

}
