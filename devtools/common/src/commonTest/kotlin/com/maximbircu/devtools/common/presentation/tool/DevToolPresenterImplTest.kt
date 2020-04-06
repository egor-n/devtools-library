package com.maximbircu.devtools.common.presentation.tool

import com.maximbircu.devtools.common.core.createTool
import com.maximbircu.devtools.common.mvp.BasePresenterTest
import com.maximbircu.devtools.common.presentation.tools.text.TextTool
import com.maximbircu.devtools.common.presentation.tools.toggle.ToggleTool
import com.maximbircu.devtools.common.utils.mockk
import com.maximbircu.devtools.common.utils.returns
import io.mockk.every
import io.mockk.verify
import kotlin.test.Test

class DevToolPresenterImplTest : BasePresenterTest<DevToolView, DevToolPresenter>(mockk()) {
    override fun createPresenter(): DevToolPresenter = DevToolPresenter.create(view)

    @Test
    fun `sets title on bind`() {
        val tool: ToggleTool = createTool { ::title returns "Toggle tool title" }

        presenter.onToolBind(tool)

        verify { view.setTitle("Toggle tool title") }
    }

    @Test
    fun `shows enable toggle if tool can be disabled on bind`() {
        val tool: ToggleTool = createTool { ::canBeDisabled returns true }

        presenter.onToolBind(tool)

        verify { view.showEnableToggle() }
    }

    @Test
    fun `hides enable toggle if tool can not be disabled on bind`() {
        val tool: ToggleTool = createTool { ::canBeDisabled returns false }

        presenter.onToolBind(tool)

        verify { view.hideEnableToggle() }
    }

    @Test
    fun `sets tool enabled value on bind`() {
        val tool: ToggleTool = createTool { store::isEnabled returns false }

        presenter.onToolBind(tool)

        verify { view.setDevToolEnabled(isEnabled = false) }
    }

    @Test
    fun `sets tool enabled value on tool enable toggle updated`() {
        presenter.onToolEnableToggleUpdated(false)

        verify { view.setDevToolEnabled(isEnabled = false) }
    }

    @Test
    fun `stores new tool enabled value on config updated`() {
        val tool: TextTool = createTool { store::isEnabled returns false }
        presenter.onToolBind(tool)
        every { view.isToolEnabled } returns true

        presenter.onPersistToolState()

        verify { tool.store.isEnabled = true }
    }
}
