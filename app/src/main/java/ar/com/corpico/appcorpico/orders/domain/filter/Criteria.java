package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrador on 08/01/2017.
 */
public interface Criteria <T>{
    List<T> match(List<T> itemsDeEntrada);
    Object toSql();
}
