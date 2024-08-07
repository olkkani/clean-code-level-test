package io.olkkani.cleancodeleveltest.domain

import io.olkkani.cleancodeleveltest.config.exception.IllegalArgumentException
import javax.persistence.*


@Entity
@Table
class Quiz(
    @Column(name = "quiz_type")
    @Convert(converter = QuizTypeOptionConverter::class)
    var quizType: QuizTypeOption,
    @Column
    var question: String,
    @Column(name = "option_a")
    var optionA: String,
    @Column(name = "option_b")
    var optionB: String,
    @Convert(converter = CorrectOptionConverter::class)
    @Column(name = "correct_option")
    var correctOption: CorrectOption,
    @Column
    var answer: String,
    @Column
    var description: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : BaseEntity()


enum class QuizTypeOption(var code: String, var value: Long){

    OPTION_A("CODE", 0),
    OPTION_B("TEXT", 1);

    companion object{
        infix fun from(value: Long?): QuizTypeOption = QuizTypeOption.values().firstOrNull { it.value == value }?: throw IllegalArgumentException("문제 타입이 저장된 값과 다릅니다.")
        infix fun to(code: String?): QuizTypeOption = QuizTypeOption.values().firstOrNull(){it.code == code} ?: throw IllegalArgumentException("문제 타입이 존재하지 않습니다.")
    }
}


@Converter
class QuizTypeOptionConverter : AttributeConverter<QuizTypeOption, Long> {
    override fun convertToDatabaseColumn(quizTypeOption: QuizTypeOption): Long {
        return quizTypeOption.value
    }
    override fun convertToEntityAttribute(dbData: Long): QuizTypeOption {
        return QuizTypeOption from dbData
    }
}

enum class CorrectOption(var code: String, var value: Long) {
    OPTION_A("A", 0),
    OPTION_B("B", 1);

    companion object{
        infix fun from(value: Long?): CorrectOption = CorrectOption.values().firstOrNull { it.value == value }?: throw IllegalArgumentException("정답의 선택지가 저장된 값과 다릅니다.")
        infix fun to(code: String?): CorrectOption = CorrectOption.values().firstOrNull(){it.code == code} ?: throw IllegalArgumentException("정답의 선택지가 존재하지 않습니다.")
    }
}

@Converter
class CorrectOptionConverter : AttributeConverter<CorrectOption, Long> {
    override fun convertToDatabaseColumn(correctOption: CorrectOption): Long {
        return correctOption.value
    }
    override fun convertToEntityAttribute(dbData: Long): CorrectOption {
        return CorrectOption from dbData
    }
}