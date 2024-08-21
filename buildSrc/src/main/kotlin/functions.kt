import org.gradle.api.logging.Logger
import java.io.File

fun Logger.warnFormatted(vararg lines: String) {
    warn(formatMessage(lines))
}

fun formatMessage(lines: Array<out String>): String {
    val length = lines.maxBy(String::length).length
    return """
                |${"-".repeat(length)}
                |${"-".repeat(length)}
                |${lines.joinToString("\n|")}
                |${"-".repeat(length)}
                |${"-".repeat(length)}
                """.trimMargin()
}

val flywayMigrationRegex = "([VU]\\d+([._]?\\d+)*|R)__[a-z]+([ _]?\\w+)+.sql".toRegex()
fun File.satisfiesFlywayNamingPatterns(): Boolean = name.matches(flywayMigrationRegex)
