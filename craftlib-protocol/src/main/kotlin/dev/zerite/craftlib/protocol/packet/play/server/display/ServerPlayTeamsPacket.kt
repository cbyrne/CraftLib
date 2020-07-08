package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicTeamMode
import dev.zerite.craftlib.protocol.data.registry.impl.TeamFriendlyFire
import dev.zerite.craftlib.protocol.data.registry.impl.TeamMode
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Creates and updates teams.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayTeamsPacket(
    var name: String,
    var mode: RegistryEntry,
    var displayName: String? = null,
    var prefix: String? = null,
    var suffix: String? = null,
    var friendlyFire: RegistryEntry? = null,
    var players: Array<String>? = null
) : Packet() {
    companion object : PacketIO<ServerPlayTeamsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayTeamsPacket {
            val name = buffer.readString()
            val mode = TeamMode[version, buffer.readByte().toInt()]

            return ServerPlayTeamsPacket(
                name,
                mode,
                buffer.takeIf { mode == MagicTeamMode.CREATE_TEAM || mode == MagicTeamMode.UPDATE_INFO }?.readString(),
                buffer.takeIf { mode == MagicTeamMode.CREATE_TEAM || mode == MagicTeamMode.UPDATE_INFO }?.readString(),
                buffer.takeIf { mode == MagicTeamMode.CREATE_TEAM || mode == MagicTeamMode.UPDATE_INFO }?.readString(),
                buffer.takeIf { mode == MagicTeamMode.CREATE_TEAM || mode == MagicTeamMode.UPDATE_INFO }
                    ?.readByte()?.toInt()
                    ?.let { TeamFriendlyFire[version, it] },
                buffer.takeIf { mode == MagicTeamMode.CREATE_TEAM || mode == MagicTeamMode.ADD_PLAYERS || mode == MagicTeamMode.REMOVE_PLAYERS }
                    ?.readArray({ readShort().toInt() }) { readString() }
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayTeamsPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)
            buffer.writeByte(TeamMode[version, packet.mode, Int::class] ?: 0)

            if (packet.mode == MagicTeamMode.CREATE_TEAM || packet.mode == MagicTeamMode.UPDATE_INFO) {
                buffer.writeString(packet.displayName ?: "")
                buffer.writeString(packet.prefix ?: "")
                buffer.writeString(packet.suffix ?: "")
                buffer.writeByte(packet.friendlyFire?.let { TeamFriendlyFire[version, it, Int::class] } ?: 0)
            }

            if (packet.mode == MagicTeamMode.CREATE_TEAM || packet.mode == MagicTeamMode.ADD_PLAYERS || packet.mode == MagicTeamMode.REMOVE_PLAYERS)
                buffer.writeArray(packet.players ?: emptyArray(), { writeShort(it) }) {
                    writeString(it)
                }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayTeamsPacket

        if (name != other.name) return false
        if (mode != other.mode) return false
        if (displayName != other.displayName) return false
        if (prefix != other.prefix) return false
        if (suffix != other.suffix) return false
        if (friendlyFire != other.friendlyFire) return false
        if (players != null) {
            if (other.players == null) return false
            if (!players!!.contentEquals(other.players!!)) return false
        } else if (other.players != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + mode.hashCode()
        result = 31 * result + (displayName?.hashCode() ?: 0)
        result = 31 * result + (prefix?.hashCode() ?: 0)
        result = 31 * result + (suffix?.hashCode() ?: 0)
        result = 31 * result + (friendlyFire?.hashCode() ?: 0)
        result = 31 * result + (players?.contentHashCode() ?: 0)
        return result
    }
}
