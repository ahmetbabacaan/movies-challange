package com.ahmetbabacan.movies

import com.ahmetbabacan.movies.data.models.*
import com.ahmetbabacan.movies.data.responses.DetailResponse
import com.ahmetbabacan.movies.data.responses.ListResponse

object MockUtil {

    fun mockMovie() = Movie(
        adult = false,
        backdrop_path = "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
        genre_ids = listOf(12, 14, 28),
        id = 634649,
        original_language = "en",
        original_title = "Spider-Man: No Way Home",
        overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        popularity = 5874.67,
        poster_path = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        release_date = "2021-12-15",
        title = "Spider-Man: No Way Home",
        video = false,
        vote_average = 8.4,
        vote_count = 4375
    )

    fun mockListResponse() = ListResponse(
        page = 1,
        results = listOf(mockMovie()),
        total_pages = 72,
        total_results = 1426,
        status_code = null,
        success = true,
        status_message = null
    )

    fun mockDetailResponse() = DetailResponse(
        adult = false,
        backdrop_path = "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
        belongs_to_collection = BelongsToCollection(
            id = 531241,
            name = "Spider-Man (Avengers) Collection",
            poster_path = "/nogV4th2P5QWYvQIMiWHj4CFLU9.jpg",
            backdrop_path = "/AvnqpRwlEaYNVL6wzC4RN94EdSd.jpg"
        ),
        budget = 200000000,
        genres = listOf(
            Genre(id = 12, name = "Adventure"),
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 28, name = "Action")
        ),
        homepage = "https://www.spidermannowayhome.movie",
        id = 634649,
        imdb_id = "tt10872600",
        original_language = "en",
        original_title =  "Spider-Man: No Way Home",
        overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        popularity = 5874.67,
        poster_path = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        production_companies = listOf(
            ProductionCompany(
                id =  420,
                logo_path = "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png",
                name = "Marvel Studios",
                origin_country = "US"
            ),
            ProductionCompany(
                id = 84041,
                logo_path =  "/nw4kyc29QRpNtFbdsBHkRSFavvt.png",
                name ="Pascal Pictures",
                origin_country = "US"
            ),
            ProductionCompany(
                id = 5,
                logo_path =  "/71BqEFAF4V3qjjMPCpLuyJFB9A.png",
                name = "Columbia Pictures",
                origin_country = "US"
            )
        ),
        production_countries = listOf(
            ProductionCountry(iso_3166_1 = "US", name = "United States of America")
        ),
        release_date = "2021-12-15",
        revenue = 1631853496,
        runtime = 148,
        spoken_languages = listOf(
            SpokenLanguage(english_name = "English", iso_639_1 = "en", name = "English"),
            SpokenLanguage(english_name = "Tagalog", iso_639_1 = "tl", name = "")
        ),
        status = "Released",
        tagline =  "The Multiverse unleashed.",
        title =  "Spider-Man: No Way Home",
        video = false,
        vote_average = 8.4,
        vote_count = 4375

    )
}
