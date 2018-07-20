package socket;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketManager {

	private static SocketManager instance;

	private Map<String,SocketClient> map = new HashMap<String,SocketClient>();
	
	public static SocketManager getInstance() {
		if (instance == null) {
			instance = new SocketManager();
		}
		return instance;
	}

	private ExecutorService executorService;

	private SocketManager() {
		// 创建线程池
		this.executorService = Executors.newCachedThreadPool();
	}

	public void addSocket( Socket socket) {
		String clientId = "";
		SocketClient client = new SocketClient(socket);
		this.map.put(clientId, client);
		this.executorService.execute(client);
	}
	
	public void removeSocket(String clientId) {
		
	}
	
	public void sendMessage(String clientId,String msg) {
		SocketClient client = this.map.get(clientId);
		if(client != null) {
		}
	}
	
}
