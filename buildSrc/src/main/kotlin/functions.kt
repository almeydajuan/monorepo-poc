import org.gradle.api.logging.Logger

fun Logger.warnFormatted(vararg lines: String) {
    val length = lines.maxBy(String::length).length
    warn(
        """
                |${"-".repeat(length)}
                |${"-".repeat(length)}
                |${lines.joinToString("\n|")}
                |${"-".repeat(length)}
                |${"-".repeat(length)}
                """.trimMargin()
    )
}
