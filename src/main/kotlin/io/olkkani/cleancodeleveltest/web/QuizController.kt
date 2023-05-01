package io.olkkani.cleancodeleveltest.web

import io.olkkani.cleancodeleveltest.model.toResponse
import io.olkkani.cleancodeleveltest.service.QuizService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class QuizController (
    private val quizService: QuizService
){
//    @GetMapping
//    fun list (
//        model: Model
//    ): String {
//        val quizzes = quizService.getList().map { it.toResponse()}
//        model.addAttribute("quizzes", quizzes)
//        return "list"
//    }

    @GetMapping("/register")
    fun register (

    ) = "register"

    @PostMapping("/editor")
    fun editor (
       id: Long,
       model: Model
    ): String {
        val quiz = quizService.get(id).toResponse()
        model.addAttribute( "quiz", quiz)
        return "editor"
    }

    @GetMapping("/level-test")
    fun quiz (
        model : Model
    ) : String{
        val quizzes = quizService.getRandomList()?.map { it.toResponse()}
        model.addAttribute("quizzes", quizzes)
        return "quiz"
    }

    @PostMapping("/result")
    fun result () = "result"
}