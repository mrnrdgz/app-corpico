package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.corpico.appcorpico.R;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by sistemas on 17/04/2017.
 */

public class AsignarAConexiones extends DialogFragment {
    private ArrayList<String> mNumeroOT = new ArrayList<>();
    private List<Tipo_Trabajo> mTipoCuadrillaList = new ArrayList<Tipo_Trabajo>();
    private ListView mCuadrillaList;
    private CuadrillaAdapter mCuadrillaAdapter;
    private RadioButton mCuadrillaRadioButton;
    private String mCuadrilla;
    public interface OnAsignarAConexionesListener {
        void onPossitiveButtonAsignarClick(String cuadrilla, ArrayList<String> numero);// Eventos Botón Positivo
        //LO DEJO X SI MAS ADELANTE LO TENGO QUE DEFINIR
        void onNegativeButtonAsignarClick();// Eventos Botón Negativo
    }

    OnAsignarAConexionesListener listener;

    public AsignarAConexiones() {
    }
    //TODO: HAGO CON ESTE ARGUNTO PARA PROBAR...LUEGO EL ARGUMENTO CREO Q
    // DEBERIA SER ORDER PARA CUANDO USE MAS DE UNA SELLECCION
    public static AsignarAConexiones newInstance(List<String> tipocuadrilla, ArrayList<String> numero) {
        AsignarAConexiones f = new AsignarAConexiones();
        /*ArrayList<Tipo_Trabajo> tc = new ArrayList<>();

        for(int i=0; i< tipocuadrilla.size(); i++){
            tc.add(tipocuadrilla.get(i));
        }*/

        Bundle args = new Bundle();
        args.putString("NUMERO", numero.get(0));
        //args.putParcelableArrayList("TIPO_CUADRILLA",tc);
        args.putString("TIPO_CUADRILLA", numero.get(0));

        f.setArguments(args);

        return f;

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createAsignarAConexiones();

    }

    /**
     * Crea un diálogo con una lista de radios
     *
     * @return Diálogo
     */
    public AlertDialog createAsignarAConexiones() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //ArrayList<Tipo_Trabajo> tc = new ArrayList<>();
        mNumeroOT.add(getArguments().getString("NUMERO"));

        /*tc.addAll(getArguments().<Tipo_Trabajo>getParcelableArrayList("TIPO_CUADRILLA"));

        for(int i=0; i< tc.size(); i++){
            mTipoCuadrillaList.add(tc.get(i));
        }*/

        android.view.View v = inflater.inflate(R.layout.dialog_asignar_conexiones, null);
        builder.setView(v);
        builder.setTitle("Asignar a cuadrilla");

        mCuadrillaList = (ListView) v.findViewById(R.id.cuadrillaslist);
        mCuadrillaRadioButton = (RadioButton) v.findViewById(R.id.CuadrillaradioButton);

        mCuadrillaAdapter = new CuadrillaAdapter(getActivity(),new ArrayList<Tipo_Trabajo>(0));
        mCuadrillaList.setAdapter(mCuadrillaAdapter);

        mCuadrillaAdapter.clear();
        mCuadrillaAdapter.addAll(mTipoCuadrillaList);
        mCuadrillaAdapter.notifyDataSetChanged();

        Button asignar = (Button) v.findViewById(R.id.aplicar_boton);
        Button cancelar = (Button) v.findViewById(R.id.cancelar_boton);

        asignar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        if (mCuadrillaRadioButton != null && mCuadrillaRadioButton.isChecked()){
                            listener.onPossitiveButtonAsignarClick(mCuadrilla,mNumeroOT);
                            dismiss();
                        }else{
                            Toast.makeText(getActivity(), "Debe seleccionar una cuadrilla", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        cancelar.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        dismiss();
                    }
                }
        );
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnAsignarAConexionesListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }
}

