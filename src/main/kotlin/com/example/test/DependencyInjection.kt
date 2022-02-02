package com.example.test

import kotlin.reflect.KClass


//fun main() {
//
//    Module {
//        factory {
//            DependencyClass()
//        }
//
//        single {
//            SingleTestClass(inject())
//        }
//
//        factory {
//            TestClass(inject())
//        }
//    }
//
//
//    val testClass: TestClass = inject()
//    testClass.testPrint()
//
//    val single: SingleTestClass = inject()
//    single.whatIsI()
//    single.increaseI()
//
//    val single2: SingleTestClass = inject()
//    single2.whatIsI()
//}

object Module {

    operator fun invoke(block: Module.() -> Unit) {
        this.block()
    }

    private val map = HashMap<KClass<*>, Any>()

    inline fun <reified T : Any> single(block: () -> T) {
        putInMap(T::class, block())
    }

    inline fun <reified T : Any> factory(noinline build: () -> T) {
        putInMap(T::class, build)
    }

    fun <T : Any>putInMap(kClass: KClass<T>, product: Any) {
        if (map.containsKey(kClass)) throw AlreadyExistInMap(kClass)
        else map[kClass] = product
    }

    fun getFromMap(kClass: KClass<*>) = map[kClass] ?: throw DoesNotExistInMap(kClass)
}


@Suppress("UNCHECKED_CAST")
inline fun <reified T> inject(): T {
    val invocable = Module.getFromMap(T::class)
    return ((invocable as? (() -> Any))?.invoke() ?: invocable) as T
}

class AlreadyExistInMap(
    clazz: KClass<*>
) : RuntimeException("Class:${clazz.simpleName} Already declared in module")

class DoesNotExistInMap(
    clazz: KClass<*>
): RuntimeException("Class:${clazz.simpleName} Never declared in module")






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

class SingleTestClass(val dependencyClass: DependencyClass) {
    private var i = 0
    fun increaseI() = i++
    fun whatIsI() = println(i)
}