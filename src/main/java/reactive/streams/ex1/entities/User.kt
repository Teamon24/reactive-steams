package reactive.streams.ex1.entities

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.Id

@Document(collection = "user")
class User(val name: String, val age: Int, val salary: Double) {

    @Id
    var id: UUID = UUID.randomUUID()

}