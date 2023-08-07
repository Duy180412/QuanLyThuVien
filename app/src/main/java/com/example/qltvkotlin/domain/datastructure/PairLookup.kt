package com.example.qltvkotlin.domain.datastructure



class PairLookup<A, B>(pairs: Array<out Pair<A, B>>) {
    private val value2Key = hashMapOf<B, A>()
    private val key2Value = pairs.toMap().onEach {
        value2Key[it.value] = it.key
    }
    val mValueA = key2Value.keys
    val mValueB = key2Value.values

    fun keyOf(value: B): A? {
        return value2Key[value]
    }

    fun requireKeyOf(value: B): A {
        return value2Key[value] ?: error("Not found key of ${value.toString()}")
    }

    fun valueOf(key: A): B? {
        return key2Value[key]
    }

    fun requireValueOf(key: A): B {
        return key2Value[key] ?: error("Not found value of ${key.toString()}")
    }
    fun getSize():Int{
        return value2Key.size
    }

    inline fun <reified T : Any> getSizeA(): Int {
        return mValueA.filterIsInstance<T>().distinct().size
    }

    inline fun <reified T : Any> getSizeB(): Int {
        return mValueB.filterIsInstance<T>().distinct().size
    }

    fun getValueAByPosition(pos: Int): A {
        return mValueA.elementAt(pos)
    }

    fun getValueBByPosition(pos: Int): B {
      return mValueB.elementAt(pos)
    }
}

fun <A, B : Any> pairLookupOf(
    vararg pairs: Pair<A, B>,
): PairLookup<A, B> {
    return PairLookup(pairs)
}
