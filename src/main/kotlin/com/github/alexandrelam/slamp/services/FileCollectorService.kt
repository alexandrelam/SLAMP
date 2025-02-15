// FileCollectorService.kt
package com.github.alexandrelam.slamp.services

import com.intellij.openapi.vfs.VirtualFile

interface FileCollectorService {
    fun addFile(file: VirtualFile)
    fun removeFile(file: VirtualFile)
    fun getFiles(): List<VirtualFile>
    fun clearFiles()
}