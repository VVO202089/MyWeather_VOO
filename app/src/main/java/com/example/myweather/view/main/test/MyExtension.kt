package com.example.myweather.view.main.test/*package com.example.myweather.view.main

class MyExtension {

    fun main(){

        val people:List<LambdaKotlin.Person> = listOf(
            LambdaKotlin.Person("Вася",21),
            LambdaKotlin.Person("Петя",22),
            LambdaKotlin.Person("Маша",23)

        )

        people.indexOf(people[0])
        people.indexOf(people[1])

        people[0].name
        people[0].age

        // обход коолекции через with
        with(people){
            indexOf(get(0))
            indexOf(get(1))
            get(0).name
            get(0).age
        }

        // apply возвращает массив
        val appleRes = people.apply {
            get(0).name = "Коля"
            get(0).age = 2
        }
    }
}*/