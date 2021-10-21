package com.alphasights.app.exception

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Dto for error messages
 */
@Schema(description = "Error thrown when the api encounters an exception")
data class ApiError(
    /**
     * Description of the message
     */
    @field:Schema(description = "A descriptive message of the exception", example = "Parameter validation failed on request")
    val message: String = "Error while processing the request",
    /**
     * The code for the exception. This will usually match
     */
    @field:Schema(description = "The http status code of the response", example = "400")
    val status: Int = 400,
    /**
     * The type of error that occurred, this will usually be the
     * class name of the exception
     */
    @field:Schema(description = "The type of error that occurred")
    val type: String = "Exception",
    /**
     * Extra context explaining why the error occurred
     */
    @field:Schema(description = "The error context", example = "Bad Request")
    val context: Any = mutableMapOf<String, Any>()
)

/**
 * An exception for an ApiError
 */
open class ApiErrorException(val error: ApiError) : RuntimeException(error.message)
