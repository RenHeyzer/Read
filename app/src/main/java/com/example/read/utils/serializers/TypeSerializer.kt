package com.example.read.utils.serializers

import com.example.read.feature_detail.domain.models.Type
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object TypeSerializer : KSerializer<Type> {
    override val descriptor = PrimitiveSerialDescriptor("Type", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Type) = encoder.encodeString(value.type)
    override fun deserialize(decoder: Decoder): Type = when (decoder.decodeString()) {
        Type.COMICS.type -> Type.COMICS
        else -> Type.MANGA
    }
}