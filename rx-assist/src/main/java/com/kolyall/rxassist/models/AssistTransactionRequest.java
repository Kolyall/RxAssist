package com.kolyall.rxassist.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Nick Unuchek on 13.11.2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AssistTransactionRequest {
    String id;
    String price;
}
