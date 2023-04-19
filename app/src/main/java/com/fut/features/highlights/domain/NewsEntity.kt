package com.fut.features.highlights.domain

import com.fut.features.highlights.data.models.response.Articles
import com.fut.features.highlights.data.models.response.Source
import java.io.Serializable

class NewsEntity(
    val articles: List<NewsInfoEntity>
) : Serializable {

    companion object {
        fun mapper(response: ArrayList<Articles>): NewsEntity {
            if (response.isEmpty()) {
                return NewsEntity(arrayListOf())
            }
            return NewsEntity(
                articles = response.map {
                    mapperToNewsInfoEntity(it)
                }
            )
        }

        private fun mapperToNewsInfoEntity(response: Articles): NewsInfoEntity {
            return NewsInfoEntity(
                source = response.source,
                author = response.author,
                title = response.title,
                description = response.description,
                url = response.url,
                urlToImage = response.urlToImage,
                publishedAt = response.publishedAt,
                content = response.content
            )
        }
    }

}

class NewsInfoEntity(
    val source: Source? = Source(),
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = "",
    val isSelected: Boolean = false
) : Serializable
