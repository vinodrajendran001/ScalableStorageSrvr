package app_kvEcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import logger.LogSetup;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import common.messages.Md5;

import ecs.KVActions;
import ecs.KVServerData;

public class ECSClient {

	private static Logger logger = Logger.getRootLogger();
	private static final String PROMPT = "ECS> ";
	private String ecsConfig;
	private boolean stop = false;
	List<KVServerData> p = new ArrayList<KVServerData>();
	private Md5 hash = new Md5();
	KVActions kvActions = null;

	public ECSClient(String filename) {
		this.ecsConfig = filename;
	}

	public void run() {

		BufferedReader br = null;
		BufferedReader stdin = null;
		String sCurrentLine;

		try {
			br = new BufferedReader(new FileReader(ecsConfig));
			while ((sCurrentLine = br.readLine()) != null) {

				String[] tokens = sCurrentLine.split("\\s+");
				KVServerData temp = new KVServerData();
				temp.setName(tokens[0]);
				temp.setIp(tokens[1]);
				temp.setPort(tokens[2]);
				temp.setPosition(hash.hashIpPort(tokens[1], tokens[2]));
				temp.setRunning(false);
				temp.setAccepting(false);
				p.add(temp);
				logger.info("server info successfully inserted");
			}
			while (!stop) {
				stdin = new BufferedReader(new InputStreamReader(System.in));
				System.out.print(PROMPT);

				String cmdLine = stdin.readLine();
				this.handleCommand(cmdLine);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleCommand(String cmdLine) {

		Random randomGenerator = new Random();
		String[] tokens = cmdLine.split("\\s+");

		if (tokens[0].equals("initService")) {
			logger.info("Received " + tokens[0].toString());
			int no_nodes = Integer.parseInt(tokens[1]);
			for (int i = 1; i <= no_nodes; i++) {
				int randomInt = randomGenerator.nextInt(p.size()) + 1;
				kvActions = new KVActions(p);
				try {
					BufferedReader br = new BufferedReader(new FileReader(
							"app_kvEcs/config.txt"));
					String line = null;
					int port = 0;
					while ((line = br.readLine()) != null) {
						if (line.contains("node" + randomInt)) {
							port = Integer.parseInt(line.split("\\s+")[2]);
						}
					}
					br.close();
					kvActions.initService(randomInt, port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (tokens[0].equals("start")) {
			if (tokens.length == 1) {
				logger.info("Received " + tokens[0].toString());
				kvActions = new KVActions(p);
				try {
					kvActions.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else if (tokens[0].equals("stop")) {
			if (tokens.length == 1) {
				logger.info("Received " + tokens[0].toString());
				kvActions = new KVActions(p);
				try {
					kvActions.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else if (tokens[0].equals("shutDown")) {
			if (tokens.length == 1) {
				logger.info("Received " + tokens[0].toString());
				kvActions = new KVActions(p);
				try {
					kvActions.shutDown();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else if (tokens[0].equals("addNode")) {
			if (tokens.length == 1) {
				logger.info("Received " + tokens[0].toString());
				kvActions = new KVActions(p);
				try {
					kvActions.addNode();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else if (tokens[0].equals("remmoveNode")) {
			if (tokens.length == 1) {
				logger.info("Received " + tokens[0].toString());
				kvActions = new KVActions(p);
				try {
					kvActions.removeNode();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else {
			logger.warn("Received " + tokens[0].toString());
			printError("Unknown command");
		}

	}

	private void printError(String error) {
		System.out.println(PROMPT + "Error! " + error);
	}

	public static void main(String[] args) {

		try {
			String argument = null;
			new LogSetup("logs/ecs/ecs.log", Level.OFF);
			if (args.length == 1) {
				argument = args[0].toString();
			} else {
				System.out.println("Wrong input parameters");
			}
			ECSClient ecsClient = new ECSClient(argument);
			ecsClient.run();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}