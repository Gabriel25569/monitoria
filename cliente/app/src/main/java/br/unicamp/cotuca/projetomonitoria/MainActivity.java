package br.unicamp.cotuca.projetomonitoria;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://177.220.18.62:8080/api/monitores";

        final Context c = this;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                   @Override
                    public void onResponse(String response) {
                       try
                        {
                            JSONArray vetorMonitores = new JSONArray(response);
                            final List<Monitor> monitores = gerarMonitores(vetorMonitores);

                            final ListView lvMonitores = (ListView) findViewById(R.id.lvMonitores);
                            final ListaMonitoresAdapter monitoresAdapter = new ListaMonitoresAdapter(c, monitores);
                            lvMonitores.setAdapter(monitoresAdapter);

                            lvMonitores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    criarActivity(monitores.get(i).getRa(), monitores.get(i).getNome());
                                }
                            });
                        }
                        catch (Exception e)
                        {
                            criarMensagem(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                criarMensagem(error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private List<Monitor> gerarMonitores(JSONArray vet) {
        String nome, imagem;
        int ra;
        List<Monitor> monitores = new ArrayList<Monitor>();
        try {
            for (int i = 0; i < vet.length(); i++) {
                ra = vet.getJSONObject(i).getInt("ra");
                nome = vet.getJSONObject(i).getString("nome");
                imagem = vet.getJSONObject(i).getString("imagem");

                monitores.add(new Monitor(ra, nome, imagem));
            }
        }
        catch (Exception e)
        {
            criarMensagem(e.getMessage());
        }

        return monitores;
    }

    private void criarMensagem (String texto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erro");
        builder.setMessage(texto);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {}
        });

        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void criarActivity(int ra, String nome) {
        Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
        Bundle b = new Bundle();
        b.putInt("ra", ra);
        b.putString("nome", nome);
        intent.putExtras(b);
        startActivity(intent);
    }
}
