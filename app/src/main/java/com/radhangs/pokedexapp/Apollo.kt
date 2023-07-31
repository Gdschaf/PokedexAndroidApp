package com.radhangs.pokedexapp

import com.apollographql.apollo3.ApolloClient

// Dagger could be a substitute for this, but it'll work
private var instance: ApolloClient? = null

fun apolloClient(): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    instance = ApolloClient.Builder()
        .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
        .build()

    return instance!!
}