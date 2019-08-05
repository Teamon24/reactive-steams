package reactive.streams.ex2

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuoteMongoBlockingRepository : CrudRepository<Quote, String> {

    @Query("{ id: { \$exists: true }}")
    fun retrieveAllQuotesPaged(page: Pageable): List<Quote>

}