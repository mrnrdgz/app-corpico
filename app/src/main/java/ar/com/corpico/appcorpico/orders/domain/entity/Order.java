package ar.com.corpico.appcorpico.orders.domain.entity;
/**
 * Created by Administrador on 07/01/2017.
 */
public class Order {
    private String mFecha;
    private String mNumero;
    private String mServicio;
    private String mSector;
    private String mTipo;
    private String mMotivo;
    private String mEstado;
    private String mAsociado;
    private String mSuministro;
    private String mTitular;
    private String mDomicilio;
    private String mLocalidad;
    private String mAnexo;
    private String mLatitud;
    private String mLongitud;
    private String mObservacionAlOperario;
    private String mObservacionDelOperario;

    public Order(String Fecha,String Numero, String Servicio, String Sector, String Tipo, String Motivo, String Estado, String Asociado,
                 String Suministro, String Titular, String Domicilio, String Localidad, String Anexo, String Latitud, String Longitud,
                 String ObservacionAlOperario,String ObservacionDelOperario) {
        this.mFecha= Fecha;
        this.mNumero = Numero;
        this.mServicio= Servicio;
        this.mSector = Sector;
        this.mTipo = Tipo;
        this.mMotivo= Motivo;
        this.mEstado = Estado;
        this.mAsociado = Asociado;
        this.mSuministro = Suministro;
        this.mTitular = Titular;
        this.mDomicilio = Domicilio;
        this.mLatitud=Localidad;
        this.mAnexo=Anexo;
        this.mLatitud = Latitud;
        this.mLongitud = Longitud;
        this.mObservacionAlOperario = ObservacionAlOperario;
        this.mObservacionAlOperario = ObservacionDelOperario;
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

    public String getObservacionAlOperario() {
        return mObservacionAlOperario;
    }

    public void setObservacionAlOpearrio(String ObservacionAlOperario) {
        this.mObservacionAlOperario = ObservacionAlOperario;
    }

    public String getEstado() {
        return mEstado;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String Fecha) {
        this.mFecha = Fecha;
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

    public String getObservacionDelOperario() {
        return mObservacionDelOperario;
    }

    public void setObservacionDelOperario(String ObservacionDelOperario) {
        this.mObservacionDelOperario = ObservacionDelOperario;
    }

    public String toString(){
        return "Order={" +
                "Fecha ='" + mFecha + '\'' +
                "Numero = '" + mNumero + '\'' +
                "Servicio = '" + mServicio + '\'' +
                "Sector = '" + mSector + '\'' +
                "Tipo = '" + mTipo + '\'' +
                "Motivo = '" + mMotivo + '\'' +
                "Estado = '" + mEstado + '\'' +
                "Asociado = '" + mAsociado + '\'' +
                "Suministro = '" + mSuministro + '\'' +
                "Titular = '" + mTitular + '\'' +
                "Domicilio = '" + mDomicilio + '\'' +
                "Localidad = '" + mLocalidad + '\'' +
                "Anexo = '" + mAnexo + '\'' +
                "Latitud = '" + mLatitud + '\'' +
                "Longitud = '" + mLongitud + '\'' +
                "ObservacionAlOperario = '" + mObservacionAlOperario + '\'' +
                "ObservacionDelOperario = '" + mObservacionDelOperario + '\'' +
                '}';
    }
}
