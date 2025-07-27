package com.shadowbq.loginlog;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.UUID;

public class LoginLogMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("loginlog");

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            String username = handler.getPlayer().getName().getString();
            UUID uuid = handler.getPlayer().getUuid();
            Path playerData = server.getSavePath(WorldSavePath.PLAYERDATA).resolve(uuid.toString() + ".dat");
            boolean isFirstJoin = !Files.exists(playerData);

            if (isFirstJoin) {
                LOGGER.info("User joined for the FIRST TIME: " + username);
                writeToLog("FIRST_JOIN", username);
            } else {
                LOGGER.info("User joined: " + username);
                writeToLog("JOIN", username);
            }
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            String username = handler.getPlayer().getName().getString();
            LOGGER.info("User left: " + username);
            writeToLog("LEAVE", username);
        });
    }

    private void writeToLog(String event, String username) {
        Path outputPath = Paths.get("logs", "login_usernames.log");
        try {
            Files.createDirectories(outputPath.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                writer.write(String.format("[%s] %s %s", event, Instant.now().toString(), username));
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to write to login log", e);
        }
    }
}