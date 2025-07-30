package com.berlin.aflami.viewmodel.util

fun Set<Long>.toggle(id: Long): Set<Long> =
    if (contains(id)) this - id else this + id