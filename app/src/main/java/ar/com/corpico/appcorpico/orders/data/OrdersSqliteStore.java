package ar.com.corpico.appcorpico.orders.data;

import org.joda.time.DateTime;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersSqliteStore implements OrderStore {
    @Override
    public void getOrders(GetCallback callback, Query query) {

    }

    @Override
    public void addOrderEtape( String estado,ArrayList<String> numero, String observacion) {

    }

    @Override
    public void addTurno(String numero, DateTime turno) {

    }

    @Override
    public void getCuadrillaxTipo(GetCuadrillaxTipoStoreCallBack callback, Criteria filter) {

    }

    @Override
    public void getTipoCuadrilla(GetTipoCuadrillaStoreCallBack callback, Criteria filter) {

    }
    @Override
    public void getTipoTrabajo(GetTipoTrabajoStoreCallBack callback, Criteria filter) {

    }

    @Override
    public void getZona(GetZonaStoreCallBack callback) {

    }
}
