package com.github.alexandrelam.slamp.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class FileCollectorToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val fileCollectorToolWindow = FileCollectorToolWindow(project)
        val content =
            ContentFactory.getInstance().createContent(
                fileCollectorToolWindow.getContent(),
                "",
                false,
            )
        toolWindow.contentManager.addContent(content)
    }
}
