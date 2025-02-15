package com.github.alexandrelam.slamp.services

import com.intellij.openapi.vfs.VirtualFile

interface FileCollectorListener {
    fun onFileListChanged(files: List<VirtualFile>)
}