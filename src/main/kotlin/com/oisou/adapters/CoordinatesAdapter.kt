package com.oisou.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.vividsolutions.jts.geom.Coordinate
import java.io.IOException

class CoordinatesAdapter : TypeAdapter<Coordinate>() {
    @Throws(IOException::class)
    override fun read(input: JsonReader): Coordinate {
        var x = 0.0
        var y = 0.0
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "x" -> x = input.nextDouble()
                "y" -> y = input.nextDouble()
            }
        }
        input.endObject()

        return Coordinate().apply { this.x = x; this.y = y }
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter, coordinate: Coordinate?) {
        out.beginObject()
        if (coordinate != null) {

            out.name("x").value(coordinate.x)
            out.name("y").value(coordinate.y)
        }
        out.endObject()
    }
}