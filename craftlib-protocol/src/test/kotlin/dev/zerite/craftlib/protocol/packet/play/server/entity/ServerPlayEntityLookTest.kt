package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the entity look packet is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityLookTest : PacketTest<ServerPlayEntityLookPacket>(ServerPlayEntityLookPacket) {

    init {
        example(ServerPlayEntityLookPacket(0, 0.0f, 0.0f))
        example(ServerPlayEntityLookPacket(255, 180.0f, 180.0f))
        example(ServerPlayEntityLookPacket(127, 90f, 0f)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(127)
                writeByte(64)
                writeByte(256)
            }
        }
    }

}