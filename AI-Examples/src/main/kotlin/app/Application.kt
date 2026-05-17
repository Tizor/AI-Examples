package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan
class Application {
}

fun main(args: Array<String>) {
    println("Hello World!!!")
    runApplication<Application>(*args)
}