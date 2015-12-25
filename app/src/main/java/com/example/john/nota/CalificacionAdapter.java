package com.example.john.nota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Objetos.Calificacion;

/**
 * Created by john on 24/12/15.
 */
public class CalificacionAdapter extends BaseAdapter {
    private ArrayList<Calificacion> entradas;
    private int R_layout_IdView;
    private Context contexto;

    public CalificacionAdapter(Context contexto, int R_layout_IdView, ArrayList<Calificacion> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
            TextView nota1, nota2, nota3, nombre;
            nombre = (TextView) view.findViewById(R.id.txtNombreMateria);
            nota1 = (TextView) view.findViewById(R.id.txtNota1);
            nota2 = (TextView) view.findViewById(R.id.txtNota2);
            nota3 = (TextView) view.findViewById(R.id.txtNota3);

            nombre.setText(entradas.get(posicion).getMateria());
            nota1.setText(entradas.get(posicion).getNota1());
            nota2.setText(entradas.get(posicion).getNota2());
            nota3.setText(entradas.get(posicion).getNota3());
        }
        return view;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return entradas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }


    public ArrayList<Calificacion> getData() {
        return entradas;
    }

}
