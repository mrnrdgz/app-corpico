package ar.com.corpico.appcorpico;

/**
 * Plantilla general para todos los casos de uso en la capa de dominio
 */

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue> {

    // Miembros de la clase
    private Q mRequestValues;
    private UseCaseCallback<P> mUseCaseCallback;



    public void setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }


    public abstract void execute(Q requestValues, UseCaseCallback callback);

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);
        void onError(String error);
    }
}
