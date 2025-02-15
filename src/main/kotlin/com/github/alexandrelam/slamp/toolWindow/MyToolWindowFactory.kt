// FileCollectorToolWindowFactory.kt
package com.github.alexandrelam.slamp.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.github.alexandrelam.slamp.services.FileCollectorListener
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.openapi.vfs.VirtualFile
import java.awt.BorderLayout
import javax.swing.DefaultListModel
import javax.swing.JPanel

class FileCollectorToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val fileCollectorToolWindow = FileCollectorToolWindow(project)
        val content = ContentFactory.getInstance().createContent(
            fileCollectorToolWindow.getContent(),
            "",
            false
        )
        toolWindow.contentManager.addContent(content)
    }
}

class FileCollectorToolWindow(project: Project) {
    private val listModel = DefaultListModel<String>()
    private val fileList = JBList(listModel)
    private val service = project.getService(FileCollectorService::class.java)

    init {
        project.messageBus.connect().subscribe(
            FileCollectorListener.TOPIC,
            object : FileCollectorListener {
                override fun onFileListChanged(files: List<VirtualFile>) {
                    updateFileList(files)
                }
            }
        )
    }

    fun getContent(): JPanel {
        val panel = JPanel(BorderLayout())
        panel.add(JBScrollPane(fileList), BorderLayout.CENTER)
        updateFileList(service.getFiles())
        return panel
    }

    private fun updateFileList(files: List<VirtualFile>) {
        listModel.clear()
        files.forEach { file ->
            listModel.addElement(file.path)
        }
    }
}