package br.com.eduardotanaka.btgchallenge.data.model.api

import br.com.eduardotanaka.btgchallenge.data.model.api.base.ApiResponseObject

/*
{
  "page": 1,
  "results": [
    {
      "adult": false,
      "backdrop_path": "/fev8UFNFFYsD5q7AcYS8LyTzqwl.jpg",
      "genre_ids": [
        16,
        28,
        35,
        10751
      ],
      "id": 587807,
      "original_language": "en",
      "original_title": "Tom & Jerry",
      "overview": "Jerry moves into New York City's finest hotel on the eve of the wedding of the century, forcing the desperate event planner to hire Tom to get rid of him. As mayhem ensues, the escalating cat-and-mouse battle soon threatens to destroy her career, the wedding, and possibly the hotel itself.",
      "popularity": 2963.412,
      "poster_path": "/6KErczPBROQty7QoIsaa6wJYXZi.jpg",
      "release_date": "2021-02-12",
      "title": "Tom & Jerry",
      "video": false,
      "vote_average": 8,
      "vote_count": 490
    }
  ]
 */
data class FilmePopularResponse(
    val page: Int,
    val results: List<FilmePopularResult>
) : ApiResponseObject
