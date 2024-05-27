import com.juanalmeyda.webapp.Game
import com.juanalmeyda.webapp.gameLens
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
    fun `get new game`() {
        val response: Response = backend(Request(GET, "http://localhost:8080/game"))

        expectThat(response.status).isEqualTo(OK)

        val game = gameLens.extract(response)
        expectThat(game).isEqualTo(Game())
    }
}