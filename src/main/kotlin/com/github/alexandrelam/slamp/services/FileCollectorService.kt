package com.github.alexandrelam.slamp.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.messages.Topic

@Service(Service.Level.PROJECT)
class FileCollectorService(private val project: Project) {
    companion object {
        val TOPIC = Topic.create("File Collector Updates", FileCollectorListener::class.java)
    }

    private val files = mutableListOf<VirtualFile>()

    fun addFile(file: VirtualFile) {
        if (!files.contains(file)) {
            files.add(file)
            notifyListeners()
        }
    }

    fun getFiles(): List<VirtualFile> = files.toList()

    fun clearFiles() {
        files.clear()
        notifyListeners()
    }

    private fun notifyListeners() {
        project.messageBus.syncPublisher(TOPIC).onFileListChanged(files)
    }
}