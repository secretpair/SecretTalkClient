package com.example.secretpairproject.config

import java.util.*


fun Date.isEqualDay(date: Date): Boolean {

    val car1 = Calendar.getInstance()
    val car2 = Calendar.getInstance()

    car1.time = this
    car2.time = date

    return car1.get(Calendar.YEAR) == car2.get(Calendar.YEAR) &&
            car1.get(Calendar.MONTH) == car2.get(Calendar.MONTH) &&
            car1.get(Calendar.DAY_OF_MONTH) == car2.get(Calendar.DAY_OF_MONTH)

}

fun Date.isEqualDayAndTime(date: Date): Boolean {

    val car1 = Calendar.getInstance()
    val car2 = Calendar.getInstance()

    car1.time = this
    car2.time = date

    return car1.get(Calendar.YEAR) == car2.get(Calendar.YEAR) &&
            car1.get(Calendar.MONTH) == car2.get(Calendar.MONTH) &&
            car1.get(Calendar.DAY_OF_MONTH) == car2.get(Calendar.DAY_OF_MONTH) &&
            car1.get(Calendar.HOUR) == car2.get(Calendar.HOUR) &&
            car1.get(Calendar.MINUTE) == car2.get(Calendar.MINUTE)

}
