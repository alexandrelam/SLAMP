package com.github.alexandrelam.slamp.toolWindow

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.github.alexandrelam.slamp.services.FileCollectorListener
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.github.alexandrelam.slamp.models.FileListItem
import com.github.alexandrelam.slamp.actions.ClearListAction
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.DoubleClickListener
import java.awt.BorderLayout
import java.awt.event.MouseEvent
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

class FileCollectorToolWindow(private val project: Project) {
    private val listModel = DefaultListModel<FileListItem>()
    private val fileList = JBList(listModel)
    private val service = project.getService(FileCollectorService::class.java)

    init {
        setupFileList()
        subscribeToFileChanges()
    }

    private fun setupFileList() {
        object : DoubleClickListener() {
            override fun onDoubleClick(event: MouseEvent): Boolean {
                openSelectedFile()
                return true
            }
        }.installOn(fileList)
    }

    private fun subscribeToFileChanges() {
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

        // Create toolbar with clear button
        val actionGroup = DefaultActionGroup().apply {
            add(ClearListAction(project).apply {
                templatePresentation.setText("Clear Files")
                templatePresentation.setDescription("Clear all files from the list")
            })
        }
        val toolbar = ActionManager.getInstance().createActionToolbar(
            "FileCollectorToolbar",
            actionGroup,
            true
        ).apply {
            setTargetComponent(panel)
        }

        // Add components to panel
        panel.add(toolbar.component, BorderLayout.NORTH)
        panel.add(JBScrollPane(fileList), BorderLayout.CENTER)

        updateFileList(service.getFiles())
        return panel
    }


    private fun updateFileList(files: List<VirtualFile>) {
        listModel.clear()
        files.forEach { file ->
            listModel.addElement(FileListItem(file, getShortenedPath(file)))
        }
    }

    private fun getShortenedPath(file: VirtualFile): String {
        val parts = file.path.split("/").filter { it.isNotEmpty() }
        return when {
            parts.size <= 1 -> file.name
            parts.size == 2 -> "${parts[0]}/${parts[1]}"
            else -> {
                val fileName = parts.last()
                val parent = parts[parts.size - 2]
                val grandParent = parts[parts.size - 3]
                "$grandParent/$parent/$fileName"
            }
        }
    }

    private fun openSelectedFile() {
        val selectedItem = fileList.selectedValue ?: return
        FileEditorManager.getInstance(project).openFile(selectedItem.file, true)
    }
}