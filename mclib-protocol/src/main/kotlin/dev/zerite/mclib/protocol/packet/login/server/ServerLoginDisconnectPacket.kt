package dev.zerite.mclib.protocol.packet.login.server

import dev.zerite.mclib.chat.component.BaseChatComponent
import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate that the connection should be closed, also
 * including a chat component which should be the the message displayed
 * on the client's disconnect screen.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerLoginDisconnectPacket(var message: BaseChatComponent) {
    companion object : PacketIO<ServerLoginDisconnectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerLoginDisconnectPacket(buffer.readChat())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerLoginDisconnectPacket,
            connection: NettyConnection
        ) {
            buffer.writeChat(packet.message)
        }
    }
}