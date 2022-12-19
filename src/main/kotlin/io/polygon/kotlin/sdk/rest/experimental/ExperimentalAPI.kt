package io.polygon.kotlin.sdk.rest.experimental

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@RequiresOptIn(
    message = "This is part of an experimental ('vX') API and is unstable. " +
            "The contract for this API may change without a major version update." +
            "Usage in Kotlin code should be marked with `@ExperimentalAPI` or `@OptIn(ExperimentalAPI::class)`",
    level = RequiresOptIn.Level.WARNING
)
annotation class ExperimentalAPI
