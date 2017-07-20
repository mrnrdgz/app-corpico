package ar.com.corpico.appcorpico.orders.domain.entity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.R.attr.order;


/**
 * Created by Administrador on 07/01/2017.
 */
public class Order {
    private String mFechaSolicitud;
    private String mNumero;
    private String mZona;
    private String mTipo_Trabajo;
    private String mMotivo;
    private ArrayList<Etapa> mEtapas;
    private String mAsociado;
    private String mSuministro;
    private String mTitular;
    private String mDomicilio;
    private String mLocalidad;
    private String mAnexo;
    private String mLatitud;
    private String mLongitud;
    private String mObservacion;
    private String mState;
    private Etapa mCurrentEtapa;

    public Order(String FechaSolicitud, String Numero, String Zona, String Tipo_Trabajo, String Motivo, ArrayList<Etapa> Etapas, String Asociado,
                 String Suministro, String Titular, String Domicilio, String Localidad, String Anexo, String Latitud, String Longitud,
                 String Observacion) {
        this.mFechaSolicitud = FechaSolicitud;
        this.mNumero = Numero;
        this.mZona = Zona;
        this.mTipo_Trabajo = Tipo_Trabajo;
        this.mMotivo= Motivo;
        this.mEtapas = Etapas;
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

    public String getmFechaSolicitud() {
        return mFechaSolicitud;
    }

    public void setmFechaSolicitud(String mFechaSolicitud) {
        this.mFechaSolicitud = mFechaSolicitud;
    }

    public String getNumero() {
        return mNumero;
    }

    public void setNumero(String Numero) {
        this.mNumero = Numero;
    }

    public String getZona() {
        return mZona;
    }

    public void setZona(String Zona) {
        this.mZona = Zona;
    }

    public String getTipo_Trabajo() {
        return mTipo_Trabajo;
    }

    public void setTipo_Trabajo(String Tipo_Trabajo) {
        this.mTipo_Trabajo = Tipo_Trabajo;
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
        return mEtapas;
    }

    public void setEtapas(ArrayList<Etapa> Etapas){
        this.mEtapas =  Etapas;
    }

    public void addEtapas(Etapa etapa) {
        // Validar contenido
        mEtapas.add(etapa);
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
    public String getCurrentState(ArrayList etapas){
        ArrayList<Etapa> etapasOrdenadas = sortEtapas(etapas);
        mState= etapasOrdenadas.get(etapasOrdenadas.size()-1).getEstado();
        return mState;
    }
    public Etapa getCurrentEtapa(ArrayList etapas){
        ArrayList<Etapa> etapasOrdenadas = sortEtapas(etapas);
        mCurrentEtapa = etapasOrdenadas.get(etapasOrdenadas.size()-1);
        return  mCurrentEtapa;
    }
    public ArrayList<Etapa> sortEtapas(ArrayList etapas) {
        Collections.sort(etapas, new Comparator<Etapa>() {
            @Override
            public int compare(Etapa p1, Etapa p2) {
                return new DateTime(p1.getFecha()).compareTo(new DateTime(p2.getFecha()));
            }
        });
        return etapas;
    }

    public String toString(){
        return "Order={" +
                "Fecha_Solicitud = '" + mFechaSolicitud + '\'' +
                "Numero = '" + mNumero + '\'' +
                "Zona = '" + mZona + '\'' +
                "Tipo_Trabajo = '" + mTipo_Trabajo + '\'' +
                "Motivo = '" + mMotivo + '\'' +
                "Etapa = '" + mEtapas + '\'' +
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
