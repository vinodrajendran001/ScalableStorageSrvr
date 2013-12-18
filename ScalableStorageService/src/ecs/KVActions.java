package ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import common.messages.Md5;

public class KVActions implements KVActionsInterface {
	
	private static Logger logger = Logger.getRootLogger();
	List <KVServerData> p = new ArrayList<KVServerData>();
	private Md5 hash = new Md5();

	public KVActions(List<KVServerData> p) {
		this.p = p;
	}

	@Override
	public void initService(int numberOfNodes) throws Exception {
		Process proc ;
		//String script = "../src/app_kvEcs/script.sh";
		String script = "C:\\Users\\User\\git\\ScalableStorageServer\\ScalableStorageService\\src\\app_kvEcs\\script.sh";
		System.out.println(script);
		Runtime run = Runtime.getRuntime();
		for (int i=0 ; i< numberOfNodes ; i ++) {
			proc = run.exec(script);
			p.get(i).setRunning(true);
			/*Now send the meta-data list to the remote server */
			
		}
		
	}

	@Override
	public void start() throws Exception {
		
		for (int i=0; i< p.size(); i++) {
			if (p.get(i).isRunning()) {
				p.get(i).setAccepting(true);
				/* Call start function */	
			}
		}
		/* If successfull then send the meta-data list to every remote server */
		 
	}

	@Override
	public void stop() throws Exception {
		
		for (int i=0; i< p.size(); i++) {
			if(p.get(i).isAccepting()) {
				p.get(i).setAccepting(true);
				/* Call stop function */
			}
		}
		/* If successfull then send the meta-data list to every remote server */
	}

	@Override
	public void shutDown() throws Exception {
		
		for (int i=0; i< p.size(); i++) {
			if(p.get(i).isRunning()) {
				p.get(i).setRunning(false);
				/* Call shutDown function*/
			}
		}	
	}

	@Override
	public void addNode() throws Exception {
		
		int counter = 0 ;
		Random randomGenerator = new Random();
		boolean newNodeFound = false ;
		
		Process proc ;
		String script = "./src/app_kvEcs/script.sh";
		Runtime run = Runtime.getRuntime();
		
		
		for (int i=0; i< p.size(); i++) {
			if(!p.get(i).isAccepting()) {
				counter ++ ;
			}
		}
		
		if (counter!=0) {
			do {
				int randomInt = randomGenerator.nextInt(p.size()) + 1 ;
				for (int i=0; i< p.size(); i++) {
					if((!p.get(i).isAccepting())&&(randomInt==i)) {
						newNodeFound=true;
						proc = run.exec(script);
						p.get(i).setRunning(true);
						p.get(i).setAccepting(true);
						p.get(i).setPosition(hash.hashIpPort(p.get(i).getIp(),p.get(i).getPort()));
						/*Initiate server with meta-data and start it using the correct command*/
						/*Update metadata*/
						/*Invoke command to update meta-data*/
						
					}
				}
				
			} while (newNodeFound=false);
			
		}
		else {
			logger.info("All KVServer instances are accepting connections , there are no new servers to be initiated ");
		}
		
	}

	@Override
	public void removeNode() throws Exception {
		// TODO Auto-generated method stub
		
	}

}