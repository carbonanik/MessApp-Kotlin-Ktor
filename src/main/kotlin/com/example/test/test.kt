package com.example

import com.example.entity.User
import org.bson.types.ObjectId
import org.junit.Test
import kotlin.reflect.jvm.jvmName


//fun main() {
//    install { DependencyClass() }
//    install { TestClass(provider()) }
//    val testClass: TestClass = provider()
//    testClass.testPrint()
//}

val component = HashMap<String, Any>()

inline fun <reified T: Any> install(block: () -> T) {
    component[T::class.jvmName] = block()
}


inline fun <reified T> provider(): T {
    return component[T::class.jvmName] as T
}

class TestClass(private val dependencyClass: DependencyClass) {
    fun testPrint() {
        dependencyClass.print()
    }
}

class DependencyClass() {
    fun print() {
        println("printing from DependencyClass")
    }
}
