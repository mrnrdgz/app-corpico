package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

/**
 * Created by User on 10/06/2017.
 */

public interface Specification<T> {
    boolean isSatisfiedBy(T item);
    Specification<T> and(Specification<T> specification);
    Specification<T> or(Specification<T> specification);
}