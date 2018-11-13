package DataCollect;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import Client.Client;

import org.apache.log4j.Logger;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;

import tcpip.ReConnect;

public class DataOperater implements Runnable {

	TimeSeries series;
	Socket socket;
	public static Logger logger = Logger.getLogger(DataOperater.class);
	public static boolean exit = false;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DataOperater(TimeSeries series, Socket socket) {
		this.series = series;
		this.socket = socket;
	}

	public void run() {
		while (true) {
			try {
				InputStream is = socket.getInputStream();// 字节输入流
				InputStreamReader isr = new InputStreamReader(is);// 将字节流转为字符流
				BufferedReader br = new BufferedReader(isr);// 为输入流添加缓冲
				String info = null;
				if ((info = br.readLine()) != null) {
					System.out.println("接收数据:" + info);
					String[] arr = info.split(";");
					String value = arr[0].split("=")[1];
					Double finalValue = Double.parseDouble(value);
					String time = arr[1].split("=")[1];
					Date date = sdf.parse(time);
					series.addOrUpdate(new Second(date), finalValue);
					Client.tf_speed.setText(value);
					Client.tf_speed2.setText(time);
				}
			} catch (Exception e) {
				logger.error("socket获取数据失败", e);
				socket = null;
//				JOptionPane.showMessageDialog(Client.contentPane,
//						"与工控机连接断开，请重新连接", "错误", JOptionPane.ERROR_MESSAGE);
//				Client.button.setEnabled(true);
//				Client.chckbxNewCheckBox.setSelected(false);
				new ReConnect(); // 开启重连线程，每10S重连一次
				break;
			}

		}

	}
}
