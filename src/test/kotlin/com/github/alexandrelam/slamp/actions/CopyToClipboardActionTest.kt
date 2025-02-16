package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class CopyToClipboardActionTest {
    @MockK
    private lateinit var event: AnActionEvent

    @MockK
    private lateinit var project: Project

    @MockK
    private lateinit var fileCollectorService: FileCollectorService

    @MockK
    private lateinit var clipboardService: ClipboardService

    @MockK
    private lateinit var presentation: Presentation

    @MockK
    private lateinit var virtualFile: VirtualFile

    private lateinit var action: CopyToClipboardAction

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { event.project } returns project
        every { event.presentation } returns presentation
        every { project.getService(FileCollectorService::class.java) } returns fileCollectorService
        every { project.getService(ClipboardService::class.java) } returns clipboardService
        every { presentation.isEnabled = any() } just runs
        every { presentation.setText(any<String>()) } just runs
        every { clipboardService.updateClipboardContent(any()) } just runs

        action = CopyToClipboardAction(project)
    }

    @Test
    fun shouldDisableWhenListIsEmpty() {
        every { fileCollectorService.getFiles() } returns emptyList()

        action.update(event)

        verify {
            presentation.isEnabled = false
            presentation.setText("Copy to Clipboard")
        }
    }

    @Test
    fun shouldEnableWhenListIsNotEmpty() {
        every { fileCollectorService.getFiles() } returns listOf(virtualFile)

        action.update(event)

        verify {
            presentation.isEnabled = true
            presentation.setText("Copy to Clipboard")
        }
    }

    @Test
    fun shouldCopyFilesToClipboard() {
        val files = listOf(virtualFile)
        every { fileCollectorService.getFiles() } returns files

        action.actionPerformed(event)

        verify {
            fileCollectorService.getFiles()
            clipboardService.updateClipboardContent(files)
        }
    }

    @Test
    fun shouldDoNothingWhenProjectIsNull() {
        every { event.project } returns null

        action.actionPerformed(event)

        verify(exactly = 0) {
            fileCollectorService.getFiles()
            clipboardService.updateClipboardContent(any())
        }
    }
}