package com.konifar.materialcat.presentation

import org.greenrobot.eventbus.EventBus

interface UseCase {

    fun eventBus(): EventBus

    fun destroy()

}
