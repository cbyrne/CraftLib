package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet tells that a player goes to bed.
 * The client with the matching Entity ID will go into bed mode.
 * This Packet is sent to all nearby players including the one sent to bed.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayUseBedPacket(
    override var entityId: Int,
    var x: Int,
    var y: Int,
    var z: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayUseBedPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayUseBedPacket(
            buffer.readInt(),
            buffer.readInt(),
            buffer.readUnsignedByte().toInt(),
            buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUseBedPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeInt(packet.x)
            buffer.writeByte(packet.y)
            buffer.writeInt(packet.z)
        }
    }
}