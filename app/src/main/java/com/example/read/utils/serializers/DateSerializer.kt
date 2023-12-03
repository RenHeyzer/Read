package com.example.read.utils.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object DateSerializer : KSerializer<Date> {

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeString(
        LocalDate.parse(
            DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochMilli(value.time)),
            dateFormat
        ).format(dateFormat)
    )

    override fun deserialize(decoder: Decoder): Date = Date(
        LocalDate.parse(decoder.decodeString(), dateFormat).atStartOfDay(ZoneId.systemDefault())
            .toInstant().toEpochMilli()
    )
}