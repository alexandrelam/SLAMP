<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# SLAMP Changelog

## [1.2.1] - 2025-02-16
### Added
- MIT License
### Changed
- Updated plugin logo
- Updated dependencies:
    - JetBrains/qodana-action to 2024.3
    - gradle/actions to 4
    - codecov/codecov-action to 5
    - org.jetbrains.intellij.platform to 2.2.1
    - org.jetbrains.qodana to 2024.3.4
    - org.gradle.toolchains.foojay-resolver-convention to 0.9.0

## [1.2.0] - 2025-02-16
### Added
- New "Copy to Clipboard" button in the tool window
- Delete buttons for individual files in the list
- Ktlint integration for code formatting and style enforcement
- Additional unit tests for actions
### Changed
- Switched from Mockito to MockK for Kotlin-friendly testing
- Improved code organization and structure
- Enhanced UI responsiveness and user interaction
- Updated file list display with better visual feedback
### Removed
- Unused MyBundle class and associated resource files
- Redundant code and unnecessary files
- Legacy tool window implementation

## [1.1.0] - 2025-02-15
### Changed
- Improved clipboard formatting to hide empty CODE/TEST sections
- Cleaner output by removing "No files in this section" placeholder
### Fixed
- Empty sections no longer appear in clipboard content
- More relevant content organization in clipboard

## [1.0.0] - 2025-02-15
### Added
- Quick toggle button in the main toolbar for adding/removing files
- File Collector tool window for managing selected files
- Double-click functionality to open files from the collection
- Clear all files button with visual feedback
- Automatic clipboard updates with formatted content
- Smart separation of code and test files

## [Unreleased]
### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)