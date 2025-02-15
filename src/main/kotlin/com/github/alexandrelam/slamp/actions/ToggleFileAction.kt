package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import org.jetbrains.annotations.NotNull
import java.awt.datatransfer.StringSelection
import com.intellij.openapi.util.io.FileUtil

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

        updateClipboardContent(service.getFiles(), project)
    }

    override fun update(@NotNull e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)

        e.presentation.isEnabledAndVisible = project != null && file != null

        if (project != null && file != null) {
            val service = project.getService(FileCollectorService::class.java)
            val isFileInCollection = service.getFiles().contains(file)

            e.presentation.icon = if (isFileInCollection)
                AllIcons.General.Remove else AllIcons.General.Add
        }
    }

    private fun updateClipboardContent(files: List<VirtualFile>, project: Project) {
        try {
            val contentBuilder = StringBuilder()

            files.forEach { file ->
                contentBuilder.append("// ${file.name}\n")
                val content = FileUtil.loadTextAndClose(file.inputStream)
                contentBuilder.append(content)
                contentBuilder.append("\n\n")
            }

            val stringSelection = StringSelection(contentBuilder.toString())
            CopyPasteManager.getInstance().setContents(stringSelection)

            // Success notification
            NotificationGroupManager.getInstance()
                .getNotificationGroup("SLAMP Notifications")
                .createNotification(
                    "${files.size} files copied to clipboard",
                    NotificationType.INFORMATION
                )
                .notify(project)

        } catch (e: Exception) {
            // Error notification
            NotificationGroupManager.getInstance()
                .getNotificationGroup("SLAMP Notifications")
                .createNotification(
                    "Failed to copy files to clipboard",
                    NotificationType.ERROR
                )
                .notify(project)
        }
    }
}