package br.unicamp.cotuca.projetomonitoria;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by u16195 on 25/04/2018.
 */

public class ListaHorariosAdapter extends ArrayAdapter<Horario> {

    private Context context;
    private List<Horario> horarios = null;

    public ListaHorariosAdapter(Context context, List<Horario> horarios) {
        super(context, 0, horarios);
        this.horarios = horarios;
        this.context = context;
    }

    //Sobrescreve o método getView()
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Posição do item em meio aos dados do Adapter
        Horario horario = horarios.get(position);

        //Infla o xml lista_itens.xml
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.list_horarios, null);

        //Cria uma view de texto
        TextView textViewHorario = (TextView) view.findViewById(R.id.text_view_horario);

        textViewHorario.setText(horario.getInicio() + " - " + horario.getFim());
        return view;
    }
}

