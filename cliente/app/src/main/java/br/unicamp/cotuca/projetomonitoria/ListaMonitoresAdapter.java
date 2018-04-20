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

import com.android.volley.Response;

import java.util.List;

/**
 * Created by u16195 on 19/04/2018.
 */

public class ListaMonitoresAdapter extends ArrayAdapter<Monitor> {

    private Context context;
    private List<Monitor> monitores = null;

    public ListaMonitoresAdapter(Context context, List<Monitor> monitores) {
        super(context, 0, monitores);
        this.monitores = monitores;
        this.context = context;
    }

    //Sobrescreve o método getView()
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Posição do item em meio aos dados do Adapter
        Monitor monitor = monitores.get(position);

        //Infla o xml lista_itens.xml
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.list_monitores, null);

        //Cria uma view de imagem
        ImageView imageViewMonitor = (ImageView) view.findViewById(R.id.image_view_monitor);
        byte[] imageAsBytes = Base64.decode(monitor.getImagem().getBytes(), Base64.DEFAULT);
        imageViewMonitor.setImageBitmap(
                BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
        );

        //Cria uma view de texto
        TextView textViewNomeMonitor = (TextView) view.findViewById(R.id.text_view_nome_monitor);

        textViewNomeMonitor.setText(monitor.getNome());

        return view;
    }
}
