package com.kolyall.rxassist.utils;

import android.app.Activity;

import com.kolyall.rxassist.models.AssistConfig;
import com.kolyall.rxassist.models.AssistTransactionRequest;
import com.kolyall.rxassist.models.AssistUser;

import java.util.Calendar;

import by.kolyall.utils.DateUtils;
import ru.assisttech.sdk.AssistPaymentData;
import ru.assisttech.sdk.engine.AssistPayEngine;
import ru.assisttech.sdk.engine.PayEngineListener;

/**
 * Created by User on 21.04.2017.
 */

public class AssistManager {
    private final AssistConfig mAssistConfig;
    private final AssistPayEngine mEngine;

    public AssistManager(AssistConfig assistConfig, AssistPayEngine engine) {
        this.mAssistConfig = assistConfig;
        this.mEngine = engine;
    }

    public AssistConfig getAssistConfig() {
        return mAssistConfig;
    }

    public AssistPayEngine getEngine() {
        return mEngine;
    }

    public void makeCardPayWeb(Activity activity, AssistUser assistUser, PayEngineListener payEngineListener,
                               AssistTransactionRequest transaction) {
        mEngine.setServerURL(mAssistConfig.getAssistUrl());
        mEngine.setEngineListener(payEngineListener);
        AssistPaymentData data = new AssistPaymentData();
        data.setMerchantId(mAssistConfig.getAssistMerchantId());
        data.setLogin(mAssistConfig.getAssistLogin());
        data.setPassword(mAssistConfig.getAssistPass());
//        data.setCurrency(AssistPaymentData.Currency.BYN);
        data.setOrderCurrency(AssistPaymentData.Currency.BYN);

        data.setOrderNumber(transaction.getId());
        data.setOrderAmount(String.valueOf(transaction.getPrice()));

        data.setEmail(assistUser.getEmail());
        data.setFirstname(assistUser.getFirstName());
        data.setLastname(assistUser.getLastName());
        // TODO: 13.11.2017  seems not needed
//        data.setOrderComment(transaction.getMessage());
        mEngine.payWeb(activity, data, false);
    }

    public void makeRecurringCardPayWeb(Activity activity, AssistUser assistUser, PayEngineListener payEngineListener,
                                        AssistTransactionRequest transaction) {
        mEngine.setServerURL(mAssistConfig.getAssistUrl());
        mEngine.setEngineListener(payEngineListener);
        AssistPaymentData data = new AssistPaymentData();

        data.setMerchantId(mAssistConfig.getAssistMerchantId());
        data.setLogin(mAssistConfig.getAssistLogin());
        data.setPassword(mAssistConfig.getAssistPass());
//        data.setCurrency(AssistPaymentData.Currency.BYN);
        data.setOrderCurrency(AssistPaymentData.Currency.BYN);

        data.setCustomerNumber(mAssistConfig.getAssistMerchantId());

        data.setOrderNumber(transaction.getId());
        data.setOrderAmount(String.valueOf(transaction.getPrice()));

        data.setRecurringIndicator(true);
        data.setRecurringMinAmount("0.10");
        data.setRecurringMaxAmount("100");
        data.setRecurringPeriod(1);
        data.setRecurringMaxDate(DateUtils.getForwardYearDate());

        data.setEmail(assistUser.getEmail());
        data.setFirstname(assistUser.getFirstName());
        data.setLastname(assistUser.getLastName());

        mEngine.payWeb(activity, data, false);
    }

    @Deprecated
    public void makePayWeb(Activity activity, AssistUser assistUser, PayEngineListener payEngineListener) {
        mEngine.setServerURL(mAssistConfig.getAssistUrl());
        mEngine.setEngineListener(payEngineListener);
        AssistPaymentData data = new AssistPaymentData();
        data.setMerchantId(mAssistConfig.getAssistMerchantId());
        data.setCustomerNumber(mAssistConfig.getAssistMerchantId());
        data.setOrderCurrency(AssistPaymentData.Currency.BYN);

        data.setOrderAmount("1");
        data.setRecurringIndicator(true);
        data.setRecurringMinAmount("0.10");
        data.setRecurringMaxAmount("100");
        data.setRecurringPeriod(1);
        data.setRecurringMaxDate(DateUtils.getForwardYearDate());

        data.setOrderNumber("R" + assistUser.getId() + "." + (Calendar.getInstance()).getTimeInMillis());
        data.setEmail(assistUser.getEmail());
        data.setFirstname(assistUser.getFirstName());
        data.setLastname(assistUser.getLastName());

        mEngine.payWeb(activity, data, false);
    }
}
