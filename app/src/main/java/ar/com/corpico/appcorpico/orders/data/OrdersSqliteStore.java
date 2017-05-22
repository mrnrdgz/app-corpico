package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersSqliteStore implements OrderStore {
    @Override
    public void getOrders(GetCallback callback, Criteria filter) {

    }

    @Override
    public void addOrderEtape( String estado,ArrayList<String> numero) {

    }

    @Override
    public void getTypes(GetTypeStoreCallBack callback, Criteria filter) {

    }

    @Override
    public void getCuadrillas(GetCuadrillaStoreCallBack callback, Criteria filter) {

    }
}
