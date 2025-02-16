package com.github.alexandrelam.slamp.actions

import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class CopyToClipboardAction(private val project: Project) : AnAction(
    "Copy to Clipboard",  // Text that will show next to the icon
    "Copy files content to clipboard",  // Tooltip
    AllIcons.Actions.Copy  // Icon
) {
    init {
        templatePresentation.setText("Copy to Clipboard")
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return

        val service = project.getService(FileCollectorService::class.java)
        project.getService(ClipboardService::class.java)
            .updateClipboardContent(service.getFiles())
    }

    override fun update(e: AnActionEvent) {
        if (e.project == null) {
            e.presentation.isEnabled = false
            e.presentation.setText("Copy to Clipboard")
            return
        }

        val service = project.getService(FileCollectorService::class.java)
        e.presentation.isEnabled = service.getFiles().isNotEmpty()
        e.presentation.setText("Copy to Clipboard")
    }
}
