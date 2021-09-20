package com.example.myweather.view.main.test/*package com.example.myweather.view.main

import android.util.Log

class MyDelegate{

}

class BaseImpl:base1,base2{

    override fun funBase1() {
        Log.d("myLogs", "funBase1")
    }

    override val nameBase1:String = "nameBase1"
        get() = TODO("Not yet implemented")

    override fun funBase2() {
        TODO("Not yet implemented")
    }


    override val nameBase2: String
        get() = TODO("Not yet implemented")

}
interface base1{
    fun funBase1()
    val nameBase1:String
}

interface base2{
    fun funBase2()
    val nameBase2:String
}*/

