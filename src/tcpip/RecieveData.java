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
		 InputStream is = socket.getInputStream();// �ֽ�������
         InputStreamReader isr = new InputStreamReader(is);// ���ֽ���תΪ�ַ���
         BufferedReader br = new BufferedReader(isr);// Ϊ��������ӻ���
         String info = null;
		   while ((info = br.readLine()) != null) {
			     System.out.println("���Ƿ��������ͻ���˵" + info);
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
		


