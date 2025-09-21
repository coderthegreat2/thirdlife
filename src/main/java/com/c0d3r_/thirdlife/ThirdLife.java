package com.c0d3r_.thirdlife;

import com.c0d3r_.thirdlife.command.Commands;
import com.c0d3r_.thirdlife.event.Events;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThirdLife implements ModInitializer {
	public static final String MOD_ID = "thirdlife";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Events.register();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			Commands.register(dispatcher);
		});

		LOGGER.info("ThirdLife Initialized");
	}
}