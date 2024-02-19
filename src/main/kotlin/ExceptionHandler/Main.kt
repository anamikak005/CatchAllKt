package ExceptionHandler

import ExceptionHandlers.Companion.catchAll
import java.sql.SQLException

fun main() {
        catchAll(
            tryBlock = {
//                 Example code that might throw an exception
                if ((1..10).random() > 5) {
                    throw IllegalArgumentException("This is an illegal argument exception")
                } else {
                    throw SQLException("This is a null pointer exception")
                }
            },
            catchBlock = { e ->
                // This block is executed if any of the specified exceptions are caught
                println("Caught an exception: ${e.message}")
            },
            IllegalArgumentException::class, NullPointerException::class // Specify the exceptions to catch
        )
    }
