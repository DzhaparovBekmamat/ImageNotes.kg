package com.example.myapplication.base

/**
 * Author: Dzhaparov Bekmamat
 */
typealias BaseMapper<Input, Output> = (Input) -> Output

fun <Input, Output> BaseMapper<Input, Output>.fromToList(input: List<Input>?) = input?.map {
    invoke(it)
}