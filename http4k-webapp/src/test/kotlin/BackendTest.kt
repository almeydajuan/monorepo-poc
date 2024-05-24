import com.juanalmeyda.webapp.Greeting
import com.juanalmeyda.webapp.greetingLens
import com.juanalmeyda.webapp.newBackend
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BackendTest {

    private val backend = newBackend()


    @Test
    fun `check hello world`() {
        val response: Response = backend(Request(GET, "http://localhost:8080/hello?name=John"))

        expectThat(response.status).isEqualTo(OK)

        val greeting = greetingLens.extract(response)
        expectThat(greeting).isEqualTo(Greeting("John"))
        expectThat(greeting.greet()).isEqualTo("Hello John!")
    }
}