package io.kotlinProject.demo.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank(
    @JsonProperty("account_number")
    val accountNumber: String,

    @JsonProperty("trust")
    val trust: Double,

    @JsonProperty("default_transaction_fee")
    val transactionFee: Int
)

// in Kotlin if we want to have a getter, `get()` under variable is
// a better option compared to separate getter function

// by default properties in Kotlin are public. Hence, you don't need
// to type `public`. But if you need `private` -> type it

// data class automatically creates implementation of `equals/hashcode/toString`

// `@JsonProperty` is to match property in JSON && in class