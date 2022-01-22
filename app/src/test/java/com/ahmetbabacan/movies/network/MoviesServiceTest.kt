package com.ahmetbabacan.movies.network

import com.ahmetbabacan.movies.MainCoroutinesRule
import com.ahmetbabacan.movies.data.responses.DetailResponse
import com.ahmetbabacan.movies.data.responses.ListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class MoviesServiceTest : ApiAbstract<MoviesService>() {

    private lateinit var service: MoviesService

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(MoviesService::class.java)
    }

    @Test
    fun nowPlayingTest() = runBlocking {
        enqueueResponse("/NowPlayingResponse.json")
        val response = service.nowPlaying(1)
        val responseBody = requireNotNull(response.body()) as ListResponse
        mockWebServer.takeRequest()

        assertThat(responseBody.total_results, `is`(1426))
        assertThat(
            responseBody.results!![1].title,
            `is`("Spider-Man: No Way Home")
        )
        assertThat(responseBody.results!![2].poster_path, `is`("/aWeKITRFbbwY8txG5uCj4rMCfSP.jpg"))
    }

    @Test
    fun upcomingTest() = runBlocking {
        enqueueResponse("/UpcomingResponse.json")
        val response = service.upcoming()
        val responseBody = requireNotNull(response.body()) as ListResponse
        mockWebServer.takeRequest()

        assertThat(responseBody.total_results, `is`(297))
        assertThat(
            responseBody.results!![2].title,
            `is`("Spider-Man: No Way Home")
        )
        assertThat(responseBody.results!![3].poster_path, `is`("/kZNHR1upJKF3eTzdgl5V8s8a4C3.jpg"))
    }

    @Throws(IOException::class)
    @Test
    fun detailTest() = runBlocking {
        enqueueResponse("/Spiderman.json")
        val response = service.detail(634649)
        val responseBody = requireNotNull(response.body()) as DetailResponse
        mockWebServer.takeRequest()

        assertThat(responseBody.id, `is`(634649))
        assertThat(responseBody.imdb_id, `is`("tt10872600"))
        assertThat(responseBody.poster_path, `is`("/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"))
    }
}
