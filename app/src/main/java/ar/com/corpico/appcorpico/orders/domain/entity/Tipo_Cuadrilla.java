package ar.com.corpico.appcorpico.orders.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 22/05/2017.
 */

public class Tipo_Cuadrilla implements Parcelable {
    private String tipo_cuadrilla;
    private String servicio;

    public Tipo_Cuadrilla(String tipo_cuadrilla, String servicio) {
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.servicio = servicio;
    }
    public Tipo_Cuadrilla() {

    }
    public String getTipo_cuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipo_cuadrilla(String tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tipo_cuadrilla);
        dest.writeString(this.servicio);
    }

    protected Tipo_Cuadrilla(Parcel in) {
        this.tipo_cuadrilla = in.readString();
        this.servicio = in.readString();
    }

    public static final Parcelable.Creator<Tipo_Cuadrilla> CREATOR = new Parcelable.Creator<Tipo_Cuadrilla>() {
        @Override
        public Tipo_Cuadrilla createFromParcel(Parcel source) {
            return new Tipo_Cuadrilla(source);
        }

        @Override
        public Tipo_Cuadrilla[] newArray(int size) {
            return new Tipo_Cuadrilla[size];
        }
    };
}
