package com.oisou.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.oisou.adapters.CoordinatesAdapter
import com.vividsolutions.jts.geom.Coordinate
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class BeanConfig {
    @Bean
    fun gson(): Gson {
        return GsonBuilder()
            .registerTypeHierarchyAdapter(Coordinate::class.java, CoordinatesAdapter())
            .create()
    }
}