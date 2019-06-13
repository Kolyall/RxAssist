package com.kolyall.rxassist.utils;

import java.util.Map;

import io.reactivex.functions.Function;
import ru.assisttech.sdk.AssistResult;

/**
 * Created by User on 26.04.2017.
 */

public class MapHashMapToAssistResult implements Function<Map<String, String>, AssistResult> {

    @Override
    public AssistResult apply(Map<String, String> responseFields) {
        AssistResult result = new AssistResult();
        if (!responseFields.get("responsecode").isEmpty()) {
            /* Success */
            result.setApprovalCode(responseFields.get("approvalcode"));
            result.setBillNumber(responseFields.get("billnumber"));
            result.setExtra(responseFields.get("responsecode") + " " + responseFields.get("customermessage"));
            result.setMeantypeName(responseFields.get("meantypename"));
            result.setMeanNumber(responseFields.get("meannumber"));
            result.setCardholder(responseFields.get("cardholder"));
            result.setCardExpirationDate(responseFields.get("cardexpirationdate"));

            if ("AS000".equalsIgnoreCase(responseFields.get("responsecode"))) {
                result.setOrderState(AssistResult.OrderState.APPROVED);
            } else {
                result.setOrderState(AssistResult.OrderState.DECLINED);
            }
        } else {
            /* Fault */
            result.setExtra(responseFields.get("faultcode") + ": " + responseFields.get("faultstring"));
        }
        return result;
    }
}
