package io.olkkani.cleancodeleveltest.web

import QuizRequest
import QuizResponse
import io.olkkani.cleancodeleveltest.service.QuizService
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import toPaginationResponse
import toResponse

@RestController
@RequestMapping("/quizzes/")
class QuizRestController(
    private val quizService: QuizService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: QuizRequest
    ): ResponseEntity.HeadersBuilder<*> {
        quizService.create(request)
        return ResponseEntity.noContent()
    }

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "10") perPage : Int,
        @RequestParam(defaultValue = "0") page : Int
    )= ResponseEntity.ok().body(quizService.getAll(PageRequest.of(page-1,perPage)).toPaginationResponse())


    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long
    ): ResponseEntity<QuizResponse>
        = ResponseEntity.ok(quizService.get(id).toResponse())

    @GetMapping("/random")
    fun getRandomQuizzes(): ResponseEntity<List<QuizResponse>>
        = ResponseEntity.ok().body(quizService.getRandomList()?.map { it.toResponse() })


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun edit (
        @PathVariable id: Long,
        @RequestBody request: QuizRequest
    ): ResponseEntity.BodyBuilder {
        quizService.edit(id, request)
        return ResponseEntity.accepted()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity.HeadersBuilder<*> {
        quizService.delete(id)
        return ResponseEntity.noContent()
    }
}