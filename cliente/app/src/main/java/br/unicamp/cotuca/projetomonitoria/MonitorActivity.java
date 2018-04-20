package br.unicamp.cotuca.projetomonitoria;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Bundle b = getIntent().getExtras();
        int value = -1; // or other values

        ListView lvHorarios = (ListView) findViewById(R.id.lvHorarios);
        RadioGroup rbgDiasSemana = (RadioGroup) findViewById(R.id.rbgDiasSemana);
        TextView tvNome = (TextView) findViewById(R.id.tvNome);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
