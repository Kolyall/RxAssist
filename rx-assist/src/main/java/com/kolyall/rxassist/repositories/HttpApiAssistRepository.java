package com.kolyall.rxassist.repositories;

import android.app.Activity;

import com.kolyall.rxassist.models.AssistConfig;
import com.kolyall.rxassist.models.AssistTransactionRequest;
import com.utils.rxandroid.exceptions.FlowException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import ru.assisttech.sdk.AssistPaymentData;
import ru.assisttech.sdk.AssistResult;
import ru.assisttech.sdk.FieldName;
import ru.assisttech.sdk.engine.AssistPayEngine;
import ru.assisttech.sdk.processor.AssistProcessorListener;
import ru.assisttech.sdk.processor.AssistRecurrentPayProcessor;
import ru.assisttech.sdk.storage.AssistTransaction;

import static ru.assisttech.sdk.storage.AssistTransaction.PaymentMethod.CARD_TERMINAL;

/**
 * Created by User on 26.04.2017.
 */

public class HttpApiAssistRepository implements AssistRepository {
    public static final String TAG = HttpApiAssistRepository.class.getSimpleName();

    private AssistPayEngine mPayEngine;
    private AssistConfig mAssistConfig;

    public HttpApiAssistRepository(AssistConfig assistConfig, AssistPayEngine engine) {
        mPayEngine = engine;
        mAssistConfig = assistConfig;
    }

    @Override
    public Observable<AssistResult> recurrentCardPayment(String billNumber, AssistTransactionRequest transaction) {
        return Observable.unsafeCreate((Observer<? super AssistResult> subscriber) -> {
            AssistPaymentData data = new AssistPaymentData();
            data.setMerchantId(mAssistConfig.getAssistMerchantId());
            data.setLogin(mAssistConfig.getAssistLogin());
            data.setPassword(mAssistConfig.getAssistPass());

            data.setOrderNumber(transaction.getId());

            data.setOrderAmount(transaction.getPrice());
            data.setOrderCurrency(AssistPaymentData.Currency.BYN);

            //region v 1.4.2
            data.getFields().put(FieldName.BillNumber, billNumber);
            data.getFields().put(FieldName.Amount, transaction.getPrice());
            data.getFields().put(FieldName.Currency, AssistPaymentData.Currency.BYN.toString());
            //endregion

            AssistTransaction t = mPayEngine.createTransaction(
                    data.getMerchantID(),
                    data,
                    CARD_TERMINAL
            );

            AssistRecurrentPayProcessor processor = new AssistRecurrentPayProcessor(null, mPayEngine.buildServiceEnvironment(data));
            processor.setNetEngine(mPayEngine.getNetEngine());
            processor.setURL(mAssistConfig.getAssistRecUrl());
            processor.setTransaction(t);
            processor.setListener(new AssistProcessorListener() {
                @Override
                public void onFinished(long id, AssistResult result) {
                    subscriber.onNext(result);
                    subscriber.onComplete();
                }

                @Override
                public void onError(long id, String message) {
                    subscriber.onError(new RuntimeException(message));
                }

                @Override
                public void onNetworkError(long id, String message) {
                    subscriber.onError(new RuntimeException(message));
                }

                @Override
                public void onTerminated(long id) {
                    subscriber.onError(new RuntimeException());
                }

                @Override
                public void onActivityCreated(Activity activity) {

                }
            });
            processor.start(null);
        })
                .doOnNext(assistResult -> {
                    if (!assistResult.isPositive()) {
                        throw new PaymentNotApprovedException();
                    }
                })
                .filter(AssistResult::isPositive);
    }

    public static class PaymentNotApprovedException extends FlowException {
    }
}
