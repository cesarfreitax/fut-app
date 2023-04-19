package com.fut.core.utils

object Constants {

    // FOOTBALL API
    const val KEYWORD_FOOTBALL_API = "x-rapidapi-key"
    const val KEY_FOOTBALL_API = "5bfbb68b11f7ad4458d4248b46e7d687"
    const val URL_FOOTBALL_API = "https://v3.football.api-sports.io"
    const val IMAGE_NOT_AVAILABLE_URL =
        "https:\\/\\/media-3.api-sports.io\\/football\\/teams\\/7845.png"
    const val IMAGE_SOON_URL = "https://media-2.api-sports.io//football//teams//7849.png"
    const val COUNTRY_BRAZIL = "Brazil"
    const val TIMEZONE_SP = "America/Sao_Paulo"

    // NEWS API
    const val URL_NEWS_API = "https://newsapi.org"
    const val KEY_NEWS_API = "400a70792c2842778b4807634f58c687"
    const val PUBLISHED_AT = "publishedAt"
    val NEWS_FILTER_WORDS = listOf(
        "gol",
        "escanteio",
        "lateral",
        "falta",
        "cartão amarelo",
        "cartão vermelho",
        "pênalti",
        "cobrança de falta",
        "cobrança de pênalti",
        "chute",
        "cabeçada",
        "defesa",
        "goleiro",
        "atacante",
        "meio-campo",
        "zagueiro",
        "técnico",
        "arbitragem",
        "campeonato",
        "liga",
        "torcida",
        "clube",
        "uniforme",
        "jogo",
        "partida",
        "futebol de campo",
        "futebol de salão",
        "futsal",
        "gramado",
        "estádio",
        "bola",
        "trave",
        "redes",
        "linha de fundo",
        "linha lateral",
        "linha de meio-campo",
        "bancada",
        "cobertura",
        "contra-ataque",
        "drible",
        "gol contra",
        "impedimento",
        "marcação",
        "passe",
        "posse de bola",
        "pressão",
        "rebatida",
        "troca de passes",
        "virada de jogo",
        "finta",
        "súmula",
        "uniforme",
        "tiro de meta",
        "linhas do campo",
        "camisa",
        "chuteira",
        "tabela",
        "técnico",
        "torcida",
        "zagueiro",
        "libertadores",
        "estadual",
        "copa do brasil",
        "sulamericana",
        "árbitro",
        "assistência",
        "centroavante",
        "desarme",
        "estilo de jogo",
        "goleada",
        "jogada ensaiada",
        "jogador",
        "jogador de linha",
        "juiz",
        "linha de impedimento",
        "marcação cerrada",
        "meia-atacante",
        "mundial de clubes",
        "prorrogação",
        "reserva",
        "tabela de classificação",
        "tática",
        "tiro livre",
        "titular",
        "transição defensiva",
        "transição ofensiva",
        "triangulação",
        "uniforme de jogo",
        "vantagem"
    )

    const val YESTERDAY = "Ontem"
    const val TODAY = "Hoje"
    const val TOMORROW = "Amanhã"

    // USER PREF
    const val SELECTED_COUNTRY_NAME = "selectedCountryName"
    const val SELECTED_TEAM_NAME = "selectedTeamName"
    const val SELECTED_TEAM_ID = "selectedTeamId"
    const val USER_PREF_SELECTED_LEAGUES_ID = "leaguesId"
    const val USER_PREF_SELECTED_LEAGUES_NAME = "leaguesName"
}