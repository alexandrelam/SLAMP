package com.github.alexandrelam.slamp.toolWindow

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.github.alexandrelam.slamp.listeners.FileCollectorListener
import com.github.alexandrelam.slamp.models.FileListItem
import com.github.alexandrelam.slamp.actions.ClearListAction
import com.github.alexandrelam.slamp.actions.CopyToClipboardAction
import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.DoubleClickListener
import java.awt.BorderLayout
import java.awt.Rectangle
import java.awt.event.MouseEvent
import javax.swing.DefaultListModel
import javax.swing.JPanel

class FileCollectorToolWindow(private val project: Project) {
    private val listModel = DefaultListModel<FileListItem>()
    private val fileList = JBList(listModel).apply {
        cellRenderer = FileListItemRenderer(project)
    }
    private val service = project.getService(FileCollectorService::class.java)

    init {
        setupFileList()
        subscribeToFileChanges()
        setupMouseListener()
    }

    private fun setupMouseListener() {
        fileList.addMouseListener(object : java.awt.event.MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val index = fileList.locationToIndex(e.point)
                if (index != -1) {
                    val item = listModel.getElementAt(index)
                    val cellBounds = fileList.getCellBounds(index, index)

                    // Calculate delete button bounds using the constants from renderer
                    val deleteButtonBounds = Rectangle(
                        cellBounds.x + cellBounds.width - FileListItemRenderer.TOTAL_BUTTON_WIDTH,
                        cellBounds.y,
                        FileListItemRenderer.TOTAL_BUTTON_WIDTH,
                        cellBounds.height
                    )

                    if (deleteButtonBounds.contains(e.point)) {
                        // Delete button clicked
                        service.removeFile(item.file)
                        project.getService(ClipboardService::class.java)
                            .updateClipboardContent(service.getFiles())
                        e.consume()
                    } else if (e.clickCount == 2) {
                        // Double click on item (not on delete button)
                        openSelectedFile()
                    }
                }
            }
        })
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

        // Create toolbar with copy and clear buttons
        val actionGroup = DefaultActionGroup().apply {
            // Add copy button first (left)
            add(CopyToClipboardAction(project).apply {
                templatePresentation.setText("Copy to Clipboard")
                templatePresentation.setDescription("Copy files content to clipboard")
            })

            // Add clear button second (right)
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