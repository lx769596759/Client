package tcpip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import Client.Client;
import DataCollect.DataOperater;

public class ReConnect extends Thread {

	public static Logger logger = Logger.getLogger(ReConnect.class);
	public ReConnect() {
		start();
	}

	public void run() {
		while (true) {
			Socket socket = new Socket();
			try {
				socket.connect(new InetSocketAddress(Client.ip, 8888), 5000);
			} catch (IOException e) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}

			// 开启接受线程
			Thread t1 = new Thread(new DataOperater(Client.series, socket));
			t1.setPriority(10);
			t1.start();
			logger.info("重连成功，开始重新接收数据...");
			Client.chckbxNewCheckBox.setSelected(true);
			break;
		}

	}
}
