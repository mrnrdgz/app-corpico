package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by Administrador on 26/07/2017.
 */

public class DetalleMedidor {

    private String mNumero;
    private String mMarca;
    private String mModelo;
    private String mFactorM;
    private String mCapacidad;
    private String mTension;

    public DetalleMedidor(String mNumero, String mMarca, String mModelo, String mFactorM, String mCapacidad, String mTension) {
        this.mNumero = mNumero;
        this.mMarca = mMarca;
        this.mModelo = mModelo;
        this.mFactorM = mFactorM;
        this.mCapacidad = mCapacidad;
        this.mTension = mTension;
    }

    public String getmNumero() {
        return mNumero;
    }

    public String getmMarca() {
        return mMarca;
    }

    public String getmModelo() {
        return mModelo;
    }

    public String getmFactorM() {
        return mFactorM;
    }

    public String getmCapacidad() {
        return mCapacidad;
    }

    public String getmTension() {
        return mTension;
    }
}
