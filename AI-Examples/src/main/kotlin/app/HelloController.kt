//package app
//
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//class HelloController(
//    private val aiAssistant: AiAssistantService
//) {
//
//    @GetMapping("/")
//    fun index(): String = "Greetings from Spring Boot!"
//
//    @PostMapping("/ai")
//    fun index2() {
//        val e = aiAssistant.handleRequest("")
//        println(e)
//    }
//}