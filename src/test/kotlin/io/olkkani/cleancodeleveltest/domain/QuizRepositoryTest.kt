package io.olkkani.cleancodeleveltest.domain

import io.olkkani.cleancodeleveltest.config.exception.NotFoundException
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class QuizRepositoryTest(
    @Autowired private val quizRepository: QuizRepository,
    @Autowired private val quizRepositorySupport: QuizRepositorySupport
) {

    @DisplayName("Quiz 생성 테스트")
    @Test
    fun createQuizTest() {
        // given
        val quiz = Quiz(
            question = "test question",
            answer = AnswerOption.OPTION_B,
            optionA = "test optionA",
            optionB = "test optionB",
            description = "test description"
        )
        // when
        val savedQuiz = quizRepository.save(quiz)

        // then
        assertThat(quiz.question).isEqualTo(savedQuiz.question)

    }

    @Test
    fun getQuizzesTest() {
        //Given
        for (i in 1 .. 20) {
            quizRepository.save(
                Quiz(
                    question = "질문 $i",
                    answer = AnswerOption.OPTION_A,
                    optionA = "test optionA",
                    optionB = "test optionB",
                    description = "test description"
                )
            )
        }
            // When
            val quizzesPageOne: Slice<Quiz> = quizRepository.findQuizzesBy(PageRequest.of(0,3))
            val quizzesPageTwo = quizRepository.findQuizzesBy(PageRequest.of(1,3))

        for (i in 1 ..5 ){
            logger.error { quizzesPageOne.content[0].id }
        }
            // Then
            assertThat(quizzesPageOne.content[0].id).isEqualTo(1)
            assertThat(quizzesPageOne.size).isEqualTo(3)

            assertThat(quizzesPageTwo.content[0].id).isEqualTo(4)
            assertThat(quizzesPageTwo.size).isEqualTo(3)
    }

    @DisplayName("Quiz 랜덤 항목 조회 테스트")
    @Test
    fun getRandomQuizTest() {
        // Given
        for (i in 1 .. 20){
            quizRepository.save(
                Quiz(
                    question = "질문 $i",
                    answer = AnswerOption.OPTION_B,
                    optionA = "test optionA",
                    optionB = "test optionB",
                    description = "test description"
                )
            )
        }

        // When
        val savedQuizzes: List<Quiz>? = quizRepositorySupport.getRandomQuiz()
        val allQuizzesCount = quizRepository.count()
        // Then
        assertThat(allQuizzesCount).isGreaterThan(10)
        assertThat(savedQuizzes?.size).isEqualTo(10)




    }

    @Test
    @Transactional
    fun editQuizTest() {
        // given
        val quiz = Quiz(
            question = "최초 질문",
            answer = AnswerOption.OPTION_A,
            optionA = "test optionA",
            optionB = "test optionB",
            description = "test description"
        )
        val savedQuiz = quizRepository.save(quiz)

        //when
        val editedQuiz = quizRepository.findByIdOrNull(savedQuiz.id)?.apply {
            question = "변경된 질문"
        }?: throw NotFoundException("질문이 존재하지 않습니다.")

//         Then
        assertThat(savedQuiz.id).isEqualTo(editedQuiz.id)
        assertThat(savedQuiz.question).isNotEqualTo(editedQuiz.question)
    }


}