package io.olkkani.cleancodeleveltest.config.exception

import java.lang.RuntimeException

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class NotFoundException (
    override val message: String,
): ServerException(404, message)

data class UnAuthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다",
): ServerException(401, message)


data class IllegalArgumentException(
    override val message: String,
): ServerException(400, message)


