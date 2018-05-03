package br.unicamp.cotuca.projetomonitoria;

/**
 * Created by u16195 on 25/04/2018.
 */

public class Horario {
    private String inicio, fim;

    public Horario (String i, String f)
    {
        super();
        this.inicio = i;
        this.fim = f;
    }

    public String getInicio() { return inicio; }

    public String getFim() { return fim; }
}
