package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.PotionEffect
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to remove a potion effect from an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayRemoveEntityEffectPacket(
    override var entityId: Int,
    var effect: RegistryEntry
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayRemoveEntityEffectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayRemoveEntityEffectPacket(
            buffer.readInt(),
            PotionEffect[version, buffer.readByte().toInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayRemoveEntityEffectPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeByte(PotionEffect[version, packet.effect, Int::class] ?: 0)
        }
    }
}