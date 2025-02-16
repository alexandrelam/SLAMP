package com.github.alexandrelam.slamp.actions

import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull

class ToggleFileAction : AnAction() {
    override fun actionPerformed(@NotNull e: AnActionEvent) {
        val project: Project = e.project ?: return
        val file: VirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val service = project.getService(FileCollectorService::class.java)

        if (service.getFiles().contains(file)) {
            service.removeFile(file)
            e.presentation.icon = AllIcons.General.Add
        } else {
            service.addFile(file)
            e.presentation.icon = AllIcons.General.Remove
        }

        // Update clipboard using the service
        project.getService(ClipboardService::class.java)
            .updateClipboardContent(service.getFiles())
    }

    override fun update(@NotNull e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)

        e.presentation.isEnabledAndVisible = project != null && file != null

        if (project != null && file != null) {
            val service = project.getService(FileCollectorService::class.java)
            val isFileInCollection = service.getFiles().contains(file)

            e.presentation.icon =
                if (isFileInCollection) {
                    AllIcons.General.Remove
                } else {
                    AllIcons.General.Add
                }
        }
    }
}
