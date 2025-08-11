# Login Logger Mod

[![Mod Version](https://img.shields.io/badge/Mod%20Version-1.0.0-blue)](https://github.com/shadowbq/loginlogger-fabricmod/releases)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.7-green)](https://minecraft.net)
[![Fabric Loader](https://img.shields.io/badge/Fabric%20Loader-0.16.14+-orange)](https://fabricmc.net)
[![Fabric API](https://img.shields.io/badge/Fabric%20API-0.129.0+-yellow)](https://modrinth.com/mod/fabric-api)
[![Java](https://img.shields.io/badge/Java-21+-red)](https://adoptium.net)
[![License](https://img.shields.io/badge/License-MIT-brightgreen)](https://github.com/shadowbq/loginlogger-fabricmod/blob/main/LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-success)](https://github.com/shadowbq/loginlogger-fabricmod)

A Minecraft Fabric mod that logs player login and logout events to a file, with special detection for first-time players.

## Features

- **Player Join Logging**: Records when players join the server
- **Player Leave Logging**: Records when players disconnect from the server  
- **First-Time Player Detection**: Automatically detects and logs when a player joins for the very first time
- **File-Based Logging**: Saves all events to `logs/login_usernames.log` with timestamps
- **Server-Side Only**: Lightweight mod that runs only on the server

## Requirements

- **Minecraft**: 1.21.7
- **Fabric Loader**: 0.16.14 or higher
- **Fabric API**: 0.129.0+1.21.7 or compatible version
- **Java**: 21 or higher

### For Development

- **Gradle**: 8.14.3 (via wrapper - automatically downloaded)
- **Git**: For cloning the repository

## Installation

1. Download the mod JAR file from the [Releases](../../releases) page
2. Place the JAR file in your server's `mods` folder
3. Ensure you have Fabric Loader and Fabric API installed
4. Start your Minecraft server

## Usage

The mod works automatically once installed. No configuration is required.

### Log Format

The mod creates log entries in the following format:

```text
[EVENT_TYPE] TIMESTAMP USERNAME
```

**Event Types:**

- `FIRST_JOIN`: Player joining for the first time
- `JOIN`: Regular player join
- `LEAVE`: Player disconnect

**Example log entries:**

```text
[FIRST_JOIN] 2025-07-27T20:15:30.123Z Steve
[JOIN] 2025-07-27T20:16:45.456Z Alex  
[LEAVE] 2025-07-27T20:25:10.789Z Steve
```

### Log File Location

Logs are saved to: `logs/login_usernames.log` (relative to server root directory)

## Building from Source

### Build Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/shadowbq/loginlogger-fabricmod.git
   cd loginlogger-fabricmod
   ```

2. Build the mod:

   ```bash
   ./gradlew build
   ```

3. The built JAR file will be located in `build/libs/`

### Understanding the Mod JAR

Your built JAR file (`loginlog-1.0.0.jar`) is a complete Minecraft Fabric mod. Here's what makes it work:

**JAR Contents:**

```text
META-INF/MANIFEST.MF
META-INF/
com/shadowbq/loginlog/LoginLogMod.class    # Compiled mod code
fabric.mod.json                            # Mod metadata (makes it a mod!)
```

**What makes it a mod:**

- `fabric.mod.json` - Tells Fabric Loader this is a mod and how to load it
- Compiled Java classes - Your mod's functionality
- Built with Fabric Loom - Properly configured for Minecraft

**Installation:**

1. Copy `build/libs/loginlog-1.0.0.jar` to your server's `mods/` folder
2. No renaming required - any `.jar` filename works
3. Fabric Loader automatically detects and loads it on server start

### Development Setup

1. Clone the repository
2. Import the project into your IDE (IntelliSJ IDEA, VS Code, etc.)
3. Run `./gradlew genSources` to generate Minecraft source mappings
4. The project uses Fabric Loom for development - refer to the [Fabric documentation](https://fabricmc.net/wiki/tutorial:setup) for more details

## Project Structure

```text
LoginLoggerMod/
├── src/main/java/com/shadowbq/loginlog/
│   └── LoginLogMod.java              # Main mod class
├── src/main/resources/
│   └── fabric.mod.json               # Mod metadata
├── src/test/java/com/shadowbq/loginlog/
│   └── LoginLogModTest.java          # Basic unit tests
├── build.gradle                      # Build configuration
├── settings.gradle                   # Gradle settings
├── README.md                         # This file
└── LICENSE                           # MIT License
```

## Other Useful Build Commands

```bash
# Just compile the code (faster)
./gradlew compileJava

# Run tests
./gradlew test

# Build without running tests
./gradlew build -x test

# Clean and rebuild everything
./gradlew clean build

# Show build tasks available
./gradlew tasks
```

## Technical Details

- **Mod ID**: `loginlog`
- **Main Class**: `com.shadowbq.loginlog.LoginLogMod`
- **Environment**: Server-side only
- **Dependencies**: Fabric API for server connection events

### Build Information

- **Gradle**: 8.14.3 (via wrapper)
- **Fabric Loom**: 1.10.4 (build plugin)
- **JUnit**: 5.10.1 (testing framework)

The mod uses Fabric's `ServerPlayConnectionEvents` to detect player connections and disconnections, and checks for the existence of player data files to determine first-time joins.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

If you encounter any issues or have suggestions, please [open an issue](../../issues) on GitHub.

## Changelog

### Version 1.0.0

- Initial release
- Basic join/leave logging functionality
- First-time player detection
- File-based log output with timestamps
