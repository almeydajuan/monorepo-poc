import com.juanalmeyda.webapp.Game
import com.juanalmeyda.webapp.Move
import com.juanalmeyda.webapp.Player.O
import com.juanalmeyda.webapp.Player.X
import com.juanalmeyda.webapp.gameLens
import com.juanalmeyda.webapp.newBackend
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BackendTest {

    private val backend = newBackend()

    @Test
    fun `start new game`() {
        val response: Response = backend(Request(GET, "http://localhost:8080/game"))

        expectThat(response.status).isEqualTo(OK)

        val game = gameLens.extract(response)
        expectThat(game).isEqualTo(Game())
    }

    @Test
    fun `make some moves`() {
        expectThat(backend(Request(POST, "/game?x=0&y=1")).status).isEqualTo(OK)
        expectThat(backend(Request(POST, "/game?x=2&y=0")).status).isEqualTo(OK)
        expectThat(backend(Request(POST, "/game?x=1&y=1")).status).isEqualTo(OK)

        val response = backend(Request(GET, "/game"))
        expectThat(response.status).isEqualTo(OK)

        expectThat(gameLens.extract(response)).isEqualTo(
            Game(
                moves = listOf(
                    Move(0, 1, X),
                    Move(2, 0, O),
                    Move(1, 1, X),
                )
            )
        )
    }
}