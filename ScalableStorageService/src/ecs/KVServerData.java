package ecs;

public class KVServerData {
	
	String name;
	String ip;
	String port;
	String position;
	
	boolean running;
	boolean accepting;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isAccepting() {
		return accepting;
	}
	public void setAccepting(boolean accepting) {
		this.accepting = accepting;
	}
	
}