package ar.com.corpico.appcorpico.orders.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrador on 21/05/2017.
 */

public class Tipo_Trabajo implements Parcelable {
    private String tipo_cuadrilla;
    private String tipo_trabajo;
    private String servicio;

    public Tipo_Trabajo(String tipo_cuadrilla, String tipo_trabajo,String servicio) {
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.tipo_trabajo = tipo_trabajo;
        this.servicio= servicio;
    }
    public String getServicio() {
        return servicio;
    }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    public String getTipoCuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipoCuadrilla(String tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public String getTipoTrabajo() {
        return tipo_trabajo;
    }

    public void setTipoTrabajo(String tipo_trabajo) {
        this.tipo_trabajo = tipo_trabajo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tipo_cuadrilla);
        dest.writeString(this.tipo_trabajo);
        dest.writeString(this.servicio);
    }

    protected Tipo_Trabajo(Parcel in) {
        this.tipo_cuadrilla = in.readString();
        this.tipo_trabajo = in.readString();
        this.servicio = in.readString();
    }

    public static final Parcelable.Creator<Tipo_Trabajo> CREATOR = new Parcelable.Creator<Tipo_Trabajo>() {
        @Override
        public Tipo_Trabajo createFromParcel(Parcel source) {
            return new Tipo_Trabajo(source);
        }

        @Override
        public Tipo_Trabajo[] newArray(int size) {
            return new Tipo_Trabajo[size];
        }
    };
}
