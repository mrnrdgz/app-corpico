package ar.com.corpico.appcorpico.orders.domain.usecase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;

/**
 * Created by sistemas on 21/09/2017.
 */

public class AddTurno extends UseCase<AddTurno.RequestValues, AddTurno.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public AddTurno(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(AddTurno.RequestValues requestValues, final UseCaseCallback callb) {

        mOrdersRepository.addTurno(requestValues.getOrderNumber(),requestValues.getTurno());
        callb.onSuccess(new AddTurno.ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String mNumero;
        private DateTime mTurno;


        public RequestValues() {
        }

        public RequestValues(String numero, DateTime turno) {
            mNumero = numero;
            mTurno = turno;
            // Validar lógica
        }

        public String getOrderNumber() {
            return mNumero;
        }

        public DateTime getTurno() {
            return mTurno;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {


    }
}