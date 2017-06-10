package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

/**
 * Created by User on 10/06/2017.
 */

abstract class CompositeSpec<T> implements Specification<T>{

   public abstract boolean isSatisfiedBy(T item);

    public Specification<T> and(Specification<T> other){
        return new AndSpec(this, other);
    }

    public Specification<T> or(Specification<T> other){
        return new OrSpec(this, other);
    }


    public class AndSpec extends CompositeSpec<T>{

        Specification<T> spec1;
        Specification<T> spec2;

        public AndSpec(Specification<T> spec1, Specification<T> spec2) {
            this.spec1 = spec1;
            this.spec2 = spec2;
        }

        @Override
        public boolean isSatisfiedBy(T item) {
            return spec1.isSatisfiedBy(item) && spec2.isSatisfiedBy(item);
        }
    }

    public class OrSpec extends CompositeSpec<T>{

        Specification<T> spec1;
        Specification<T> spec2;

        public OrSpec(Specification<T> spec1, Specification<T> spec2) {
            this.spec1 = spec1;
            this.spec2 = spec2;
        }

        @Override
        public boolean isSatisfiedBy(T item) {
            return spec1.isSatisfiedBy(item) || spec2.isSatisfiedBy(item);
        }
    }
}
