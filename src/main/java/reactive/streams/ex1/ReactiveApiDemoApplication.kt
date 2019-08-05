package reactive.streams.ex1
import reactive.streams.ex1.entities.User
import org.springframework.boot.CommandLineRunner
import reactor.core.publisher.Flux
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import reactive.streams.ex1.repos.UserRepository

/**
 *
 */
@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
open class ReactiveApiDemoApplication {

    @Bean
    open fun run(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner {
            userRepository
                .deleteAll()
                .thenMany(
                    Flux.just(
                        User("Dhiraj", 23, 3456.0),
                        User("Mike", 34, 3421.0),
                        User("Hary", 21, 1897.0),
                        User("Travis", 19, 3294.0),
                        User("Mike", 27, 8974.0),
                        User("Leonard", 33, 5274.0)
                    )
                        .flatMap(userRepository::save))
                .thenMany(userRepository.findAll())
                .subscribe(System.out::println)

        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ReactiveApiDemoApplication::class.java, *args)
        }
    }

}