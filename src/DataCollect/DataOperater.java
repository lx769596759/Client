package DataCollect;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextPane;
import org.apache.log4j.Logger;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import tcpip.ReConnect;
import Client.Client;

public class DataOperater implements Runnable {

	private TimeSeries series;
	private Socket socket;
	public static Logger logger = Logger.getLogger(DataOperater.class);
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
				Client.chckbxNewCheckBox.setSelected(false);
				JTextPane infoPane = Client.jsdChart.getInfoPane();
				infoPane.setText("已断开，正在重连......");
				new ReConnect(); // 开启重连线程，每10S重连一次
				break;
			}

		}

	}
}
