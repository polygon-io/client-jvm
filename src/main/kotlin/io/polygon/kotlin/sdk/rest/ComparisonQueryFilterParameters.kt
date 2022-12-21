package io.polygon.kotlin.sdk.rest

import io.ktor.http.*

/**
 * Common query filter extensions for query parameters across the Polygon API.
 * Learn more about them in this blog post:
 * https://polygon.io/blog/api-pagination-patterns/
 *
 * In many cases, you may just want to query for a particular value instead of an inequality (ie `ticker=GE`);
 * For a shortcut to query where the field is exactly a given value, see [ComparisonQueryFilterParameters.equal]
 */
data class ComparisonQueryFilterParameters<T>(
    /**
     * Return results where the field is exactly this value.
     */
    val equal: T? = null,

    /**
     * Return results where this field is less than the value.
     */
    val lessThan: T? = null,

    /**
     * Return results where this field is less than or equal to the value.
     */
    val lessThanOrEqual: T? = null,

    /**
     * Return results where this field is greater than the value.
     */
    val greaterThan: T? = null,

    /**
     * Return results where this field is greater than or equal to the value.
     */
    val greaterThanOrEqual: T? = null,
) {
    companion object {

        /**
         * Helper function for creating a [ComparisonQueryFilterParameters] where just the `equal` parameter is set.
         */
        @JvmStatic
        fun <T> equal(value: T): ComparisonQueryFilterParameters<T> = ComparisonQueryFilterParameters(equal = value)
    }

}

/**
 * A builder class for better Java interop with [ComparisonQueryFilterParameters].
 *
 * Note that this builder class is handwritten instead of generated using a `@Builder` annotation because
 * the annotation doesn't support generating builders for classes with generic type parameters.
 */
class ComparisonQueryFilterParametersBuilder<T>() {
    private var equal: T? = null
    private var lessThan: T? = null
    private var lessThanOrEqual: T? = null
    private var greaterThan: T? = null
    private var greaterThanOrEqual: T? = null

    constructor(source: ComparisonQueryFilterParameters<T>) : this() {
        this.equal = source.equal
        this.lessThan = source.lessThan
        this.lessThanOrEqual = source.lessThanOrEqual
        this.greaterThan = source.greaterThan
        this.greaterThanOrEqual = source.greaterThanOrEqual
    }

    fun equal(value: T?): ComparisonQueryFilterParametersBuilder<T> = apply { this.equal = value }
    fun lessThan(value: T): ComparisonQueryFilterParametersBuilder<T> = apply { this.lessThan = value }
    fun lessThanOrEqual(value: T): ComparisonQueryFilterParametersBuilder<T> = apply { this.lessThanOrEqual = value }
    fun greaterThan(value: T): ComparisonQueryFilterParametersBuilder<T> = apply { this.greaterThan = value }
    fun greaterThanOrEqual(value: T): ComparisonQueryFilterParametersBuilder<T> =
        apply { this.greaterThanOrEqual = value }

    fun build(): ComparisonQueryFilterParameters<T> =
        ComparisonQueryFilterParameters(
            equal = this.equal,
            lessThan = this.lessThan,
            lessThanOrEqual = this.lessThanOrEqual,
            greaterThan = this.greaterThan,
            greaterThanOrEqual = this.greaterThanOrEqual
        )
}

/**
 * Helper function for applying [ComparisonQueryFilterParameters] to a [URLBuilder]
 */
internal fun <T> URLBuilder.applyComparisonQueryFilterParameters(rootName: String, params: ComparisonQueryFilterParameters<T>?) {
    params?.equal?.toString()?.let { parameters[rootName] = it }
    params?.lessThan?.toString()?.let { parameters["$rootName.lt"] = it }
    params?.lessThanOrEqual?.toString()?.let { parameters["$rootName.lte"] = it }
    params?.greaterThan?.toString()?.let { parameters["$rootName.gt"] = it }
    params?.greaterThanOrEqual?.toString()?.let { parameters["$rootName.gte"] = it }
}
