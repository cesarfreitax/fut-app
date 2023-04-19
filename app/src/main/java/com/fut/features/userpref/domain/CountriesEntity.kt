package com.fut.features.userpref.domain

import com.fut.features.userpref.data.models.response.*
import java.io.Serializable


class CountriesEntity(
    val response: List<CountryInfoEntity>
) : Serializable {

    companion object {
        fun mapper(response: ArrayList<CountryInfo>): CountriesEntity =
            CountriesEntity(
                response = response.map { country ->
                    countryInfoMapper(country)
                }
            )

        private fun countryInfoMapper(response: CountryInfo): CountryInfoEntity =
            CountryInfoEntity(
                name = response.name.orEmpty(),
                code = response.code.orEmpty(),
                flag = response.flag.orEmpty()
            )
    }


}

class CountryInfoEntity(
    val name: String,
    val code: String,
    val flag: String,
    var isSelected: Boolean? = false
) : Serializable
