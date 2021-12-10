package `in`.cbslgroup.ucobank.util

object Constants {

    val CANCELLED = "cancelled"
    val UPCOMING = "upcoming"
    val COMPLETED = "completed"


    //for checking from which page the otp should be given
    val FRAG_REGISTER = "frag_register"
    val FRAG_FORGOT_PASSWORD = "frag_forgot_password"
    val FRAG_LOGIN = "frag_login"


    //manoj shakya 10-04-2021
    const val SOMETHINGWRONG = "Something went wrong. Please Try Again"
    const val MOBILENO_VALIDATION =
        "{\"pattern\":\"^[6-9][0-9]{9}$\", \"msg\":  \"Mobile No. accepts only  numbers and length should be 10 (first number to start with [6-9])}\"}"
    const val EMAIL_VALIDATION =
        "{\"pattern\":\"^[a-zA-Z0-9][a-zA-Z0-9._-]+@[a-zA-Z0-9][a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$\",  \"msg\": \"Enter a valid email address\"}"




}