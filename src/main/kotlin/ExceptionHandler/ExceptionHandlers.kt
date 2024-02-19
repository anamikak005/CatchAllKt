import kotlin.reflect.KClass

class ExceptionHandlers {

    companion object {
        /**
         * Catches multiple exceptions specified in the vararg parameter and handles them using a common catch block.
         *
         * @param tryBlock The block of code to try.
         * @param catchBlock The block of code to execute if an exception of a specified type is caught.
         * @param exceptionClasses Vararg parameter specifying the classes of exceptions to handle.
         */
        inline fun catchAll(tryBlock: () -> Unit,
                            catchBlock: (Throwable) -> Unit,
                            vararg exceptionClasses: KClass<out Throwable>) {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (exceptionClasses.any { it.isInstance(e) }) {
                    catchBlock(e)
                } else {
                    throw e // Rethrow the exception if it's not one of the specified types
                }
            }
        }
    }
}
