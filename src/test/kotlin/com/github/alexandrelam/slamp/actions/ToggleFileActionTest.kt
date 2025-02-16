package com.github.alexandrelam.slamp.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.github.alexandrelam.slamp.services.ClipboardService
import com.github.alexandrelam.slamp.services.FileCollectorService
import com.intellij.icons.AllIcons
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*  // Use mockito-kotlin instead

class ToggleFileActionTest {

 private lateinit var event: AnActionEvent
 private lateinit var project: Project
 private lateinit var virtualFile: VirtualFile
 private lateinit var fileCollectorService: FileCollectorService
 private lateinit var clipboardService: ClipboardService
 private lateinit var presentation: Presentation
 private lateinit var action: ToggleFileAction

 @Before
 fun setUp() {
  // Create mocks using mockito-kotlin
  event = mock()
  project = mock()
  virtualFile = mock()
  fileCollectorService = mock()
  clipboardService = mock()
  presentation = mock()
  action = ToggleFileAction()

  // Setup common mocks
  whenever(event.project).thenReturn(project)
  whenever(event.getData(CommonDataKeys.VIRTUAL_FILE)).thenReturn(virtualFile)
  whenever(event.presentation).thenReturn(presentation)
  whenever(project.getService(FileCollectorService::class.java)).thenReturn(fileCollectorService)
  whenever(project.getService(ClipboardService::class.java)).thenReturn(clipboardService)
 }

 @Test
 fun `test update - should disable when project is null`() {
  whenever(event.project).thenReturn(null)

  action.update(event)

  verify(presentation).isEnabledAndVisible = false
 }

 @Test
 fun `test update - should disable when file is null`() {
  whenever(event.getData(CommonDataKeys.VIRTUAL_FILE)).thenReturn(null)

  action.update(event)

  verify(presentation).isEnabledAndVisible = false
 }

 @Test
 fun `test update - should enable and show add icon when file not in collection`() {
  whenever(fileCollectorService.getFiles()).thenReturn(emptyList())

  action.update(event)

  verify(presentation).isEnabledAndVisible = true
  verify(presentation).icon = AllIcons.General.Add
 }

 @Test
 fun `test update - should enable and show remove icon when file in collection`() {
  whenever(fileCollectorService.getFiles()).thenReturn(listOf(virtualFile))

  action.update(event)

  verify(presentation).isEnabledAndVisible = true
  verify(presentation).icon = AllIcons.General.Remove
 }

 @Test
 fun `test actionPerformed - should add file when not in collection`() {
  whenever(fileCollectorService.getFiles()).thenReturn(emptyList())

  action.actionPerformed(event)

  verify(fileCollectorService).addFile(virtualFile)
  verify(presentation).icon = AllIcons.General.Remove
  verify(clipboardService).updateClipboardContent(any())
 }

 @Test
 fun `test actionPerformed - should remove file when in collection`() {
  whenever(fileCollectorService.getFiles()).thenReturn(listOf(virtualFile))

  action.actionPerformed(event)

  verify(fileCollectorService).removeFile(virtualFile)
  verify(presentation).icon = AllIcons.General.Add
  verify(clipboardService).updateClipboardContent(any())
 }

 @Test
 fun `test actionPerformed - should do nothing when project is null`() {
  whenever(event.project).thenReturn(null)

  action.actionPerformed(event)

  verifyNoInteractions(fileCollectorService, clipboardService)
 }

 @Test
 fun `test actionPerformed - should do nothing when file is null`() {
  whenever(event.getData(CommonDataKeys.VIRTUAL_FILE)).thenReturn(null)

  action.actionPerformed(event)

  verifyNoInteractions(fileCollectorService, clipboardService)
 }
}