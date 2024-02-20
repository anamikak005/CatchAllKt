import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.sql.SQLException
import kotlin.test.assertEquals
import kotlin.test.assertSame

@ExtendWith(MockitoExtension::class)
class TestCatchAllException {

    @Test
    fun `catchAll throws exception when unexpected exception type is caught`() {
        val unexpectedException = NullPointerException("Unexpected exception")

        val exception = assertThrows(NullPointerException::class.java) {
            ExceptionHandlers.catchAll(
                tryBlock = {
                    throw unexpectedException
                },
                catchBlock = { e ->
                    // This block would handle expected exceptions, but we expect it not to be called here
                    throw AssertionError("Unexpected execution of catch block", e)
                },
                SQLException::class, ArithmeticException::class
            )
        }

        assert(exception === unexpectedException) { "The thrown exception is not the same as the expected unexpected exception" }
    }

    @Test
    fun `catchAll rethrows exception when expected exception type is caught`() {
        val expectedException = NullPointerException("Expected exception")

        val exception = assertThrows(NullPointerException::class.java) {
            ExceptionHandlers.catchAll(
                tryBlock = {
                    throw expectedException
                },
                catchBlock = { e ->
                    // Correctly rethrowing the expected exception
                    throw e
                },
                SQLException::class, NullPointerException::class
            )
        }

        assert(exception === expectedException) { "The thrown exception is the same as the expected exception" }
    }

    @Test
    fun `catchAll does not throw any exception when try runs successfully`() {
        var value: Int = 0

        val exception = assertDoesNotThrow {
            ExceptionHandlers.catchAll(
                tryBlock = {
                    if (value > 0) {
                        throw NullPointerException("This will not be thrown")
                    }
                    value = 5
                           },
                catchBlock = { e ->
                    throw e
                },
                SQLException::class, NullPointerException::class
            )
        }

        assertEquals(5, value, "The try block did not throw any exception")
    }

    @Test
    fun `catchAll correctly handles multiple specified exception types`() {
        val exceptionMessage = "Handled in catch block"
        var message = ""

        assertDoesNotThrow {
            ExceptionHandlers.catchAll(
                tryBlock = {
                    throw SQLException("Simulated SQL exception")
                },
                catchBlock = { e ->
                    if (e is SQLException) {
                        message = exceptionMessage
                    }
                },
                SQLException::class, NullPointerException::class
            )
        }
        assertEquals(exceptionMessage, message, "SQLException was handled correctly")
    }

    @Test
    fun `catchAll executes try block without catching exceptions when none are specified`() {
        var value = 0

        assertDoesNotThrow {
            ExceptionHandlers.catchAll(
                tryBlock = {
                     value = 5
                },
                catchBlock = { _ ->
                    throw AssertionError("Catch block should not be executed")
                }
                // No exceptions specified
            )
        }
        assertEquals(5, value, "The try block did not throw any exception")
    }

    @Test
    fun `catchAll correctly propagates exceptions thrown from catch block`() {
        val catchBlockException = IllegalStateException("Exception from catch block")

        val exception = assertThrows(IllegalStateException::class.java) {
            ExceptionHandlers.catchAll(
                tryBlock = {
                    throw NullPointerException("Trigger catch block")
                },
                catchBlock = { _ ->
                    throw catchBlockException
                },
                NullPointerException::class
            )
        }
        assertSame(catchBlockException, exception, "The exception from the catch block was not propagated correctly")
    }

}