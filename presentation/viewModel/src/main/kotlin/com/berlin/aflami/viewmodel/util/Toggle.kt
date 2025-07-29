package com.berlin.aflami.viewmodel.util

fun <T> Set<T>.toggle(item: T): Set<T> =
    if (contains(item)) this - item else this + item