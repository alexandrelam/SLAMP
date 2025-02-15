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
            val (codeFiles, testFiles) = separateFiles(files)

            if (codeFiles.isNotEmpty()) {
                contentBuilder.append("[CODE]\n")
                appendFiles(contentBuilder, codeFiles)
            }

            if (testFiles.isNotEmpty()) {
                contentBuilder.append("[TEST]\n")
                appendFiles(contentBuilder, testFiles)
            }

            // Always add INSTRUCTION section at the end
            contentBuilder.append("[INSTRUCTION]\n")

            val stringSelection = StringSelection(contentBuilder.toString())
            CopyPasteManager.getInstance().setContents(stringSelection)
            notifySuccess(files.size)
        } catch (e: Exception) {
            notifyError()
        }
    }

    private fun separateFiles(files: List<VirtualFile>): Pair<List<VirtualFile>, List<VirtualFile>> {
        return files.partition { file ->
            !isTestFile(file)
        }
    }

    private fun isTestFile(file: VirtualFile): Boolean {
        return file.path.contains("/test/") ||
                file.nameWithoutExtension.endsWith("Test") ||
                file.nameWithoutExtension.endsWith("Tests") ||
                file.nameWithoutExtension.endsWith("Spec") ||
                file.nameWithoutExtension.startsWith("Test")
    }

    private fun appendFiles(builder: StringBuilder, files: List<VirtualFile>) {
        files.forEach { file ->
            val relativePath = project.basePath?.let { basePath ->
                file.path.removePrefix(basePath).removePrefix("/")
            } ?: file.path
            builder.append("// ${relativePath}\n")
            val content = FileUtil.loadTextAndClose(file.inputStream)
            builder.append(content)
            builder.append("\n\n")
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