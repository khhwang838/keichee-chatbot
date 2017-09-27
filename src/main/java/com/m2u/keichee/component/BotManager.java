package com.m2u.keichee.component;

import java.util.HashMap;
import java.util.Map;

import org.alicebot.ab.constants.MagicBooleans;
import org.alicebot.ab.constants.MagicStrings;
import org.alicebot.ab.core.Bot;
import org.alicebot.ab.core.Chat;
import org.alicebot.ab.core.Graphmaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.m2u.keichee.IConstants;

public class BotManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final BotManager instance = new BotManager();
	
	private final Map<String, Bot> botPool = new HashMap<String, Bot>();
	private Chat chat;
	
	public void addBot(Bot bot) {
		logger.debug("Adding bot named [{}].", bot.name);
		botPool.put(bot.name, bot);
	}
	
	public Bot getBot(String botName) {
		logger.debug("Getting a bot from the pool.");
		if (botName == null || "".equals(botName)) {
			return getDefaultBot();
		}
//		logger.debug("Returning a bot named [{}].", bot.name);
		return botPool.get(botName);
	}
	
	private Bot getDefaultBot() {
		return botPool.get(IConstants.DEFAULT.BOT_NAME);
	}
	
	public void createDefaultBot() {
		logger.info(MagicStrings.programNameVersion);
		logger.debug("Creating a default bot.");
		
		String botName = "alice2";
		String action = "chat";

		logger.info("trace mode = " + MagicBooleans.trace_mode);
		Graphmaster.enableShortCuts = true;
//		Bot bot = new Bot(botName, MagicStrings.root_path, action);
		Bot bot = new Bot(botName, "D:\\kihyun\\git\\keichee-chatbot\\src\\main\\resources", action);
		botPool.put(botName, bot);
		
		if (bot.brain.getCategories().size() < 100)
			bot.brain.printgraph();
		
		chat = new Chat(bot);
	}

	public Chat getChat() {
		return this.chat;
	}
}
