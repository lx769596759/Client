package DataCollect;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
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
				InputStream is = socket.getInputStream();// �ֽ�������
				InputStreamReader isr = new InputStreamReader(is);// ���ֽ���תΪ�ַ���
				BufferedReader br = new BufferedReader(isr);// Ϊ��������ӻ���
				String info = null;
				if ((info = br.readLine()) != null) {
					System.out.println("��������:" + info);
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
				logger.error("socket��ȡ����ʧ��", e);
				socket = null;
//				JOptionPane.showMessageDialog(Client.contentPane,
//						"�빤�ػ����ӶϿ�������������", "����", JOptionPane.ERROR_MESSAGE);
//				Client.button.setEnabled(true);
				Client.chckbxNewCheckBox.setSelected(false);
				new ReConnect(); // ���������̣߳�ÿ10S����һ��
				break;
			}

		}

	}
}
