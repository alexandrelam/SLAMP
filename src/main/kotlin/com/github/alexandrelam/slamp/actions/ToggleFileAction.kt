// ToggleFileAction.kt
package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
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
    }

    override fun update(@NotNull e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)

        // Log project and file state
        println("ToggleFileAction update:")
        println("- Project: ${project?.name ?: "null"}")
        println("- File: ${file?.path ?: "null"}")

        e.presentation.isEnabledAndVisible = project != null && file != null

        if (project != null && file != null) {
            val service = project.getService(FileCollectorService::class.java)
            val isFileInCollection = service.getFiles().contains(file)

            // Log collection state
            println("- Service: ${service != null}")
            println("- File in collection: $isFileInCollection")
            println("- Collection size: ${service.getFiles().size}")

            e.presentation.icon = if (isFileInCollection)
                AllIcons.General.Remove else AllIcons.General.Add
        }

        // Log final presentation state
        println("- Button enabled/visible: ${e.presentation.isEnabledAndVisible}")
        println("----------------------------------------")
    }
}