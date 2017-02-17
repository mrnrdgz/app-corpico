package ar.com.corpico.appcorpico.orders.domain.entity;

import java.util.ArrayList;

/**
 * Created by Administrador on 07/01/2017.
 */
public class Order {
    private String mNumero;
    private String mServicio;
    private String mSector;
    private String mTipo;
    private String mMotivo;
    private ArrayList<Etapa> mEtapa;
    private String mAsociado;
    private String mSuministro;
    private String mTitular;
    private String mDomicilio;
    private String mLocalidad;
    private String mAnexo;
    private String mLatitud;
    private String mLongitud;
    private String mObservacion;

    public Order(String Numero, String Servicio, String Sector, String Tipo, String Motivo, ArrayList<Etapa> Etapa, String Asociado,
                 String Suministro, String Titular, String Domicilio, String Localidad, String Anexo, String Latitud, String Longitud,
                 String Observacion) {
        this.mNumero = Numero;
        this.mServicio= Servicio;
        this.mSector = Sector;
        this.mTipo = Tipo;
        this.mMotivo= Motivo;
        this.mEtapa = Etapa;
        this.mAsociado = Asociado;
        this.mSuministro = Suministro;
        this.mTitular = Titular;
        this.mDomicilio = Domicilio;
        this.mLatitud=Localidad;
        this.mAnexo=Anexo;
        this.mLatitud = Latitud;
        this.mLongitud = Longitud;
        this.mObservacion = Observacion;
    }

    public String getNumero() {
        return mNumero;
    }

    public void setNumero(String Numero) {
        this.mNumero = Numero;
    }

    public String getSector() {
        return mSector;
    }

    public void setSector(String Sector) {
        this.mSector = Sector;
    }

    public String getTipo() {
        return mTipo;
    }

    public void setTipo(String Tipo) {
        this.mTipo = Tipo;
    }

    public String getAsociado() {
        return mAsociado;
    }

    public void setAsociado(String Asociado) {
        this.mAsociado = Asociado;
    }

    public String getSuministro() {
        return mSuministro;
    }

    public void setSuministro(String Suministro) {
        this.mSuministro = Suministro;
    }

    public String getTitular() {
        return mTitular;
    }

    public void setTitular(String Titular) {
        this.mTitular = Titular;
    }

    public String getDomicilio() {
        return mDomicilio;
    }

    public void setDomicilio(String Domicilio) {
        this.mDomicilio = Domicilio;
    }

    public String getLatitud() {
        return mLatitud;
    }

    public void setLatitud(String Latitud) {
        this.mLatitud = Latitud;
    }

    public String getLongitud() {
        return mLongitud;
    }

    public void setLongitud(String Longitud) {
        this.mLongitud = Longitud;
    }

    public String getObservacion() {
        return mObservacion;
    }

    public void setObservacion(String Observacion) {
        this.mObservacion = Observacion;
    }

    public ArrayList<Etapa> getEtapas() {
        return mEtapa;
    }

    public String getServicio() {
        return mServicio;
    }

    public void setServicio(String Servicio) {
        this.mServicio = Servicio;
    }

    public String getMotivo() {
        return mMotivo;
    }

    public void setMotivo(String Motivo) {
        this.mMotivo = Motivo;
    }

    public String getLocalidad() {
        return mLocalidad;
    }

    public void setLocalidad(String Localidad) {
        this.mLocalidad = Localidad;
    }

    public String getAnexo() {
        return mAnexo;
    }

    public void setAnexo(String Anexo) {
        this.mAnexo = Anexo;
    }

    public String toString(){
        return "Order={" +
                "Numero = '" + mNumero + '\'' +
                "Servicio = '" + mServicio + '\'' +
                "Sector = '" + mSector + '\'' +
                "Tipo = '" + mTipo + '\'' +
                "Motivo = '" + mMotivo + '\'' +
                "Etapa = '" + mEtapa + '\'' +
                "Asociado = '" + mAsociado + '\'' +
                "Suministro = '" + mSuministro + '\'' +
                "Titular = '" + mTitular + '\'' +
                "Domicilio = '" + mDomicilio + '\'' +
                "Localidad = '" + mLocalidad + '\'' +
                "Anexo = '" + mAnexo + '\'' +
                "Latitud = '" + mLatitud + '\'' +
                "Longitud = '" + mLongitud + '\'' +
                "Observacion = '" + mObservacion + '\'' +
                '}';
    }
}
