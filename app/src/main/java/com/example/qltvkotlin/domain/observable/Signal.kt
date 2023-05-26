package com.example.qltvkotlin.domain.observable

interface Signal {
    fun subscribe(subcription: () -> Unit): AutoCloseable
    fun emit()

    interface Closable : AutoCloseable {
        val bags: List<AutoCloseable>

        fun Signal.bind(subscription: () -> Unit) {
            (bags as? ArrayList)?.add(subscribe(subscription))
        }
    }

    class Bags : Closable {
        override val bags = arrayListOf<AutoCloseable>()

        override fun close() {
            bags.forEach { it.close() }
            bags.clear()
        }
    }

    class MultipleSubscription : Signal {
        private val list = arrayListOf<() -> Unit>()
        override fun subscribe(subcription: () -> Unit): AutoCloseable {
            list.add(subcription)
            return AutoCloseable {
                list.remove(subcription)
            }
        }

        override fun emit() {
            list.forEach { it() }
        }

    }
}