package com.fut.core.utils

/**
 * This class is used to handle the state of a view (loading, success or error)
 *
 * @param data used to define the generic type of response
 * @param messageResId used to pass the id of a message error from values/strings.xml
 */

sealed class ViewState<T>(
    val data: T? = null,
    val messageResId: Int? = null
) {
    class Success<T>(data: T) : ViewState<T>(data)
    class Error<T>(messageResId: Int, data: T? = null) : ViewState<T>(data, messageResId)
    class Loading<T>(data: T? = null) : ViewState<T>(data)
}