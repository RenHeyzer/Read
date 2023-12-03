package com.example.read.utils.serializers

import com.example.read.feature_detail.domain.models.Status
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object StatusSerializer : KSerializer<Status> {
    override val descriptor = PrimitiveSerialDescriptor("Status", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Status) = encoder.encodeString(value.status)
    override fun deserialize(decoder: Decoder): Status = when (decoder.decodeString()) {
        Status.ANNOUNCEMENT.status -> Status.ANNOUNCEMENT
        Status.CONTINUES.status -> Status.CONTINUES
        Status.SUSPENDED.status -> Status.SUSPENDED
        Status.ABANDONED.status -> Status.ABANDONED
        else -> Status.FINISHED
    }
}