package dev.zerite.craftlib.protocol.data.registry

import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDimension
import dev.zerite.craftlib.protocol.data.registry.impl.MagicGamemode
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Registry for storing the data about all the protocol-related
 * enums and their mappings.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object MagicRegistry {

    /**
     * Mappings for the difficulty values.
     */
    val difficulty = create<MagicDifficulty> {
        ProtocolVersion.MC1_7_2 {
            MagicDifficulty.PEACEFUL to 0
            MagicDifficulty.EASY to 1
            MagicDifficulty.NORMAL to 2
            MagicDifficulty.HARD to 3
        }
        ProtocolVersion.MC1_7_6 {
            MagicDifficulty.PEACEFUL to "example"
            MagicDifficulty.EASY to "easy"
            MagicDifficulty.NORMAL to "normal"
            MagicDifficulty.HARD to "hard"
        }
    }

    /**
     * Mappings for the dimension values.
     */
    val dimension = create<MagicDimension> {
        ProtocolVersion.MC1_7_2 {
            MagicDimension.NETHER to -1
            MagicDimension.OVERWORLD to 0
            MagicDimension.END to 1
        }
    }

    /**
     * Mappings for the gamemode values.
     */
    val gamemode = create<MagicGamemode> {
        ProtocolVersion.MC1_7_2 {
            MagicGamemode.SURVIVAL to 0
            MagicGamemode.CREATIVE to 1
            MagicGamemode.ADVENTURE to 2
        }
    }

    /**
     * Creates a new Minecraft enum and provides a builder function
     * to initialize it.
     *
     * @param  build         The builder function to initialize these mappings.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun <T : RegistryEntry> create(build: MinecraftRegistry<T>.() -> Unit) =
        MinecraftRegistry<T>().apply(build).apply { rebuild() }

}

/**
 * Represents a single registry entry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class RegistryEntry(@Suppress("UNUSED") val name: String) {
    override fun toString() = name
}

/**
 * Base for all unknown registry entries to inherit.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class UnknownRegistryEntry<T : Any>(@Suppress("UNUSED") val raw: T) :
    RegistryEntry("UNKNOWN")