package com.alphasights.app.exception

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private val log = KotlinLogging.logger {}

@ControllerAdvice
@ResponseBody
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val error = ApiError(
            type = ex.javaClass.simpleName,
            message = ex.message ?: "",
            status = 500
        )
        val headers = HttpHeaders()
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val body = handleApiException(ex, error, headers, status, request)
        log.error(ex.message, ex)
        return ResponseEntity(body, headers, status)
    }

    @ExceptionHandler(ApiErrorException::class)
    fun handleAPIExceptions(ex: ApiErrorException, request: WebRequest): ResponseEntity<Any> {
        val error = ex.error
        val headers = HttpHeaders()
        val status = error.status
        return ResponseEntity(error, headers, status)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        super.handleExceptionInternal(ex, body, headers, status, request)
        val error = ApiError(
            type = ex.javaClass.simpleName,
            message = ex.message ?: "",
            status = status.value()
        )
        val err = handleApiException(ex, error, headers, status, request)
        log.error(ex.message, ex)
        return ResponseEntity(err, headers, status)
    }

    protected fun handleApiException(
        ex: Exception,
        body: ApiError,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ApiError {
        return when (ex) {
            is BindException -> {
                body.copy(
                    message = "Validation failed for method argument with ${ex.bindingResult.errorCount} error(s)",
                    context = ex.bindingResult.fieldErrors.map {
                        var field = it.field
                        field to it.defaultMessage
                    }.toMap()
                )
            }
            else -> body
        }
    }
}
