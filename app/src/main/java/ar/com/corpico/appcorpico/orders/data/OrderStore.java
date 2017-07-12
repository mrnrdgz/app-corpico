package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface OrderStore {
    void getOrders(GetCallback callback, Specification filter);

    interface GetCallback{
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    void addOrderEtape(String estado, ArrayList<String> numero);
    void getCuadrillaxTipo(GetCuadrillaxTipoStoreCallBack callback, Criteria filter);
    interface GetCuadrillaxTipoStoreCallBack{
        void onSuccess(List<Tipo_Trabajo> tipoCuadrilla);
        void onError(String error);
    }
    void getTipoCuadrilla(GetTipoCuadrillaStoreCallBack callback, Criteria filter);
    interface GetTipoCuadrillaStoreCallBack{
        void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onError(String error);
    }
    void getTipoTrabajo(GetTipoTrabajoStoreCallBack callback, Criteria filter);
    interface GetTipoTrabajoStoreCallBack{
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }
    void getZona(GetZonaStoreCallBack callback, Criteria filter);
    interface GetZonaStoreCallBack{
        void onSuccess(List<Zona> zona);
        void onError(String error);
    }
}
