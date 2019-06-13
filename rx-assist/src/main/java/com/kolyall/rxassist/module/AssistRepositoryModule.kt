package com.kolyall.rxassist.module

import android.content.Context
import com.kolyall.rxassist.models.AssistConfig
import com.kolyall.rxassist.repositories.AssistRepository
import com.kolyall.rxassist.repositories.HttpApiAssistRepository
import com.kolyall.rxassist.utils.AssistManager
import dagger.Module
import dagger.Provides
import ru.assisttech.sdk.engine.AssistPayEngine
import javax.inject.Singleton

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
class AssistRepositoryModule {
    @Provides
    @Singleton
    fun provideAssistCustomerPayEngine(context: Context): AssistPayEngine {
        return AssistPayEngine.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideRxAssistRecurrent(assistConfig: AssistConfig, engine: AssistPayEngine): AssistRepository {
        return HttpApiAssistRepository(assistConfig, engine)
    }

    @Provides
    @Singleton
    fun provideAssistManager(assistConfig: AssistConfig, engine: AssistPayEngine): AssistManager {
        return AssistManager(assistConfig, engine)
    }
}