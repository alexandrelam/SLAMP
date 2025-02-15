// FileCollectorServiceImpl.kt
package com.github.alexandrelam.slamp.services

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class FileCollectorServiceImpl(private val project: Project) : FileCollectorService {
    private val files = mutableListOf<VirtualFile>()

    override fun addFile(file: VirtualFile) {
        if (!files.contains(file)) {
            files.add(file)
            notifyListeners()
        }
    }

    override fun removeFile(file: VirtualFile) {
        if (files.remove(file)) {
            notifyListeners()
        }
    }

    override fun getFiles(): List<VirtualFile> = files.toList()

    override fun clearFiles() {
        files.clear()
        notifyListeners()
    }

    private fun notifyListeners() {
        project.messageBus.syncPublisher(FileCollectorListener.TOPIC)
            .onFileListChanged(files)
    }
}