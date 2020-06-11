package dev.zerite.craftlib.protocol.data.world

/**
 * Stores a chunk's full data, including the block information and
 * lighting data.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class Chunk(private var blocks: Array<Block?>) {
    companion object {
        /**
         * The desired size which the blocks array should be.
         */
        private const val DESIRED_BLOCKS = 16 * 16 * 16

        /**
         * Gets the index of a block for an array.
         *
         * @param  x          The x position of the block in the chunk.
         * @param  y          The y position of the block in the chunk.
         * @param  z          The z position of the block in the chunk.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        fun index(x: Int, y: Int, z: Int) = x + (y * 16 + z) * 16
    }

    /**
     * Gets a block at the location.
     *
     * @param  x          The x position of the block in the chunk.
     * @param  y          The y position of the block in the chunk.
     * @param  z          The z position of the block in the chunk.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(x: Int, y: Int, z: Int) = blocks.getOrNull(index(x, y, z))

    /**
     * Sets a block at the given location on the client side.
     *
     * @param  x          The x position of the block in the chunk.
     * @param  y          The y position of the block in the chunk.
     * @param  z          The z position of the block in the chunk.
     * @param  value      The new block.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun set(x: Int, y: Int, z: Int, value: Block) {
        if (blocks.size < DESIRED_BLOCKS) blocks = Array(DESIRED_BLOCKS) { blocks.getOrNull(it) }
        blocks[index(x, y, z)] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chunk

        if (!blocks.contentEquals(other.blocks)) return false

        return true
    }

    override fun hashCode() = blocks.contentHashCode()
}

/**
 * Stores information about a single block in the world
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class Block(
    val id: Int,
    val metadata: Int,
    val blockLight: Int,
    val skyLight: Int,
    val location: BlockLocation
)

/**
 * Vector for storing a 3D block location.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class BlockLocation(
    val x: Int,
    val y: Int,
    val z: Int
)

/**
 * Stores information about a chunk.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ChunkMetadata(
    val chunkX: Int,
    val chunkZ: Int,
    val primaryBitmap: Int,
    val addBitmap: Int
)