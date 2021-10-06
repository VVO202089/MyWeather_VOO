package com.example.myweather.view.main.test

import android.util.Log

class LambdaKotlin {

    fun main() {
        val l1 = {
            Log.d("myLogs", "run1 ") // это как функция, инициализация
            "run2"
        }// результат
        val l2 = run {
            Log.d("myLogs", "run3")
            "run4"
        }
        Log.d("myLogs", l1()) // выполнение
        Log.d("myLogs", l2)

        // анонимная функция
        val valfunAnonim = fun(int: Int): String {
            Log.d("MyLog", "зашли в анонимус")
            return "funAnonimous"
        }

        // через лямбду
        val funLambda = hack@{ int1: Int, int2: Int ->
            Log.d("MyLog", "зашли в анонимус")
            return@hack "funAnonimous"
        }
    }

    data class Person(var name:String,var age:Int)

    fun p2(person: Person){
        Log.d("MyLog", "${person.name}")
    }

}