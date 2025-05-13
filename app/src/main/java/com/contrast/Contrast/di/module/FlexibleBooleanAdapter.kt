package com.contrast.Contrast.di.module





import com.squareup.moshi.*
import java.lang.reflect.Type

class FlexibleBooleanAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Boolean {
        return when (reader.peek()) {
            JsonReader.Token.BOOLEAN -> reader.nextBoolean()
            JsonReader.Token.STRING -> {
                when (val value = reader.nextString().lowercase()) {
                    "true", "1" -> true
                    "false", "0" -> false
                    else -> false
                }
            }
            JsonReader.Token.NUMBER -> reader.nextInt() != 0
            JsonReader.Token.NULL -> {
                reader.nextNull<Unit>()
                false
            }
            else -> {
                reader.skipValue()
                false
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Boolean?) {
        writer.value(value)
    }
}
