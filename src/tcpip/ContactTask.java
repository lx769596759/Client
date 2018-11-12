package tcpip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import Client.Client;
import DataCollect.DataOperater;

import javax.swing.JOptionPane;

public class ContactTask extends TimerTask{

    private Socket socket;
    private Timer timer;
    public ContactTask(Socket socket,Timer timer) {
        this.socket = socket;
        this.timer=timer;
    }  
	public void run() {   	   		       	    			    			
	 try{
	     InputStream is = socket.getInputStream();
	     InputStreamReader isr = new InputStreamReader(is);
	     BufferedReader br = new BufferedReader(isr);
	     String info = br.readLine();  //读不到会阻塞线程，如果socket断开，会产生异常
	 }catch (IOException e){
	     timer.cancel();
	     socket=null;
	     DataOperater.exit=true;
	     Client.button.setEnabled(true);
	     Client.chckbxNewCheckBox.setSelected(false);
	     Client.btnNewButton_1.setEnabled(true);
	     JOptionPane.showMessageDialog(Client.contentPane, "与下位机连接断开，请重新连接", "错误",JOptionPane.ERROR_MESSAGE ); 
		}	     	    	  	    	    	      
		  }	    	   
	  

}
