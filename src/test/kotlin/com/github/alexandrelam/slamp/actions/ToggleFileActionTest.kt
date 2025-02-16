package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Before
import org.junit.Test

class ToggleFileActionTest {
    @MockK
    private lateinit var event: AnActionEvent

    @MockK
    private lateinit var project: Project

    @MockK
    private lateinit var virtualFile: VirtualFile

    @MockK
    private lateinit var fileCollectorService: FileCollectorService

    @MockK
    private lateinit var clipboardService: ClipboardService

    @MockK
    private lateinit var presentation: Presentation

    @InjectMockKs
    private lateinit var action: ToggleFileAction

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { event.project } returns project
        every { event.getData(CommonDataKeys.VIRTUAL_FILE) } returns virtualFile
        every { event.presentation } returns presentation
        every { project.getService(FileCollectorService::class.java) } returns fileCollectorService
        every { project.getService(ClipboardService::class.java) } returns clipboardService
        every { presentation.icon = any() } just runs
        every { presentation.isEnabledAndVisible = any() } just runs
    }

    @Test
    fun shouldDisableUpdateWhenProjectIsNull() {
        every { event.project } returns null

        action.update(event)

        verify { presentation.isEnabledAndVisible = false }
    }

    @Test
    fun shouldDisableUpdateWhenFileIsNull() {
        every { event.getData(CommonDataKeys.VIRTUAL_FILE) } returns null

        action.update(event)

        verify { presentation.isEnabledAndVisible = false }
    }

    @Test
    fun shouldEnableAndShowAddIconWhenFileNotInCollection() {
        every { fileCollectorService.getFiles() } returns emptyList()

        action.update(event)

        verifySequence {
            presentation.isEnabledAndVisible = true
            presentation.icon = AllIcons.General.Add
        }
    }

    @Test
    fun shouldEnableAndShowRemoveIconWhenFileInCollection() {
        every { fileCollectorService.getFiles() } returns listOf(virtualFile)

        action.update(event)

        verifySequence {
            presentation.isEnabledAndVisible = true
            presentation.icon = AllIcons.General.Remove
        }
    }

    @Test
    fun shouldAddFileWhenNotInCollection() {
        every { fileCollectorService.getFiles() } returns emptyList()
        every { fileCollectorService.addFile(any()) } just runs
        every { clipboardService.updateClipboardContent(any()) } just runs

        action.actionPerformed(event)

        verifySequence {
            fileCollectorService.getFiles() // First call to check if file is in collection
            fileCollectorService.addFile(virtualFile)
            presentation.icon = AllIcons.General.Remove
            fileCollectorService.getFiles() // Second call to get updated list for clipboard
            clipboardService.updateClipboardContent(any())
        }
    }

    @Test
    fun shouldRemoveFileWhenInCollection() {
        every { fileCollectorService.getFiles() } returns listOf(virtualFile)
        every { fileCollectorService.removeFile(any()) } just runs
        every { clipboardService.updateClipboardContent(any()) } just runs

        action.actionPerformed(event)

        verifySequence {
            fileCollectorService.getFiles() // First call to check if file is in collection
            fileCollectorService.removeFile(virtualFile)
            presentation.icon = AllIcons.General.Add
            fileCollectorService.getFiles() // Second call to get updated list for clipboard
            clipboardService.updateClipboardContent(any())
        }
    }

    @Test
    fun shouldDoNothingWhenProjectIsNull() {
        every { event.project } returns null

        action.actionPerformed(event)

        verify(exactly = 0) {
            fileCollectorService.addFile(any())
            fileCollectorService.removeFile(any())
            clipboardService.updateClipboardContent(any())
        }
    }

    @Test
    fun shouldDoNothingWhenFileIsNull() {
        every { event.getData(CommonDataKeys.VIRTUAL_FILE) } returns null

        action.actionPerformed(event)

        verify(exactly = 0) {
            fileCollectorService.addFile(any())
            fileCollectorService.removeFile(any())
            clipboardService.updateClipboardContent(any())
        }
    }
}