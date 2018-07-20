package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import converter.base.IConverter;
import converter.cmd.ConveterCommand;
import converter.cmd.ResposeCode;
import converter.controller.ConverterController;
import task.ConverterTask;
import task.manager.TaskManager;
import utils.JSONUtils;

/***
 * 
 * @author Socket 链接的客户端实例
 *
 */
public class SocketClient implements Runnable {

	private Socket socket = null;

	private String socketId = null;

	private boolean isConnect = false;

	private TaskManager taskMgr = new TaskManager();

	private ResposeCode responseCode = new ResposeCode();

	private PrintStream out = null;

	private Timer timer = null;

	private final long INTERVAL = 1500; // 每隔 一段时间 检查是否有任务完成

	public SocketClient(Socket socket) {
		this.socket = socket;
	}

	public String getSocketId() {
		return this.socketId;
	}

	public void run() {
		this.isConnect = true;
		BufferedReader buf = null;
		try {
			// 获取Socket的输出流，用来向客户端发送数据
			out = new PrintStream(this.socket.getOutputStream());
			// 获取Socket的输入流，用来接收从客户端发送过来的数据
			buf = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.listener();
		try {
			while (this.isConnect) {
				// 接收从客户端发送过来的数据
				String jsonStr = buf.readLine();
				System.out.println("收到来自客户端的一条信息：");
				JSONObject jsonObj = JSONUtils.parse(jsonStr);
				ConveterCommand cmd = new ConveterCommand(jsonObj);
				String action = cmd.getAction();
				if (action.equals("play")) {
					IConverter converter = ConverterController.getConveter(cmd.getInputPath(), cmd.getOutputPath());
					taskMgr.addTask(new ConverterTask(converter), cmd.getId());
				} else if (action.equals("stop")) {
					taskMgr.removeTask(cmd.getId());
				}
				this.responseClient(ResposeCode.OK, null);
			}
			out.close();
			buf.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.responseClient(ResposeCode.ERROR_REQUEST, e.toString());
		} finally {
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * 响应客户端
	 */
	private void responseClient(int code, String msg) {
		this.responseClient(code, msg, null);
	}

	private void responseClient(int code, String msg, Map<String, String> map) {
		if (out != null) {
			// 回复客户端已接受命令
			responseCode.setBody(code, msg, map);
			out.println(responseCode.toJSONString());
			out.flush();
		}
	}

	/***
	 * 定时监听任务情况
	 */
	private void listener() {

		if (timer == null)
			timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Set<String> set = taskMgr.getWorkingTasks();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String taskId = iterator.next();
					if (taskMgr.isDone(taskId) == 1) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", taskId);
						responseClient(ResposeCode.OK, "convert task done", map);
						taskMgr.removeTask(taskId);
					}
				}
			}
		}, 0, INTERVAL);
	}

}
