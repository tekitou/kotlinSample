package org.tekitou.kotlin.kotlinSample.model

class User(name:String?) {
    val name = name
    override fun toString(): String {
        return "User(name=$name)"
    }
}