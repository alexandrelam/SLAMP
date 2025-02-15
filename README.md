# SLAMP - Smart List And Model Prompter

![Build](https://github.com/alexandrelam/SLAMP/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

SLAMP is an IntelliJ IDEA plugin that streamlines the process of collecting and formatting code for LLM prompts. It helps developers easily gather multiple files and their contents in a structured format, perfect for asking questions about code to AI assistants.

## 🚀 Features

### File Collection
- Quick toggle button in the main toolbar to add/remove files
- Visual feedback with Add/Remove icons
- Automatic path shortening for better readability
- Support for multiple files

### Smart Organization
- Automatic separation of code and test files
- Maintains full relative paths for context
- Clear section separation with [CODE] and [TEST] tags
- Dedicated [INSTRUCTION] section for prompts

### Clipboard Management
- Automatic clipboard updates when files change
- Structured format perfect for LLM prompts
- Clear notifications for all clipboard actions
- One-click list clearing

## 🎯 Usage

1. **Adding Files**
  - Click the toggle button in the main toolbar while viewing a file
  - The icon will change to indicate if the file is in your collection

2. **Viewing Collection**
  - Open the "File Collector" tool window
  - See all collected files with their paths
  - Double-click any file to open it

3. **Managing Files**
  - Clear all files using the "Clear Files" button
  - Files are automatically formatted when copied
  - Clipboard updates automatically with changes

## ⚙️ Installation

### From JetBrains Marketplace
1. Open IntelliJ IDEA
2. Go to `Settings/Preferences` > `Plugins` > `Marketplace`
3. Search for "SLAMP"
4. Click `Install`

### Manual Installation
1. Download the [latest release](https://github.com/alexandrelam/SLAMP/releases/latest)
2. In IntelliJ IDEA, go to `Settings/Preferences` > `Plugins` > `⚙️` > `Install plugin from disk...`
3. Select the downloaded file

## 🛠️ Development

### Prerequisites
- Java 17+
- IntelliJ IDEA
- Kotlin development environment

### Building
```bash
./gradlew build