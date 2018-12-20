package com.pranav.user.leadtracking.controller

interface ProcessResponcceInterphase<T> {
    fun processResponce(responce: T)
    fun processResponceError(responce: Any?)
}