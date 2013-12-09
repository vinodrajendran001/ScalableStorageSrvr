package ecs;

public interface KVActionsInterface {
	
	
	/* Randomly choose <numberOfNodes> servers from the available machines
	 * and start the KVServer by issuing a SSH call to the respective machine.
	 * This call launches the server. You can assume that the KVServer.jar
	 * is located in the same directory as the ECS. All servers are initialized
	 * with the meta-data and remain in state stopped. 
	 * */
	public void initService(int numberOfNodes) throws Exception ;
	
	/* Starts the storage service by calling start() on all KVServer
	 * instances that participate in the service.
	 * */
	
	public void start () throws Exception ;
	
	/* Stops the service; all participating KVServers are stopped for
     * processing client requests but the processes remain running. 
	 * */
	
	public void stop () throws Exception ;
	
	/*
	 * Stops all server instances and exits the remote processes.
	 */
	
	public void shutDown () throws Exception ;
	
	/*
	 * Add a new node to the storage service at an arbitrary position.
	 * */
	
	public void addNode() throws Exception ;
	
	/*
	 * Remove a node from the storage service at an arbitrary position.
	 * */
	
	public void removeNode () throws Exception ;
	
	

}