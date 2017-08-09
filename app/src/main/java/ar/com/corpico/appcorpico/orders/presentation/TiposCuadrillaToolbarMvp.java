package ar.com.corpico.appcorpico.orders.presentation;

import java.util.List;

/**
 * Contrato Mvp Toolbar Tipos de cuadrilla
 */

public interface TiposCuadrillaToolbarMvp {

    interface View{
        void showTiposCuadrilla(List<String> tiposCuadrilla);
        void setPresenter(Presenter presenter);
    }

    interface Presenter{
        void loadTiposCuadrilla(String servicio);
    }
}
