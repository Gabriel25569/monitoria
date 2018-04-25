package br.unicamp.cotuca.projetomonitoria;

import android.media.Image;

/**
 * Created by u16195 on 19/04/2018.
 */

public class Monitor {
    private int ra;
    private String nome;
    private String imagem;

    public Monitor(int r, String n, String i) {
        super();
        this.ra = r;
        this.nome = n;
        this.imagem = i;
    }

    public int getRa() { return ra; }

    public String getNome () { return nome; }

    public String getImagem() { return imagem; }
}