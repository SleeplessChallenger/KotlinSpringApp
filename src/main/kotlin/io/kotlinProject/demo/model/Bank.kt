package io.kotlinProject.demo.model

data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
)

// in Kotlin if we want to have a getter, `get()` unser variable is
// a better option compared to separate getter function

// by default properties in Kotlin are public. Hence you don't need
// to type `public`. But if you need `private` -> type it

// data class automatically creates implementation of `equals/hashcode/toString`