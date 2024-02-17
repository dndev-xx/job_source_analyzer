package org.example.util

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ConfigFile(val fileName: String = "")
