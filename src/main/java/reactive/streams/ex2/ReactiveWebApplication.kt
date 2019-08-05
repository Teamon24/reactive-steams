package reactive.streams.ex2
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
open class ReactiveWebApplication {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ReactiveWebApplication::class.java, *args)
        }
    }

}