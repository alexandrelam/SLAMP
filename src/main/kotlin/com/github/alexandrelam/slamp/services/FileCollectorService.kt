package com.github.alexandrelam.slamp.services

import com.github.alexandrelam.slamp.listeners.FileCollectorListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class FileCollectorService(private val project: Project) {
    private val files = mutableListOf<VirtualFile>()

    fun addFile(file: VirtualFile) {
        if (!files.contains(file)) {
            files.add(file)
            notifyListeners()
        }
    }

    fun removeFile(file: VirtualFile) {
        if (files.remove(file)) {
            notifyListeners()
        }
    }

    fun getFiles(): List<VirtualFile> = files.toList()

    fun clearFiles() {
        files.clear()
        notifyListeners()
    }

    private fun notifyListeners() {
        project.messageBus.syncPublisher(FileCollectorListener.TOPIC)
            .onFileListChanged(files)
    }
}