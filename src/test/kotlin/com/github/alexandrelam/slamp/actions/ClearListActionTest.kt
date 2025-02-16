package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Before
import org.junit.Test

class ClearListActionTest {
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

    @InjectMockKs
    private lateinit var action: ClearListAction

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { event.project } returns project
        every { event.presentation } returns presentation
        every { project.getService(FileCollectorService::class.java) } returns fileCollectorService
        every { project.getService(ClipboardService::class.java) } returns clipboardService
        every { presentation.isEnabled = any() } just runs
        every { presentation.setText(any<String>()) } just runs
        every { fileCollectorService.clearFiles() } just runs
        every { clipboardService.updateClipboardContent(any()) } just runs
    }

    @Test
    fun shouldDisableWhenListIsEmpty() {
        every { fileCollectorService.getFiles() } returns emptyList()

        action.update(event)

        verify {
            presentation.isEnabled = false
            presentation.setText("Clear Files")
        }
    }

    @Test
    fun shouldEnableWhenListIsNotEmpty() {
        every { fileCollectorService.getFiles() } returns listOf(virtualFile)

        action.update(event)

        verify {
            presentation.isEnabled = true
            presentation.setText("Clear Files")
        }
    }

    @Test
    fun shouldClearFilesAndUpdateClipboard() {
        action.actionPerformed(event)

        verifySequence {
            fileCollectorService.clearFiles()
            clipboardService.updateClipboardContent(emptyList())
        }
    }

    @Test
    fun shouldDoNothingWhenProjectIsNull() {
        every { event.project } returns null

        action.actionPerformed(event)

        verify(exactly = 0) {
            fileCollectorService.clearFiles()
            clipboardService.updateClipboardContent(any())
        }
    }
}