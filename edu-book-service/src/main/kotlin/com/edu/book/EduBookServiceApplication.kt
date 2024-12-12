package com.edu.book

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @Auther: liukaihua
 * @Date: 2024/2/29 23:30
 * @Description:
 */

@SpringBootApplication(scanBasePackages = ["com.edu"])
@EnableScheduling
@EnableTransactionManagement
class EduBookServiceApplication {


}
fun main(args: Array<String>) {
    runApplication<EduBookServiceApplication>(*args)
}