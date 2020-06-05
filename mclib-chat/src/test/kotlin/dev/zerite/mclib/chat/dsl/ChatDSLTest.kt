package dev.zerite.mclib.chat.dsl

import dev.zerite.mclib.chat.component.*
import dev.zerite.mclib.chat.type.ChatColor
import dev.zerite.mclib.chat.type.ClickEvent
import dev.zerite.mclib.chat.type.HoverEvent
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the chat DSL is producing the correct outputs.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ChatDSLTest {

    @Test
    fun `Test Types`() {
        assertEquals(StringChatComponent("test"), chat { string("test") })
        assertEquals(KeybindChatComponent("jump"), chat { key("jump") })
        assertEquals(
            ScoreChatComponent("KodingDev", "obj", "test"),
            chat { score("KodingDev", "obj", "test") }
        )
        assertEquals(SelectorChatComponent("@e"), chat { selector("@e") })
        assertEquals(TranslationChatComponent("test"), chat { translation("test") })
        assertEquals(
            TranslationChatComponent("test", arrayOf(StringChatComponent("example"))),
            chat { translation("test", string("example")) }
        )

        assertEquals(
            StringChatComponent("parent").apply {
                clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "cmd")
            },
            chat { string("parent") onClick click(ClickEvent.Action.RUN_COMMAND, "cmd") }
        )
        assertEquals(
            StringChatComponent("parent").apply {
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, "text")
            },
            chat { string("parent") onHover hover(HoverEvent.Action.SHOW_TEXT, "text") }
        )
    }

    @Test
    fun `Test Styles`() {
        ChatColor.values().forEach {
            assertEquals(
                StringChatComponent("placeholder").apply { color = it },
                chat { string("placeholder") + it }
            )
        }

        ChatFormat.values().forEach {
            assertEquals(
                StringChatComponent("placeholder").apply { it.build(this) },
                chat { string("placeholder") + it }
            )
        }
    }

    @Test
    fun `Test Children`() {
        assert(chat { string("example") with string("test") }.siblings.isNotEmpty())
        assert(chat { string("example") }.siblings.isEmpty())
    }

}