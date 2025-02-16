package com.github.alexandrelam.slamp.actions

import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class ClearListAction(private val project: Project) : AnAction(
    "Clear Files",  // Text that will show next to the icon
    "Clear all files from the list",  // Tooltip
    AllIcons.Actions.GC  // Icon
) {
    init {
        // Ensure text is always shown with the icon
        templatePresentation.setText("Clear Files")
    }

    override fun actionPerformed(e: AnActionEvent) {
        // Early return if project is null
        if (e.project == null) return

        val service = project.getService(FileCollectorService::class.java)
        service.clearFiles()
        // Update clipboard using the service
        project.getService(ClipboardService::class.java)
            .updateClipboardContent(emptyList())
    }

    override fun update(e: AnActionEvent) {
        val service = project.getService(FileCollectorService::class.java)
        e.presentation.isEnabled = service.getFiles().isNotEmpty()
        // Ensure text stays visible even when disabled
        e.presentation.setText("Clear Files")
    }
}