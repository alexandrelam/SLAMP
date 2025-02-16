package com.github.alexandrelam.slamp.listeners

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.messages.Topic

interface FileCollectorListener {
    fun onFileListChanged(files: List<VirtualFile>)

    companion object {
        val TOPIC = Topic.create("File Collector Updates", FileCollectorListener::class.java)
    }
}
