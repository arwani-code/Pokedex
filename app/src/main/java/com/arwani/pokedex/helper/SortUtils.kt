package com.arwani.pokedex.helper

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    fun getSortedQuery(sortType: SortType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM pokedex ")
        when (sortType) {
            SortType.ASCENDING -> {
                simpleQuery.append("ORDER BY name ASC")
            }
            SortType.DESCENDING -> {
                simpleQuery.append("ORDER BY name DESC")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}