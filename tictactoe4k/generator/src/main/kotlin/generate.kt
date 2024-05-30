import com.juanalmeyda.metadata.FileExporter
import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.test
import com.juanalmeyda.metadata.yaml.yaml

fun main() {
    FileExporter(
        yaml {
            version(1)
            service {
                name("tictactoe4k")
            }
            characteristics {
                characteristic(experimental)
                characteristic(test)
            }
            attributes {
                attribute(attribute = experimental to "webapp")
            }
        }
    )
}
