package tcpip;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import Client.Client;


public class SendData  implements Runnable{ 	  	  
	Socket socket = null;
	
	public SendData(Socket socket){
		 this.socket=socket;
		};
		
	public SendData(){};
	//���������������
	public void sendData(String cmd) {	 		   
			try {
			   //Socket socket=new Socket(Client.textField_1.getText(),8888);
			   OutputStream os = socket.getOutputStream();			
	           PrintWriter pw = new PrintWriter(os);// ��װ��ӡ��
	           pw.write(cmd+"\n");
	           pw.flush();
	           try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Client.logger.error(e.getMessage(),e);
			}
	           //socket.shutdownOutput();
	           //pw.close();
			} catch (IOException e) {
				Client.logger.error(e.getMessage(),e);
			}
	  	}
	@Override
	public void run() {
		OutputStream os=null;
	    PrintWriter pw=null;
		try {
			os = socket.getOutputStream();
		} catch (IOException e) {
			Client.logger.error(e.getMessage(),e);
		}
        pw = new PrintWriter(os);
        pw.write("��ӭ����");
        pw.flush();//����flush()�������������
        try {
			socket.shutdownOutput();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			Client.logger.error(e.getMessage(),e);
		} 
	}
	}
		  	       
      


	
 
 
    