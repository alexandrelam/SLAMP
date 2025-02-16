package com.github.alexandrelam.slamp.toolWindow

import com.github.alexandrelam.slamp.models.FileListItem
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLabel
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import javax.swing.*

class FileListItemRenderer(private val project: Project) : ListCellRenderer<FileListItem> {
    companion object {
        const val BUTTON_WIDTH = 24
        const val BUTTON_HEIGHT = 24
        const val BUTTON_PADDING = 8
        // Total width including padding
        const val TOTAL_BUTTON_WIDTH = BUTTON_WIDTH + (BUTTON_PADDING * 2)
    }

    override fun getListCellRendererComponent(
        list: JList<out FileListItem>,
        value: FileListItem,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val panel = JPanel(BorderLayout())
        panel.border = BorderFactory.createEmptyBorder(4, 8, 4, 8)

        // File name label
        val label = JBLabel(value.displayName)
        panel.add(label, BorderLayout.CENTER)

        // Delete button
        val deleteButton = JButton(AllIcons.Actions.Close)
        deleteButton.preferredSize = Dimension(BUTTON_WIDTH, BUTTON_HEIGHT)
        deleteButton.minimumSize = Dimension(BUTTON_WIDTH, BUTTON_HEIGHT)
        deleteButton.maximumSize = Dimension(BUTTON_WIDTH, BUTTON_HEIGHT)
        deleteButton.border = BorderFactory.createEmptyBorder(2, 2, 2, 2)
        deleteButton.isOpaque = false
        deleteButton.isFocusable = false

        // Create a wrapper panel for the button with padding
        val buttonPanel = JPanel(BorderLayout())
        buttonPanel.isOpaque = false
        buttonPanel.border = BorderFactory.createEmptyBorder(0, BUTTON_PADDING, 0, BUTTON_PADDING)
        buttonPanel.add(deleteButton, BorderLayout.CENTER)

        panel.add(buttonPanel, BorderLayout.EAST)

        // Set background colors based on selection
        if (isSelected) {
            panel.background = list.selectionBackground
            label.foreground = list.selectionForeground
        } else {
            panel.background = list.background
            label.foreground = list.foreground
        }

        return panel
    }
}