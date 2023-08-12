package com.dev.demoapp.dev.utils

object Validate {

    fun checkNumberPerson(id: String): Boolean {
        return id.matches(Regex("[0-9]+"))
    }

}
