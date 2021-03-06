package com.maximbircu.devtools.common.presentation.tools.group

import com.maximbircu.devtools.common.core.DevTool
import com.maximbircu.devtools.common.core.ToolStore

/**
 * Represents a dev tool able to combine multiple tools into a group.
 *
 * @property tools the group member tools
 */
class GroupTool(val tools: Map<String, DevTool<*>> = mapOf()) : DevTool<Unit>() {
    override val store: ToolStore<Unit> = GroupToolStore()

    override fun getDefaultValue() {
        if (tools.isEmpty()) {
            throw IllegalArgumentException("A group should contain at least on child")
        }
    }
}
