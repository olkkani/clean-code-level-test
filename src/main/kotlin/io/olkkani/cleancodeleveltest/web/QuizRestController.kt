package io.olkkani.cleancodeleveltest.web

import io.olkkani.cleancodeleveltest.model.QuizRequest
import io.olkkani.cleancodeleveltest.model.toResponse
import io.olkkani.cleancodeleveltest.service.QuizService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quiz/")
class QuizRestController(
    private val quizService: QuizService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: QuizRequest
    ) {
        quizService.create(request)
    }

    @GetMapping("/list")
    fun getAll(

    ) = quizService.getAll().map { it.toResponse() }

    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long
    ) = quizService.get(id)


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun edit (
        @PathVariable id: Long,
        @RequestBody request: QuizRequest
    ) {
        quizService.edit(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable id: Long
    ) {
        quizService.delete(id)

//        return ResponseStatus
    }


//    @GetMapping
//    fun getRandomList() = quizService.getRandomList()

}