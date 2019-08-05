package reactive.streams.ex1
import reactive.streams.ex1.controllers.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.*
import reactive.streams.ex1.repos.UserRepository

/**
 *
 */

@Configuration
open class BeanConfig(private val userRepository: UserRepository) {

    private val userId = "userId"
    private val users = "/users"

    private fun contentJson() = contentType(APPLICATION_JSON)
    private fun acceptJson() = accept(APPLICATION_JSON)

    @Bean
    open fun route(): RouterFunction<ServerResponse> {
        val userHandler = UserHandler(userRepository)
        val createUser = HandlerFunction { user: ServerRequest -> userHandler.createUser(user) }
        val listUser = HandlerFunction { user: ServerRequest -> userHandler.listUser(user) }
        val getUserById = HandlerFunction { user: ServerRequest -> userHandler.getUserById(user) }
        val deleteUser = HandlerFunction { user: ServerRequest -> userHandler.deleteUser(user) }
        val streamEvents = HandlerFunction { user: ServerRequest -> userHandler.streamEvents(user) }

        return RouterFunctions
            .route(
                POST(users).and(contentJson()),
                createUser)

            .andRoute(
                GET(users).and(acceptJson()),
                listUser)

            .andRoute(
                GET("$users/{$userId}").and(acceptJson()),
                getUserById)

            .andRoute(
                PUT(users).and(acceptJson()),
                createUser)

            .andRoute(
                DELETE("$users/$userId").and(acceptJson()),
                deleteUser)

            .andRoute(
                GET("$users/events/stream").and(acceptJson()),
                streamEvents)

    }


}