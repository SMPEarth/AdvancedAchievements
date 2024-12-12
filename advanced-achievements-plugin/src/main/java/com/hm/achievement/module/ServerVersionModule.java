package com.hm.achievement.module;

import javax.inject.Singleton;

import org.bukkit.Bukkit;
import dagger.Module;
import dagger.Provides;
import java.util.logging.Logger;

@Module
public class ServerVersionModule {

	private static final Logger logger = Bukkit.getLogger();

	@Provides
	@Singleton
	int provideServerVersion() {
		// Use the Bukkit API to get the server version
		String versionString = Bukkit.getBukkitVersion();
		logger.info("Bukkit version: " + versionString);

		// Example version string format: "1.21.1-R0.1-SNAPSHOT"
		String[] versionParts = versionString.split("\\.");

		// Ensure the major and minor versions are present
		if (versionParts.length < 2) {
			logger.severe("Invalid version format: " + versionString);
			throw new IllegalArgumentException("Unable to parse server version from: " + versionString);
		}

		try {
			// Parse the major version (e.g., "1") and minor version (e.g., "21")
			int majorVersion = Integer.parseInt(versionParts[0]);
			int minorVersion = Integer.parseInt(versionParts[1].split("-")[0]);

			// Combine major and minor version (e.g., "1.21" becomes 121 for comparison)
			int version = majorVersion * 100 + minorVersion;
			logger.info("Parsed server version: " + version);

			return version;
		} catch (NumberFormatException e) {
			logger.severe("Failed to parse version numbers: " + e.getMessage());
			throw new IllegalArgumentException("Unable to parse server version from: " + versionString, e);
		}
	}
}
