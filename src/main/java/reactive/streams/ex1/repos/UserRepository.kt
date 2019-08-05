package reactive.streams.ex1.repos
import reactive.streams.ex1.entities.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface UserRepository : ReactiveMongoRepository<User, UUID> {

    fun findById(id: String): Mono<User> {
        return this.findById(userId(id))
    }

    fun deleteById(id: String): Mono<Void> {
        return this.deleteById(userId(id))
    }

    private fun userId(str: String): UUID {
        return UUID.fromString(str)
    }
}


