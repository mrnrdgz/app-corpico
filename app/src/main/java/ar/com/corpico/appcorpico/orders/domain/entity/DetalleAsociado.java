package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by sistemas on 13/09/2017.
 */

public class DetalleAsociado {
    private String mAsociado;
    private String mSuministro;
    private String mTitular;
    private String mDomicilio;
    private String mLocalidad;
    private String mAnexo;
    private String mZona;
    private String mLatitud;
    private String mLongitud;
    private Integer mGrupo;
    private Integer mRuta;
    private Integer mOrden;
    private String mTipo_Usuario;
    private String mTarifa;
    private String mPotencia_Declarada;

    public DetalleAsociado(String Asociado, String Suministro, String Titular,
                           String Domicilio, String Localidad, String Anexo,
                           String Zona, String Latitud, String Longitud, Integer Grupo,
                           Integer Ruta, Integer Orden,
                           String Tipo_Usuario, String Tarifa, String Potencia_Declarada) {
        this.mAsociado = Asociado;
        this.mSuministro = Suministro;
        this.mTitular = Titular;
        this.mDomicilio = Domicilio;
        this.mLocalidad = Localidad;
        this.mAnexo = Anexo;
        this.mZona = Zona;
        this.mLatitud = Latitud;
        this.mLongitud = Longitud;
        this.mGrupo= Grupo;
        this.mRuta = Ruta;
        this.mOrden = Orden;
        this.mTipo_Usuario = Tipo_Usuario;
        this.mTarifa = Tarifa;
        this.mPotencia_Declarada = Potencia_Declarada;
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

    public void setmTitular(String Titular) {
        this.mTitular = Titular;
    }

    public String getDomicilio() {
        return mDomicilio;
    }

    public void setDomicilio(String Domicilio) {
        this.mDomicilio = Domicilio;
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

    public String getZona() {
        return mZona;
    }

    public void setZona(String Zona) {
        this.mZona = mZona;
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

    public Integer getGrupo() {
        return mGrupo;
    }

    public void setGrupo(Integer Grupo) {
        this.mGrupo = Grupo;
    }

    public Integer getRuta() {
        return mRuta;
    }

    public void setRuta(Integer Ruta) {
        this.mRuta = Ruta;
    }

    public Integer getOrden() {
        return mOrden;
    }

    public void setOrden(Integer Orden) {
        this.mOrden = Orden;
    }

    public String getTipo_Usuario() {
        return mTipo_Usuario;
    }

    public void setTipo_Usuario(String Tipo_Usuario) {
        this.mTipo_Usuario = Tipo_Usuario;
    }

    public String getTarifa() {
        return mTarifa;
    }

    public void setTarifa(String Tarifa) {
        this.mTarifa = Tarifa;
    }

    public String getPotencia_Declarada() {
        return mPotencia_Declarada;
    }

    public void setPotencia_Declarada(String Potencia_Declarada) {
        this.mPotencia_Declarada = Potencia_Declarada;
    }
}
