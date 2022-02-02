package com.example.di

import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.ktor.ext.Koin

fun Application.configureKoin(){
    install(Koin){
        PrintLogger(level = Level.DEBUG)
        modules(mainModule)
    }
}