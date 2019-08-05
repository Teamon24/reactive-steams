package reactive.streams.ex2

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface QuoteMongoReactiveRepository :
    ReactiveCrudRepository<Quote, String> {

    @Query("{ id: { \$exists: true }}")
    fun retrieveAllQuotesPaged(page: Pageable) : Flux<Quote>
}