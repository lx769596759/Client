package tcpip;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;



public class RecieveData  implements Runnable{
	   Socket socket=null;
	   public RecieveData(Socket s)
	   {
		   this.socket=s;
	   }
	   
	@Override
	public void run() {
		 try {
		 InputStream is = socket.getInputStream();// 字节输入流
         InputStreamReader isr = new InputStreamReader(is);// 将字节流转为字符流
         BufferedReader br = new BufferedReader(isr);// 为输入流添加缓冲
         String info = null;
		   while ((info = br.readLine()) != null) {
			     System.out.println("我是服务器，客户端说" + info);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		  
   }
}
		


