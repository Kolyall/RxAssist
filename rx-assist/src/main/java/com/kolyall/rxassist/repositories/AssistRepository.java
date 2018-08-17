package com.kolyall.rxassist.repositories;

import com.assist.api.models.AssistTransactionRequest;

import ru.assisttech.sdk.AssistResult;
import rx.Observable;

/**
 * Created by User on 26.04.2017.
 */

public interface AssistRepository {
    Observable<AssistResult> recurrentCardPayment(String billNumber, AssistTransactionRequest transaction);
}
