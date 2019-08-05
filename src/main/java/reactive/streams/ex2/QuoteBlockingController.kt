package reactive.streams.ex2

import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QuoteBlockingController(private val quoteMongoBlockingRepository: QuoteMongoBlockingRepository) {

    val quotesBlocking: Iterable<Quote>
        @GetMapping("/quotes-blocking")
        @Throws(Exception::class)
        get() {
            Thread.sleep(DELAY_PER_ITEM_MS * quoteMongoBlockingRepository.count())
            return quoteMongoBlockingRepository.findAll()
        }

    @GetMapping("/quotes-blocking-paged")
    @Throws(Exception::class)
    fun getQuotesBlocking(
        @RequestParam(name = "page") page: Int,
        @RequestParam(name = "size") size: Int
    ): Iterable<Quote> {
        Thread.sleep((DELAY_PER_ITEM_MS * size).toLong())
        return quoteMongoBlockingRepository.retrieveAllQuotesPaged(PageRequest.of(page, size))
    }

    companion object {

        private val DELAY_PER_ITEM_MS = 100
    }
}

