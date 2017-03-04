package com.putallazmilton.alquick.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.putallazmilton.alquick.R;

/**
 * Created by Milton on 08/02/2017.
 */

public class AlquilerHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView tipoalq;
    private TextView propietario;
    private TextView id;
    private ToggleButton botonstar;
    private boolean pressed;
    public AlquilerHolder(View itemView) {
        super(itemView);
        botonstar = (ToggleButton) itemView.findViewById(R.id.heartbutton);
        image = (ImageView) itemView.findViewById(R.id.imagen);
        tipoalq =(TextView) itemView.findViewById(R.id.tipoalq);
        propietario = (TextView) itemView.findViewById(R.id.tvpropietario);
        id = (TextView) itemView.findViewById(R.id.idalqui);

    }

    public ToggleButton getBotonstar() {
        return botonstar;
    }

    public void setBotonstar(ToggleButton botonstar) {
        this.botonstar = botonstar;
    }

    public ImageView getImage(){
        return this.image;
    }
    public void setTipoAlq(String text) {
        tipoalq.setText(text);
    }

    public void setPropietario(String pro){
        propietario.setText(pro);
    }

    public void setId(String idd){
        id.setText(idd);
    }

    public TextView getId() {
        return id;
    }

    public TextView getTipoalq() {
        return tipoalq;
    }

    public TextView getPropietario() {
        return propietario;
    }
}