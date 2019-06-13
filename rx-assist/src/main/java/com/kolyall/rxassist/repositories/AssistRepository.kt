package com.kolyall.rxassist.repositories

import com.kolyall.rxassist.models.AssistTransactionRequest

import io.reactivex.Observable
import ru.assisttech.sdk.AssistResult

/**
 * Created by User on 26.04.2017.
 */

interface AssistRepository {
    fun recurrentCardPayment(billNumber: String, transaction: AssistTransactionRequest): Observable<AssistResult>
}
