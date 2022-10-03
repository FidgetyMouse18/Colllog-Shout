package com.colllogshout;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import jaco.mp3.player.MP3Player;
import java.util.Locale;

@Slf4j
@PluginDescriptor(
	name = "Collection Log Shout"
)
public class ColllogShoutPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ColllogShoutConfig config;

	private MP3Player trackPlayer = new MP3Player(getClass().getClassLoader().getResource("COLLECTION_LOG.mp3"));
	@Override
	protected void startUp() throws Exception
	{
		log.info("ColllogShout started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("ColllogShout stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}

	@Subscribe
	public void onMenuOpened(MenuOpened event) {
		if (event.getMenuEntries().length < 2)
		{
			return;
		}

		MenuEntry entry = event.getMenuEntries()[1];

		String entryTarget = entry.getTarget();
		if (entryTarget.equals(""))
		{
			entryTarget = entry.getOption();
		}

		if (!entryTarget.toLowerCase(Locale.ROOT).endsWith("Collection log".toLowerCase(Locale.ROOT)))
		{
			return;
		}

		trackPlayer.setVolume(config.volume());
		trackPlayer.play();
	}

	@Provides
	ColllogShoutConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ColllogShoutConfig.class);
	}
}
