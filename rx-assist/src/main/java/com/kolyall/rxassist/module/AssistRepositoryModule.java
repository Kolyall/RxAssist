package com.kolyall.rxassist.module;

import android.content.Context;

import com.assist.api.models.AssistConfig;
import com.assist.api.service.repositories.AssistRepository;
import com.assist.api.service.repositories.HttpApiAssistRepository;
import com.assist.api.utils.AssistManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.assisttech.sdk.engine.AssistPayEngine;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class AssistRepositoryModule {
    @Provides
    @Singleton
    public AssistPayEngine provideAssistCustomerPayEngine(Context context) {
        return AssistPayEngine.getInstance(context);
    }

    @Provides
    @Singleton
    public AssistRepository provideRxAssistRecurrent(AssistConfig assistConfig, AssistPayEngine engine) {
        return new HttpApiAssistRepository(assistConfig, engine);
    }

    @Provides
    @Singleton
    public AssistManager provideAssistManager(AssistConfig assistConfig, AssistPayEngine engine) {
        return new AssistManager(assistConfig, engine);
    }
}