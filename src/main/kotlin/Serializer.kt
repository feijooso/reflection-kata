package com.kata

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import com.eclipsesource.json.JsonValue
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

class Serializer {
    fun serialize(obj: Any) = getJson(obj).toString()

    private fun getJson(obj: Any): JsonValue {
        if (obj is String) return Json.value(obj)
        if (obj is Int) return Json.value(obj)
        if (obj is Float) return Json.value(obj)
        val json = JsonObject()
        obj::class.memberProperties.forEach { json.add(it.name, getJson(it.getter.call(obj)!!)) }
        return json
    }

    fun deserialize(string: String, clazz: KClass<*>): Any {
        val jsonValue = Json.parse(string)
        return fromJson(jsonValue, clazz)
    }

    private fun fromJson(json: JsonValue, clazz: KClass<*>): Any {
        if (clazz == String::class) return json.asString()
        if (clazz == Float::class) return json.asFloat()
        if (clazz == Int::class) return json.asInt()
        val jsonObject = json.asObject()
        val parameters = clazz.primaryConstructor!!.parameters
        val constructorParameters = mutableMapOf<KParameter, Any?>()
        for (param in parameters) {
            val paramValue = fromJson(jsonObject[param.name], param.type.classifier as KClass<*>)
            constructorParameters[param] = paramValue
        }
        return clazz.primaryConstructor!!.callBy(constructorParameters)
    }
}
