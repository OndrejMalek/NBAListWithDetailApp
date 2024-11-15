package eu.malek.nbaplayers.ui.data

enum class UiError(val message: String) {

    NoConnection("No connection"),

    Unauthorized(
        "Your account tier does not have access."
    ),

    ApiConfiguration("Error bad server configuration"),

    TooManyRequests("You're rate limited. Try in next minute"),
    InternalServerError("We had a problem with our server. Try again later."),
    ServiceUnavailable(
        "We're temporarily offline for maintenance. Please try again later."
    ), ;

    companion object
}

class UiErrorException(val error: UiError) : Throwable()