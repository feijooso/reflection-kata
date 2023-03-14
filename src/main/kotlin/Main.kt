package com.kata

import com.eclipsesource.json.JsonObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/*
1. Imprimir info clase Tank:
- Nombre
- Package
- Annotations
- Imprimir constructor y parametros
- Por cada propiedad y metodos:
- Imprimir nombre, parametros, tipo de retorno, visibilidad
- Imprimir la misma informacion de su clase padre*/

fun main() {
/*    println(Tank::class.simpleName)
    println(Tank::class.java.packageName)
    println(Tank::class.findAnnotation())
    println(Tank::class.constructors)*/

    changePrivateParameter()
    callPrivateMethod()
    callConstructorFromClassName()

    println(serialize(Position(1,1)))

}

fun serialize(obj: Any): String {
    val json = JsonObject()
    obj::class.memberProperties.forEach { json.add(it.name, it.getter.call(obj).toString()) }
    return json.toString()
}

private fun callConstructorFromClassName() {
    val c = Class.forName("com.kata.Tank").kotlin
    val instance = c.primaryConstructor!!.call("name", Position(0, 0))
    println(instance is Tank)
}

private fun callPrivateMethod() {
    val tanke = Tank("tanke", Position(0, 0))
    val c = Tank::class.java.declaredMethods[1]
    c.invoke(tanke)
}

private fun changePrivateParameter() {
    val tanke = Tank("tanke", Position(0, 0))
    val c = Tank::class.java.getDeclaredField("isShooting")
    c.isAccessible = true
    c.set(tanke, true)
    c.isAccessible = false
    println(tanke.isShooting)
}
