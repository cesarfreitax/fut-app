package com.fut.features.matches.domain

import com.fut.features.matches.data.models.response.FixtureInfo
import com.fut.features.matches.data.models.response.FixturesResponse
import com.fut.features.matches.data.models.response.Parameters
import com.fut.features.search.data.models.response.Paging
import java.io.Serializable

class FixturesEntity(
    val get: String,
    val parameters: Parameters,
    val errors: ArrayList<String>,
    val results: Int,
    val paging: Paging,
    val response: ArrayList<FixtureInfo>
) : Serializable {

    companion object {
        fun mapper(response: FixturesResponse): FixturesEntity {
            return FixturesEntity(
                get = response.get,
                parameters = response.parameters,
                errors = response.errors,
                results = response.results,
                paging = response.paging,
                response = response.response
            )
        }
    }
}