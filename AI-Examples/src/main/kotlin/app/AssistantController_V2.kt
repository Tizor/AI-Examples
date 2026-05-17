package app

import app.models.ClassificationResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Map


@RestController
@RequestMapping("/api/assistant")
open class AssistantController_V2 (
    private val assistantService: AiAssistantService_v2

) {



    @PostMapping("/classify")
//    @Operation(summary = "Классификация запроса", description = "Определяет категорию запроса пользователя")
    fun classify(@RequestBody request: MutableMap<String?, String?>): ResponseEntity<ClassificationResponse?> {
        val question = request.get("question")
        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest().build()
        }

        val response: ClassificationResponse? = assistantService.classifyRequest(question)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/ask")
//    @Operation(
//        summary = "Обработка запроса",
//        description = "Классифицирует запрос и возвращает структурированный JSON ответ"
//    )
    fun ask(@RequestBody request: MutableMap<String, String>): ResponseEntity<Any?> {
        val question = request.get("question")
        if (question == null || question.isBlank()) {
            return ResponseEntity.badRequest().body(mutableMapOf<String, String>("error" to "Question cannot be empty"))
        }

        log.info("Processing question: {}", question)
        val response: Any? = assistantService.handleRequest(question)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/health")
    fun health(): ResponseEntity<MutableMap<String, String>> {
        return ResponseEntity.ok(mutableMapOf<String, String>("status" to "OK", "service" to "GigaChat JSON Assistant"))
    }

    companion object {
        private val log = LoggerFactory.getLogger(AssistantController_V2::class.java)
    }
}