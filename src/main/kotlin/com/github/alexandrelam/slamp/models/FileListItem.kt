package com.github.alexandrelam.slamp.models

import com.intellij.openapi.vfs.VirtualFile

data class FileListItem(val file: VirtualFile, val displayName: String) {
    override fun toString(): String = displayName
}
