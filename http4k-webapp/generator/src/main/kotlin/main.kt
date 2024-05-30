import com.juanalmeyda.metadata.FileExporter
import com.juanalmeyda.metadata.yaml.Characteristic.experimental
import com.juanalmeyda.metadata.yaml.Characteristic.test
import com.juanalmeyda.metadata.yaml.yaml

fun main() {
    FileExporter(
        service = "http4k-webapp",
        yaml = yaml {
            version(1)
            service {
                name("http4k-app")
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