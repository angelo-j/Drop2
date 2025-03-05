
# Drop2 - A LibGDX Rain Catching Game

Drop2 is a simple yet engaging game developed using the LibGDX framework.  
The objective is to control a bucket to catch falling raindrops.  
The game features a main menu, options for sound and music control, a pause menu, and a high score tracking system.

---

## 📌 Features

- **Core Gameplay:**  
  - Control a bucket to catch falling raindrops.  
  - Beat your highest score before the timer hits zero.  
  - Potential for falling dangers or boosts like bombs and speed boosts.  
  - Variable raindrop speeds retained as they fall.  

- **Main Menu:**  
  - Start Game  
  - Options  
  - Quit  

- **Options Screen:**  
  - Volume controls for sound and music.  
  - Mute/unmute toggles.  
  - Reset high score option.  

- **Pause Menu:**  
  - Displays current and high scores.  
  - Resume or quit options.  

- **High Score System:**  
  - Tracks and saves the highest score locally.  
  - Option to reset the high score.  

---

## 🎮 Controls

| **Action**                | **Key**         |
|---------------------------|-----------------|
| Move Bucket               | Arrow Keys      |
| Open Pause Menu           | ESC             |
| Navigate Menus            | Mouse           |

---

## 📂 Assets

- **Visuals:** Stored in `/assets/visual/`  
- **UI Fonts:** Located in `/assets/ui/`  
- **Audio:** Music and sound effects in `/assets/audio/`  

---

## 🛠️ Technical Details

- **Language:** Java  
- **Framework:** LibGDX  
- **Project Structure:** Follows the new LibGDX structure with `lwjgl3`.  
- **Screen Management:** Uses `ScreenManager` for transitions.  
- **Sound Management:** Handled by `SoundManager`.  

---

## 🚀 How to Build

### 1. Clone the repository:

```bash
git clone https://github.com/angelo-j/Drop2
```

### 2. Import the project:
- Import into your IDE as a Gradle project.

### 3. Run the game:
- Run the desktop launcher from the `lwjgl3` package.

---

## 🗺️ Future Plans

- Additional obstacles with different speeds.  
- Expanded soundtracks and sound effects.  
- Improved UI and visual effects. 
- iOS compatibility. 
---

## 🖇️ Contributing

- Feel free to fork this repository and submit pull requests!  

---

## 📜 License

- The LibGDX framework this project uses is licensed under the [Apache License 2.0](LICENSE.txt).
- This source code does **not** have an open-source license.  
- Feel **free to view, play, and contribute**, but please ask for permission before using or redistributing the code.
- The assets (**images**, **fonts**, **audio**) are subject to their respective licenses and are used here with attribution.

---

## 📦 Asset Attribution

- All current **visuals** and **audio** assets used in this project belong to **LibGDX** and are used for educational and non-commercial purposes only.  
For more information about LibGDX and its assets, visit the [LibGDX Official Site](https://libgdx.com/).  
These assets are sourced from the [A Simple Game](https://libgdx.com/wiki/start/a-simple-game) tutorial.
  
- The font **"Sigmar-Regular.ttf"** is part of [Google Fonts](https://fonts.google.com/) and is used under the [Open Font License](OFL.txt).  
  For more information about the font, visit: [Sigmar on Google Fonts](https://fonts.google.com/specimen/Sigmar).  

---

## 🗂️ Platforms

- **`core`:** Main module with the application logic shared by all platforms.  
- **`lwjgl3`:** Primary desktop platform using LWJGL3; previously called 'desktop'.  
- **`ios`:** iOS mobile platform using RoboVM.  
- **`ios-moe`:** iOS mobile backend using Multi-OS Engine.  

---

## 🛠️ Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.  
The Gradle wrapper is included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.

### Useful Gradle Commands

- `--continue`: Continue tasks despite errors.  
- `--daemon`: Use Gradle daemon for tasks.  
- `--offline`: Use cached dependencies.  
- `--refresh-dependencies`: Validate all dependencies.  
- `build`: Compile and build archives.  
- `clean`: Remove build folders.  
- `lwjgl3:jar`: Build a runnable jar.  
- `lwjgl3:run`: Run the application.  

---

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).  
This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

---