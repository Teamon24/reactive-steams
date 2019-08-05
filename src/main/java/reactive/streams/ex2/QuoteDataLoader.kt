package reactive.streams.ex2

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.function.LongSupplier


/**
 *
 */
@Component
class QuoteDataLoader constructor(private val quoteMongoReactiveRepository: QuoteMongoReactiveRepository ) : CommandLineRunner
{

    override fun run(vararg args: String) {

        if (quoteMongoReactiveRepository.count().block() == 0L) {
            val longSupplier = object : LongSupplier {
                var l: Long = 0L
                override fun getAsLong() = l++
            }

            val bufferedReader = BufferedReader(
                InputStreamReader(javaClass.classLoader.getResourceAsStream("pg2000.json")!!)
            )

            Flux.fromStream (
                bufferedReader.lines()
                    .filter { l -> l.trim { it <= ' ' }.isNotEmpty() }
                    .map { l ->
                        val quote = quote(longSupplier, l)
                        val mono: Mono<Quote> = quoteMongoReactiveRepository.save(quote)
                        mono
                    }
            ).subscribe { m -> log.info("New quote loaded: {}", m.block()) }
            log.info("Repository contains now {} entries.", quoteMongoReactiveRepository.count().block())
        }
    }

    fun quote(longSupplier: LongSupplier, l: String) = Quote(longSupplier.asLong.toString(), "El Quote", l)

    companion object {
        private val log = LoggerFactory.getLogger(QuoteDataLoader::class.java)
    }

}