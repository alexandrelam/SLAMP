package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.FileCollectorService

class AddCurrentFileAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val file: VirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        // Get the service instance and add the file
        val fileCollectorService = project.getService(FileCollectorService::class.java)
        fileCollectorService.addFile(file)
    }

    override fun update(e: AnActionEvent) {
        // Enable the action only when there's an open file
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabled = file != null
    }
}