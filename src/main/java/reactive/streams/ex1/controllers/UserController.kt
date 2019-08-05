package reactive.streams.ex1.controllers

import reactive.streams.ex1.entities.User
import reactive.streams.ex1.events.UserEvent
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactive.streams.ex1.repos.UserRepository
import java.time.Duration


@RestController
@RequestMapping("/users")
class UserController(var userRepository: UserRepository) {

    @PostMapping
    fun create(@RequestBody user: User): Mono<ResponseEntity<User>> {
        return userRepository.save(user)
            .map { savedUser -> ResponseEntity.ok(savedUser) }
    }

    @GetMapping
    fun listUsers(): Flux<User> {
        return userRepository.findAll()
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: String): Mono<ResponseEntity<User>> {
        return userRepository
            .findById(userId)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @PutMapping("/{userId}")
    fun getUserById(@PathVariable userId: String, @RequestBody user: User): Mono<ResponseEntity<User>> {
        return userRepository
            .findById(userId)
            .flatMap { foundUser ->
                val updatedUser = User(user.name, user.age, user.salary)
                updatedUser.id = foundUser.id
                userRepository.save(updatedUser)
            }
            .map { updatedUser -> ResponseEntity.ok(updatedUser) }
            .defaultIfEmpty(ResponseEntity.badRequest().build())
    }

    @DeleteMapping("/{userId}")
    fun deleteUserById(@PathVariable userId: String): Mono<ResponseEntity<Void>> {
        return userRepository.findById(userId)
            .flatMap { existingUser: User ->
                userRepository
                    .delete(existingUser)
                    .then<ResponseEntity<Void>>(
                        Mono.just(ResponseEntity.ok().build())
                    )
            }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @GetMapping(value = ["/reactive/streams/eventsve/streams/events"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun emitEvents(): Flux<UserEvent> {
        return Flux.interval(Duration.ofSeconds(1))
            .map { value -> UserEvent("$value", "Devglan reactive.streams.User Event") }
    }


}