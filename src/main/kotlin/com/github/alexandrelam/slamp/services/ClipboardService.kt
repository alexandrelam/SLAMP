package com.github.alexandrelam.slamp.services

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.components.Service
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import java.awt.datatransfer.StringSelection

@Service(Service.Level.PROJECT)
class ClipboardService(private val project: Project) {

    fun updateClipboardContent(files: List<VirtualFile>) {
        if (files.isEmpty()) {
            clearClipboard()
            return
        }

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

            notifySuccess(files.size)
        } catch (e: Exception) {
            notifyError()
        }
    }

    private fun clearClipboard() {
        CopyPasteManager.getInstance().setContents(StringSelection(""))
        notifyCleared()
    }

    private fun notifySuccess(fileCount: Int) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("SLAMP Notifications")
            .createNotification(
                "$fileCount files copied to clipboard",
                NotificationType.INFORMATION
            )
            .notify(project)
    }

    private fun notifyError() {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("SLAMP Notifications")
            .createNotification(
                "Failed to copy files to clipboard",
                NotificationType.ERROR
            )
            .notify(project)
    }

    private fun notifyCleared() {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("SLAMP Notifications")
            .createNotification(
                "Clipboard cleared",
                NotificationType.INFORMATION
            )
            .notify(project)
    }
}