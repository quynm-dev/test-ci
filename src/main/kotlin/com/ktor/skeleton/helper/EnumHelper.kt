package com.ktor.skeleton.helper

inline fun <reified T : Enum<T>, V> ((T) -> V).getKeyByValue(value: V): String? {
    return enumValues<T>().firstOrNull { this(it) == value }?.name
}