package reactive.streams.ex1.controllers

import reactive.streams.ex1.entities.User
import reactive.streams.ex1.events.UserEvent
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactive.streams.ex1.repos.UserRepository
import java.time.Duration


class UserHandler(private val userRepository: UserRepository) {

    fun createUser(request: ServerRequest): Mono<ServerResponse> {
        val userMono = request.bodyToMono(User::class.java).flatMap { user -> this.userRepository.save(user) }
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userMono, User::class.java)
    }

    fun listUser(request: ServerRequest): Mono<ServerResponse> {
        val user = this.userRepository.findAll()
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(user, User::class.java)
    }

    fun getUserById(request: ServerRequest): Mono<ServerResponse> {
        val userId = request.pathVariable("userId")
        val notFound = ServerResponse.notFound().build()
        val userMono = this.userRepository.findById(userId)
        return userMono.flatMap { user ->
            ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject<Any>(user))
        }
            .switchIfEmpty(notFound)
    }

    fun deleteUser(request: ServerRequest): Mono<ServerResponse> {
        val userId = request.pathVariable("userId")
        this.userRepository.deleteById(userId)
        return ServerResponse.ok().build()
    }

    fun streamEvents(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1))
            .map { value ->
                UserEvent(
                    "$value",
                    "Devglan reactive.streams.User Event"
                )
            }, UserEvent::class.java
        )
    }

}