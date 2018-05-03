package br.unicamp.cotuca.projetomonitoria;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {

    private int diaSemana = 2;
    private String url = "";
    private ListView lvHorarios = null;
    private Context c = null;
    TextView tvSemHorarios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Bundle b = getIntent().getExtras();

        lvHorarios = (ListView) findViewById(R.id.lvHorarios);
        tvSemHorarios = (TextView) findViewById(R.id.tvSemHorarios);
        RadioGroup rbgDiasSemana = (RadioGroup) findViewById(R.id.rbgDiasSemana);
        TextView tvNome = (TextView) findViewById(R.id.tvNome);

        tvNome.setText(b.getString("nome"));
        url = "http://177.220.18.62:8080/api/monitores/" + b.getInt("ra") + "/horarios/";

        c = this;

        mostrarHorarios(diaSemana); //sempre inicia mostrando os horários da segunda

        rbgDiasSemana.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                String nome = getResources().getResourceEntryName(checkedId);
                nome = nome.substring(2, 3);
                mostrarHorarios(Integer.parseInt(nome));
            }
        });
    }

    private void mostrarHorarios(int diaSemana)
    {
        tvSemHorarios.setVisibility(View.INVISIBLE);
        lvHorarios.setVisibility(View.INVISIBLE);
        RequestQueue queue = Volley.newRequestQueue(c);
        String s = url + diaSemana;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONArray vetorHorarios = new JSONArray(response);
                            if (vetorHorarios.length() == 0)
                                tvSemHorarios.setVisibility(View.VISIBLE);
                            else
                            {
                                final List<Horario> horarios = gerarHorarios(vetorHorarios);
                                final ListaHorariosAdapter horariosAdapter = new ListaHorariosAdapter(c, horarios);
                                lvHorarios.setAdapter(horariosAdapter);
                                lvHorarios.setVisibility(View.VISIBLE);
                            }
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

        queue.add(stringRequest);
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

    private List<Horario> gerarHorarios(JSONArray vet) {
        String inicio, fim;
        List<Horario> horarios = new ArrayList<Horario>();
        try {
            for (int i = 0; i < vet.length(); i++) {
                inicio = vet.getJSONObject(i).getString("inicio");
                fim = vet.getJSONObject(i).getString("fim");

                horarios.add(new Horario(inicio, fim));
            }
        }
        catch (Exception e)
        {
            criarMensagem(e.getMessage());
        }

        return horarios;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
