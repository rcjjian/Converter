package main;

public class Main {

	public static void main(String[] args) throws Exception {
		 int port = 8100; //启动socket端口 默认端口
		 if(args.length > 0 && args[0] != null) {
		 port = Integer.parseInt(args[0]); //启动socket端口
		 }
		 try {
		 MainControl.start(port);
		 }catch(Exception e) {
		 System.out.println(e);
		 }
	}

}
