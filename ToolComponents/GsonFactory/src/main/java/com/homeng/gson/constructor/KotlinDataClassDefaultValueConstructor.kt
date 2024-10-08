package com.homeng.gson.constructor

import com.google.gson.Gson
import com.google.gson.internal.ObjectConstructor
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaType

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/GsonFactory
 *    time   : 2023/11/25
 *    desc   : Kotlin Data Class 创建器，用于处理反射创建 data class 类导致默认值不生效的问题
 */
class KotlinDataClassDefaultValueConstructor<T : Any>(private val mainConstructor: MainConstructor, private val gson: Gson, private val rawType: Class<*>) :  ObjectConstructor<T?> {

    companion object {
        /** 构造函数的字段的默认值 */
        private val ABSENT_VALUE = Any()
    }

    override fun construct(): T? {
        val rawTypeKotlin = rawType.kotlin
        // 寻找 Kotlin 主构造函数，如果找不到就不往下执行
        val constructor = rawTypeKotlin.primaryConstructor ?: return null
        constructor.isAccessible = true

        // 是否初始化构造函数中的全部参数
        var initializedAllParameters = true
        val constructorSize = constructor.parameters.size
        val values = Array<Any?>(constructorSize) { ABSENT_VALUE }

        for (i in 0 until constructorSize) {
            if (values[i] !== ABSENT_VALUE) {
                continue
            }

            val parameter = constructor.parameters[i]

            // 判断这个参数是否携带了默认值
            if (parameter.isOptional) {
                initializedAllParameters = false
                continue
            }

            // 判断这个参数是否标记为空的
            if (parameter.type.isMarkedNullable) {
                values[i] = null
            } else {
                // 如果这个参数没有标记为可空的，并且没有携带默认值
                // 就需要赋一个默认值给它，否则会实例化构造函数会出现崩溃
                // java.lang.IllegalArgumentException: method XxxBean.<init> argument 3 has type int, got java.lang.Object
                // 如果是基本数据类型就一定会出现崩溃，如果是对象的话，需要同时满足以下条件才会出现崩溃
                // 1. 后台给这个参数返回 null 的情况下（这种永远不会出现，因为框架内部处理了）
                // 2. 获取对象的时候，如果没有做判空，也会出现异常
                values[i] = getTypeDefaultValue(parameter.type)
            }
        }

        // 判断构造函数上面所有的参数是否都携带了默认值
        val result = if (initializedAllParameters) {
            // 如果是的话，则传入元素全为 Any 的数组进去
            constructor.call(*values)
        } else {
            // 如果不是的话，就传入自定义顺序的 Map 对象（Key 是参数名，Value 是参数值）进去
            constructor.callBy(IndexedParameterMap(constructor.parameters, values))
        }

        return result as T
    }

    private fun getTypeDefaultValue(type: KType): Any? {
        when (type.classifier) {
            String::class -> return ""
            Boolean::class -> return false
            Int::class -> return 0
            Long::class -> return 0L
            Float::class -> return 0.0f
            Double::class -> return 0.0
            Short::class -> return 0.toShort()
            Byte::class -> return 0.toByte()
            Char::class -> return '\u0000'
        }

        val javaType = type.javaType
        val typeToken = TypeToken.get(javaType) ?: return null
        val objectConstructor = mainConstructor.get(gson, typeToken) ?: return null
        return objectConstructor.construct()
    }

    /** 一个简单的 Map，它使用参数索引而不是排序或哈希。 */
    class IndexedParameterMap(
        private val parameterKeys: List<KParameter>,
        private val parameterValues: Array<Any?>,
    ) : AbstractMutableMap<KParameter, Any?>() {

        override fun put(key: KParameter, value: Any?): Any? = null

        override val entries: MutableSet<MutableMap.MutableEntry<KParameter, Any?>>
            get() {
                val allPossibleEntries = parameterKeys.mapIndexed { index, value ->
                    SimpleEntry<KParameter, Any?>(value, parameterValues[index])
                }
                return allPossibleEntries.filterTo(mutableSetOf()) {
                    it.value !== ABSENT_VALUE
                }
            }

        override fun containsKey(key: KParameter) = parameterValues[key.index] !== ABSENT_VALUE

        override fun get(key: KParameter): Any? {
            val value = parameterValues[key.index]
            return if (value !== ABSENT_VALUE) value else null
        }
    }
}