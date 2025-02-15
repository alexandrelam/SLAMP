<!-- Plugin description -->
<div align="center">
  <img src="https://github.com/user-attachments/assets/c8a23542-35c4-42b5-9d6f-c56eae9eb843" alt="SLAMP Logo" width="180"/>

  <h1>sLAMp</h1>
  <h3>Smart List And Model Prompter</h3>

  <p align="center">
    <strong>Streamline your LLM prompting workflow in IntelliJ IDEA</strong>
  </p>

  <p align="center">
    <a href="https://github.com/alexandrelam/SLAMP/actions"><img src="https://github.com/alexandrelam/SLAMP/workflows/Build/badge.svg" alt="Build Status"></a>
    <a href="https://plugins.jetbrains.com/plugin/MARKETPLACE_ID"><img src="https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg" alt="Version"></a>
    <a href="https://plugins.jetbrains.com/plugin/MARKETPLACE_ID"><img src="https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg" alt="Downloads"></a>
    <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT"></a>
  </p>

  <br/>

  <p align="center">
    <img src="screenshot-or-demo-gif-here" width="700" alt="SLAMP Demo"/>
  </p>
</div>

## ✨ Overview

SLAMP is your intelligent companion for crafting perfect LLM prompts. It helps you collect, organize, and format your code snippets with ease, making interactions with AI assistants more productive than ever.

## 🚀 Features

<div align="center">
  <table>
    <tr>
      <td align="center">
        <img width="256" src="https://github.com/user-attachments/assets/b53bbec0-bc8e-401d-84fe-220f7172c5b2" alt="Collection"><br/>
        <strong>Smart Collection</strong>
      </td>
      <td align="center">
        <img width="256" src="https://github.com/user-attachments/assets/b5fedd9b-d436-4c3c-bbae-7080073d8cbf" alt="Organization"><br/>
        <strong>Auto Organization</strong>
      </td>
      <td align="center">
        <img width="256" src="https://github.com/user-attachments/assets/be1c9359-40d3-404a-920b-af39b9d723cf" alt="Clipboard"><br/>
        <strong>Clipboard Magic</strong>
      </td>
    </tr>
  </table>
</div>

### 📋 File Collection
- Quick toggle button in the main toolbar
- Visual feedback with Add/Remove icons
- Automatic path shortening
- Multi-file support

### 🎯 Smart Organization
- Auto-separation of code and test files
- Full relative paths preserved
- Clear [CODE] and [TEST] sections
- Ready-to-use [INSTRUCTION] prompt section

### ⚡ Clipboard Management
- Real-time clipboard updates
- LLM-optimized formatting
- Clear action notifications
- One-click clearing

## 🎮 Quick Start

<table>
<tr>
<td>

### 1. Add Files
```
Click the toolbar toggle button
while viewing any file
```

</td>
<td>

### 2. View Collection
```
Open File Collector window to
see your gathered files
```

</td>
<td>

### 3. Use Content
```
Content auto-copied and
formatted for LLM prompts
```

</td>
</tr>
</table>

## ⚙️ Installation

<details>
<summary>From JetBrains Marketplace</summary>

1. Open IntelliJ IDEA
2. Navigate to: `Settings/Preferences` > `Plugins` > `Marketplace`
3. Search for "SLAMP"
4. Click `Install`

</details>

<details>
<summary>Manual Installation</summary>

1. Download the [latest release](https://github.com/alexandrelam/SLAMP/releases/latest)
2. In IntelliJ IDEA:
   - Go to `Settings/Preferences` > `Plugins`
   - Click ⚙️ > `Install plugin from disk...`
   - Select the downloaded file

</details>

## 🛠️ Development

```bash
# Clone the repository
git clone https://github.com/alexandrelam/SLAMP.git

# Build the plugin
./gradlew build

# Run with IDE for testing
./gradlew runIde
```

## 📝 Output Format

```
[CODE]
// src/main/kotlin/com/example/MyClass.kt
class MyClass {
    // ... code content
}

[TEST]
// src/test/kotlin/com/example/MyClassTest.kt
class MyClassTest {
    // ... test content
}

[INSTRUCTION]
```

<div align="center">

## 💖 Support & Contributions

<p>
  <a href="https://github.com/alexandrelam/SLAMP/issues">Report Bug</a>
  ·
  <a href="https://github.com/alexandrelam/SLAMP/issues">Request Feature</a>
  ·
  <a href="https://github.com/alexandrelam/SLAMP/pulls">Submit PR</a>
</p>

<p>
  <strong>Star this repo if you found it useful! ⭐</strong>
</p>

---

<sub>Built with ❤️ by <a href="https://github.com/alexandrelam">Alexandre Lam</a></sub>

</div>
<!-- Plugin description end -->
