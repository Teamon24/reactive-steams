package reactive.streams.ex2

import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

import java.time.Duration

@RestController
class QuoteReactiveController(private val quoteMongoReactiveRepository: QuoteMongoReactiveRepository) {

    // If you want to use a shorter version of the Flux, use take(100) at the end of the statement below
    val quoteFlux: Flux<Quote>
        @GetMapping("/quotes-reactive")
        get() = quoteMongoReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))

    @GetMapping("/quotes-reactive-paged")
    fun getQuoteFlux(
        @RequestParam(name = "page") page: Int,
        @RequestParam(name = "size") size: Int
    ): Flux<Quote> {
        return quoteMongoReactiveRepository
            .retrieveAllQuotesPaged(PageRequest.of(page, size))
            .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }

    companion object {

        private val DELAY_PER_ITEM_MS = 300
    }

}

