package com.edu.book

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @Auther: liukaihua
 * @Date: 2024/2/29 23:30
 * @Description:
 */

@SpringBootApplication
@EnableScheduling
class EduBoolServiceApplication {


}
fun main(args: Array<String>) {
    runApplication<EduBoolServiceApplication>(*args)
}