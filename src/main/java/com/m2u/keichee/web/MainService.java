package com.m2u.keichee.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.alicebot.ab.aiml.Category;
import org.alicebot.ab.constants.MagicBooleans;
import org.alicebot.ab.constants.MagicStrings;
import org.alicebot.ab.core.AB;
import org.alicebot.ab.core.Bot;
import org.alicebot.ab.core.Chat;
import org.alicebot.ab.core.Graphmaster;
import org.alicebot.ab.utils.IOUtils;
import org.alicebot.ab.utils.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.m2u.keichee.domain.Message;

@Service
public class MainService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public String mainFunction(Message msg, String[] options) {
		String botName = "alice2";
		String action = "chat";

		logger.info(MagicStrings.programNameVersion);

		for (String s : options) {
			logger.info(s);
			String[] splitArg = s.split("=");
			if (splitArg.length >= 2) {
				String option = splitArg[0];
				String value = splitArg[1];
				if (option.equals("bot"))
					botName = value;
				if (option.equals("action"))
					action = value;
				if (option.equals("trace") && value.equals("true"))
					MagicBooleans.trace_mode = true;
				else
					MagicBooleans.trace_mode = false;
			}
		}
		logger.info("trace mode = " + MagicBooleans.trace_mode);
		Graphmaster.enableShortCuts = true;
//		Bot bot = new Bot(botName, MagicStrings.root_path, action);
		Bot bot = new Bot(botName, "D:\\kihyun\\workspace\\KeicheeBot\\src\\main\\resources", action);

		if (bot.brain.getCategories().size() < 100)
			bot.brain.printgraph();
		return testChat(msg, bot, MagicBooleans.trace_mode);
	}
//	public void mainFunction(Message msg, String[] options) {
//		String botName = "alice2";
//		String action = "chat";
//
//		logger.info(MagicStrings.programNameVersion);
//
//		for (String s : options) {
//			logger.info(s);
//			String[] splitArg = s.split("=");
//			if (splitArg.length >= 2) {
//				String option = splitArg[0];
//				String value = splitArg[1];
//				if (option.equals("bot"))
//					botName = value;
//				if (option.equals("action"))
//					action = value;
//				if (option.equals("trace") && value.equals("true"))
//					MagicBooleans.trace_mode = true;
//				else
//					MagicBooleans.trace_mode = false;
//			}
//		}
//		logger.info("trace mode = " + MagicBooleans.trace_mode);
//		Graphmaster.enableShortCuts = true;
//		Timer timer = new Timer();
//		Bot bot = new Bot(botName, MagicStrings.root_path, action); //
//		// bot.preProcessor.normalizeFile("c:/ab/log1.txt", "c:/ab/data/lognormal.txt");
//		if (bot.brain.getCategories().size() < 100)
//			bot.brain.printgraph();
//		if (action.equals("chat"))
//			testChat(msg, bot, MagicBooleans.trace_mode);
//		else if (action.equals("test"))
//			testSuite(bot, MagicStrings.root_path + "/data/find.txt");
//		else if (action.equals("ab"))
//			testAB(bot);
//		else if (action.equals("aiml2csv") || action.equals("csv2aiml"))
//			convert(bot, action);
//		else if (action.equals("abwq"))
//			AB.abwq(bot);
//	}

	private void convert(Bot bot, String action) {
		if (action.equals("aiml2csv"))
			bot.writeAIMLIFFiles();
		else if (action.equals("csv2aiml"))
			bot.writeAIMLFiles();
	}

	private void testAB(Bot bot) {
		MagicBooleans.trace_mode = true;
		AB.ab(bot);
		AB.terminalInteraction(bot);
	}

	private String testChat(Message msg, Bot bot, boolean traceMode) {
		Chat chatSession = new Chat(bot);
		bot.brain.nodeStats();
		MagicBooleans.trace_mode = traceMode;
		String textLine = msg.getContent();

//		if (textLine == null || textLine.length() < 1)
//			textLine = MagicStrings.null_input;
//		if (textLine.equals("q")) {
//			System.exit(0);
//		} else if (textLine.equals("wq")) {
//			bot.writeQuit();
//			System.exit(0);
//		} else if (textLine.equals("ab")) {
//			testAB(bot);
//		} else {
			String request = textLine;
			logger.debug("STATE=" + request + ":THAT=" + chatSession.thatHistory.get(0).get(0) + ":TOPIC="
					+ chatSession.predicates.get("topic"));
			String response = chatSession.multisentenceRespond(request);
			while (response.contains("&lt;"))
				response = response.replace("&lt;", "<");
			while (response.contains("&gt;"))
				response = response.replace("&gt;", ">");
			logger.info("Robot: " + response);

			return response;
//		}
	}

	// private void testChat(Bot bot, boolean traceMode) {
	// Chat chatSession = new Chat(bot);
	// // bot.preProcessor.normalizeFile("c:/ab/bots/super/aiml/thats.txt",
	// // "c:/ab/bots/super/aiml/normalthats.txt");
	// bot.brain.nodeStats();
	// MagicBooleans.trace_mode = traceMode;
	// String textLine = "";
	// while (true) {
	// System.out.print("Human: ");
	// textLine = IOUtils.readInputTextLine();
	// if (textLine == null || textLine.length() < 1)
	// textLine = MagicStrings.null_input;
	// if (textLine.equals("q"))
	// System.exit(0);
	// else if (textLine.equals("wq")) {
	// bot.writeQuit();
	// System.exit(0);
	// } else if (textLine.equals("ab"))
	// testAB(bot);
	// else {
	// String request = textLine;
	// logger.debug("STATE=" + request + ":THAT=" +
	// chatSession.thatHistory.get(0).get(0) + ":TOPIC="
	// + chatSession.predicates.get("topic"));
	// String response = chatSession.multisentenceRespond(request);
	// while (response.contains("&lt;"))
	// response = response.replace("&lt;", "<");
	// while (response.contains("&gt;"))
	// response = response.replace("&gt;", ">");
	// logger.info("Robot: " + response);
	//
	// }
	// }
	// }

	private void testSuite(Bot bot, String filename) {
		try {
			AB.passed.readAIMLSet(bot);
			AB.testSet.readAIMLSet(bot);
			logger.info("Passed " + AB.passed.size() + " samples.");
			String textLine = "";
			Chat chatSession = new Chat(bot);
			FileInputStream fstream = new FileInputStream(filename);
			// Get the object
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			// Read File Line By Line
			int count = 0;
			HashSet<String> samples = new HashSet<String>();
			while ((strLine = br.readLine()) != null) {
				samples.add(strLine);
			}
			ArrayList<String> sampleArray = new ArrayList<String>(samples);
			Collections.sort(sampleArray);
			for (String request : sampleArray) {
				if (request.startsWith("Human: "))
					request = request.substring("Human: ".length(), request.length());
				Category c = new Category(0, bot.preProcessor.normalize(request), "*", "*", MagicStrings.blank_template,
						MagicStrings.null_aiml_file);
				if (AB.passed.contains(request))
					logger.info("--> Already passed " + request);
				else if (!bot.deletedGraph.existsCategory(c) && !AB.passed.contains(request)) {
					String response = chatSession.multisentenceRespond(request);
					logger.info(count + ". Human: " + request);
					logger.info(count + ". Robot: " + response);
					textLine = IOUtils.readInputTextLine();
					AB.terminalInteractionStep(bot, request, textLine, c);
					count += 1;
				}
			}
			// Close the input stream
			br.close();
		} catch (Exception e) {// Catch exception if any
			logger.error("testSuite Error: " + e, e);
		}
	}
}
