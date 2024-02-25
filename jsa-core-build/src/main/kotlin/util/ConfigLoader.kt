package org.example.util

import java.io.IOException
import java.io.InputStream
import java.util.*

object ConfigLoader {

    private val config = Properties()

    init {
        loadConfig()
    }

    private fun loadConfig() {
        val configFileAnnotation = getCallingClassAnnotation()
        val fileName = if (configFileAnnotation?.fileName.isNullOrEmpty()) "app.properties" else configFileAnnotation?.fileName
        val configFile = getConfigFile(fileName)

        configFile?.let {
            try {
                config.load(it)
                return
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val yamlFileName = "$fileName.yaml"
        val yamlConfigFile = getConfigFile(yamlFileName)

        yamlConfigFile?.let {
            try {
                config.load(it)
                return
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val propertiesFileName = "$fileName.properties"
        val propertiesConfigFile = getConfigFile(propertiesFileName)

        propertiesConfigFile?.let {
            try {
                config.load(it)
                return
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        throw IllegalArgumentException("Config file '$fileName' not found")
    }

    private fun getCallingClassAnnotation(): ConfigFile? {
        val stackTrace = Thread.currentThread().stackTrace
        for (element in stackTrace) {
            if (element.methodName == "main") {
                val className = element.className
                val callingClass = Class.forName(className)
                return callingClass.getAnnotation(ConfigFile::class.java)
            }
        }
        return null
    }

    private fun getConfigFile(fileName: String?): InputStream? {
        return fileName?.let {
            this::class.java.classLoader.getResourceAsStream(it)
        }
    }

    fun getProperty(key: String): String? {
        return config.getProperty(key)
    }
}
