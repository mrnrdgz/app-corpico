package ar.com.corpico.appcorpico.orders.domain.entity;
/**
 * Created by Administrador on 07/01/2017.
 */
public class Order {
    private String mFecha;
    private String mNumero;
    private String mSector;
    private String mTipo;
    private String mEstado;
    private String mAsociado;
    private String mSuministro;
    private String mTitular;
    private String mDomicilio;
    private String mLatitud;
    private String mLongitud;
    private String mObservacion;

    public Order(String Fecha,String Numero, String Sector, String Tipo, String Estado, String Asociado,
                 String Suministro, String Titular, String Domicilio, String Latitud, String Longitud, String Observacion) {
        this.mFecha= Fecha;
        this.mNumero = Numero;
        this.mSector = Sector;
        this.mTipo = Tipo;
        this.mEstado = Estado;
        this.mAsociado = Asociado;
        this.mSuministro = Suministro;
        this.mTitular = Titular;
        this.mDomicilio = Domicilio;
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

    public String getmEstado() {
        return mEstado;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String Fecha) {
        this.mFecha = Fecha;
    }

    public String toString(){
        return "Order={" +
                "Fecha ='" + mFecha + '\'' +
                "Numero = '" + mNumero + '\'' +
                "Sector = '" + mSector + '\'' +
                "Tipo = '" + mTipo + '\'' +
                "Estado = '" + mEstado + '\'' +
                "Asociado = '" + mAsociado + '\'' +
                "Suministro = '" + mSuministro + '\'' +
                "Titular = '" + mTitular + '\'' +
                "Domicilio = '" + mDomicilio + '\'' +
                "Latitud = '" + mLatitud + '\'' +
                "Longitud = '" + mLongitud + '\'' +
                "Observacion = '" + mObservacion + '\'' +
                '}';
    }
}
