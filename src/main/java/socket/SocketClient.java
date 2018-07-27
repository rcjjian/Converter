package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import converter.base.IConverter;
import converter.cmd.ConveterCommand;
import converter.cmd.ResposeCode;
import converter.controller.ConverterController;
import task.ConverterTask;
import task.base.BaseTask;
import task.base.ITaskCallback;
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
					Map<String, String> externalMap = new HashMap<String, String>();
					externalMap.put("externalId", cmd.getExternalId());
					
					ConverterTask converterTask = new ConverterTask(converter, new ITaskCallback() {
						public void callback(BaseTask task) { // 执行完毕的回调
							ConverterTask conTask = (ConverterTask)task;
							Map<String, String> map = new HashMap<String, String>();
							map.put("taskId", conTask.getTaskId());	
							map.put("externalId", conTask.getExternalMap().get("externalId"));	
							if(conTask.getStatus() == 1) { //完成任务
								responseClient(ResposeCode.EXECUTE_SUCCESS, "convert task done", map);
							}else if(conTask.getStatus() == -1) { //中断任务
								responseClient(ResposeCode.EXECUTE_STOP, "convert task stop",map);
							}
							taskMgr.removeTask(conTask.getTaskId());
						}
					},externalMap);
					
					taskMgr.addTask(converterTask);
					Map<String, String> map = new HashMap<String, String>();
					map.put("taskId", converterTask.getTaskId());
					this.responseClient(ResposeCode.OK, null, map);
				} else if (action.equals("stop")) {
					taskMgr.stopTask(cmd.getTaskId());
				}
			}
			out.close();
			buf.close();
		} catch (SocketException e) { // 断开链接
			e.printStackTrace();
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
}
