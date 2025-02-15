package com.github.alexandrelam.slamp.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType

class ClearListAction(private val project: Project) : AnAction(
    "Clear List",
    "Clear all files from the list",
    AllIcons.Actions.GC
) {
    override fun actionPerformed(e: AnActionEvent) {
        val service = project.getService(FileCollectorService::class.java)
        service.clearFiles()

        // Show notification
        NotificationGroupManager.getInstance()
            .getNotificationGroup("SLAMP Notifications")
            .createNotification(
                "File list cleared",
                NotificationType.INFORMATION
            )
            .notify(project)
    }

    override fun update(e: AnActionEvent) {
        val service = project.getService(FileCollectorService::class.java)
        e.presentation.isEnabled = service.getFiles().isNotEmpty()
    }
}