package com.spring


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
annotation class ComponentScan(

    val value: String = ""

)
