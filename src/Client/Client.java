package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import tcpip.ReConnect;
import tcpip.SendData;
import DataCollect.DataOperater;
import dbUtility.dbTools;




public class Client extends JFrame implements Runnable  {
	
	    private static final long serialVersionUID = 1L;
	    public static TimeSeries series;  
	    public static JPanel contentPane;
	    private JTextField textField;
	    public static JCheckBox chckbxNewCheckBox;
	    private JButton initbutton;
	    public static JButton btnNewButton_1;
	    //private JButton btnNewButton_2;
	    private Socket socket; 
	    private JTextField textField_1;
	    private static SendData sd;
	    public static String ip;
	    public static Logger logger=Logger.getLogger(Client.class);
	    private static Client jsdChart;
	    private static InitDialog dialog;
	    public static JButton button;
	    //private JButton btnNewButton;
	    public static JTextField tf_speed;  //��ʾƤ�����ٶ�
	    public static JTextField tf_speed2; //��ʾ������
	    /** 
	     * ���� 
	     */  
	    @SuppressWarnings("deprecation")
	    	    
		public Client() {
	    	contentPane=(JPanel)this.getContentPane();
	    	setResizable(false);
	    	contentPane.setBackground(Color.WHITE);
	    	//contentPane.setLayout(new BorderLayout(0, 0));
	    	contentPane.setLayout(null);
	    	
	    	this.addWindowListener(new CloseWindowsListener());
					         
	    	     Client.series = new TimeSeries("�����ٶ�(m^3/s)", Second.class);
			     TimeSeriesCollection dataset = new TimeSeriesCollection(Client.series);  
			     ChartPanel chartPanel = new ChartPanel(createChart(dataset)); 
			     chartPanel.setBounds(0, 10, 840, 400);
			     chartPanel.setEnabled(false);
			     //chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));  	   
			     contentPane.add(chartPanel);  
			     JPanel connectPanel = new JPanel();
			     connectPanel.setFont(new Font("΢���ź�", Font.BOLD, 13));
			     connectPanel.setBackground(Color.WHITE);
			     connectPanel.setBounds(150, 432, 253, 87);
			     getContentPane().add(connectPanel);
			     connectPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u670D\u52A1\u5668\u8FDE\u63A5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			     connectPanel.setLayout(null);
			     JLabel lblNewLabel = new JLabel("\u4E3B\u673AIP");
			     lblNewLabel.setFont(new Font("΢���ź�", Font.BOLD, 12));
			     lblNewLabel.setBounds(10, 57, 54, 15);
			     connectPanel.add(lblNewLabel);
			        
			         
			     button = new JButton("����");
			     button.setFocusPainted(false);
			     button.addActionListener(new ActionListener() {
			     	public void actionPerformed(ActionEvent e) {
			     		try {							
							ip=textField_1.getText();
							socket = new Socket(ip,8888);
							socket.setKeepAlive(true);
							socket.setSoTimeout(30000);
//							Timer timer=new Timer();
//							ReConnect contackTask=new ReConnect(socket,timer);
//							timer.schedule(contackTask,1);//�������
			     		    //sd=new SendData(socket);
			     		    chckbxNewCheckBox.setSelected(true);
			     		    button.setEnabled(false);
							//initbutton.setEnabled(true);
							Thread t1=new Thread(new DataOperater(series,socket));
				     		t1.setPriority(10);
				     		t1.start();
						} catch (IOException e1) {
							logger.error(e1.getMessage(),e1);
							JOptionPane.showMessageDialog(contentPane, "���Ӵ�����ȷ�������Ƿ�����", "�޷�����",JOptionPane.ERROR_MESSAGE );
							chckbxNewCheckBox.setSelected(false);
							//initbutton.setEnabled(false);							
						}
			     	}
			     });
			     button.setFont(new Font("΢���ź�", Font.BOLD, 12));
			     button.setBounds(160, 53, 71, 23);
			     connectPanel.add(button);
			     
//			     initbutton = new JButton("У׼");
//			     initbutton.setFocusPainted(false);
//			     initbutton.setEnabled(false);
//			     initbutton.setFont(new Font("΢���ź�", Font.BOLD, 12));
//			     initbutton.setBounds(160, 22, 71, 23);
//			     initbutton.addActionListener(new ActionListener() {
//				     	public void actionPerformed(ActionEvent e) {
//				     		int answer=JOptionPane.showConfirmDialog(jsdChart, "�Ƿ���г�ʼ����", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
//				     		if (answer==JOptionPane.OK_OPTION)
//				     		{
//				     		dialog = new InitDialog(jsdChart, "����", false);
//							dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
//							dialog.setVisible(true);							
//							sd.sendData("startУ׼");
//							new Thread(new Client()).start();					
//				     		}
//				     	}
//				     });
//			    
//			     connectPanel.add(initbutton);
			     
			     
			     chckbxNewCheckBox = new JCheckBox("�Ƿ�����");
			     chckbxNewCheckBox.setEnabled(false);
			     chckbxNewCheckBox.setBackground(Color.WHITE);
			     chckbxNewCheckBox.setBounds(10, 22, 22, 23);
			     connectPanel.add(chckbxNewCheckBox);
			     
			     JLabel label = new JLabel("\u662F\u5426\u8FDE\u63A5");
			     label.setFont(new Font("΢���ź�", Font.BOLD, 12));
			     label.setBounds(52, 26, 54, 15);
			     connectPanel.add(label);
			     
			     textField_1 = new JTextField();
			     textField_1.setBounds(52, 54, 98, 21);
			     textField_1.setText("192.168.2.1");
			     textField_1.addActionListener(new ActionListener() {
				     	public void actionPerformed(ActionEvent e) {
				     		try {							
								ip=textField_1.getText();
								socket = new Socket(ip,8888);
								socket.setKeepAlive(true);
								socket.setSoTimeout(30000);
								//Timer timer=new Timer();
								//ReConnect contackTask=new ReConnect(socket,timer);
								//timer.schedule(contackTask,1);//�������
				     		    //sd=new SendData(socket);
				     		    chckbxNewCheckBox.setSelected(true);
				     		    button.setEnabled(false);
								//initbutton.setEnabled(true);
								Thread t1=new Thread(new DataOperater(series,socket));
					     		t1.setPriority(10);
					     		t1.start();
							} catch (IOException e1) {
								logger.error(e1.getMessage(),e1);
								JOptionPane.showMessageDialog(contentPane, "���Ӵ�����ȷ�������Ƿ�����", "�޷�����",JOptionPane.ERROR_MESSAGE );
								chckbxNewCheckBox.setSelected(false);
								//initbutton.setEnabled(false);							
							}
				     	}
				     });
			     connectPanel.add(textField_1);
			     textField_1.setColumns(10);
			         
//			     JPanel panel_1 = new JPanel();
//			     panel_1.setFont(new Font("΢���ź�", Font.BOLD, 12));
//			     panel_1.setBackground(Color.WHITE);
//			     panel_1.setBounds(353, 432, 245, 87);
//			     getContentPane().add(panel_1);
//			     panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u53D1\u9001\u547D\u4EE4", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//			     panel_1.setLayout(null);
//			         
//			     JLabel lblms = new JLabel("����ֱ��(m)");
//			     lblms.setFont(new Font("΢���ź�", Font.BOLD, 12));
//			     lblms.setBounds(21, 26, 81, 15);
//			     panel_1.add(lblms);
//			     
//			     textField = new JTextField();
//			     textField.setBounds(97, 23, 56, 21);
//			     textField.setToolTipText("����󰴻س�");
//			     String diameter=getParameter();
//			     if(diameter!=null)
//			     {
//			    	 textField.setText(diameter);
//			     }
//			     else
//			     {
//			    	 textField.setText("0.20"); 
//			     }
//			     textField.setEnabled(false);
//				 textField.addActionListener(new TextListener());
//			     panel_1.add(textField);
//			     textField.setColumns(10);
//			     
//			     btnNewButton = new JButton("�޸�");
//			     btnNewButton.setFocusPainted(false);
//			     btnNewButton.addActionListener(new ActionListener() {
//			     	public void actionPerformed(ActionEvent e) {
//			     		textField.setEnabled(true);
//			     		btnNewButton_1.setEnabled(false);
//			     	}
//			     });
//			     btnNewButton.setFont(new Font("΢���ź�", Font.BOLD, 12));
//			     btnNewButton.setBounds(157, 21, 68, 23);
//			     panel_1.add(btnNewButton);
//			     
//			     btnNewButton_1 = new JButton("��ʼ���");
//			     btnNewButton_1.setFocusPainted(false);
//			     btnNewButton_1.addActionListener(new ActionListener() {
//			     	public void actionPerformed(ActionEvent e) {			     		
//			     		try {
//							Socket connectTest= new Socket(ip,8888);//��ʱ����һ��Socket��������Ƿ�����										
//				     		btnNewButton_1.setEnabled(false);
//				     		textField_1.setEditable(false);
//				     		initbutton.setEnabled(false);
//				     		button.setEnabled(false);
//				     		btnNewButton.setEnabled(false);
//				     		btnNewButton_2.setEnabled(true);			     		
//				     		series.clear();
//				     		sd.sendData("��ʼ��أ�����ֱ����"+textField.getText());
//				     		DataOperater.exit=false;
//				     		Thread t1=new Thread(new DataOperater(series));
//				     		t1.setPriority(10);
//				     		t1.start();
//				     		connectTest=null;
//						} catch (Exception e1) {
//							JOptionPane.showMessageDialog(contentPane, "δ���ӷ����������������ӣ�", "���Ӵ���",JOptionPane.ERROR_MESSAGE);
//							button.setEnabled(true);
//							chckbxNewCheckBox.setSelected(false);
//							Client.logger.error(e1.getMessage(),e1);
//						}
//		     					     		
//			     	}
//			     });
//			     btnNewButton_1.setFont(new Font("΢���ź�", Font.BOLD, 12));
//			     btnNewButton_1.setBounds(21, 53, 93, 23);
//			     panel_1.add(btnNewButton_1);
//			     
//			     btnNewButton_2 = new JButton("ֹͣ���");
//			     btnNewButton_2.setEnabled(false);
//			     btnNewButton_2.setFocusPainted(false);
//			     btnNewButton_2.addActionListener(new ActionListener() {
//			     	public void actionPerformed(ActionEvent e) {
//			    		btnNewButton_1.setEnabled(true);
//			    		//textField_1.setEditable(true);
//			     		initbutton.setEnabled(false);
//			     		//button.setEnabled(true);
//			     		btnNewButton.setEnabled(true);
//			     		btnNewButton_2.setEnabled(false);		
//			     		sd.sendData("ֹͣ���0");
//			     		try {
//							Thread.sleep(500);
//							DataOperater.exit=true;
//						} catch (InterruptedException e1) {
//							logger.error(e1.getMessage(),e1);
//						}			     		
//			     	}
//			     });
//			     btnNewButton_2.setFont(new Font("΢���ź�", Font.BOLD, 12));
//			     btnNewButton_2.setBounds(132, 53, 93, 23);
//			     panel_1.add(btnNewButton_2);
			     
			     
			     JPanel dataPanel = new JPanel();
			     dataPanel.setFont(new Font("΢���ź�", Font.BOLD, 12));
			     dataPanel.setBackground(Color.WHITE);
			     dataPanel.setBounds(530, 432, 300, 87);
			     getContentPane().add(dataPanel);
			     dataPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ʵʱ����", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			     dataPanel.setLayout(null);
			     
			     
			     JLabel speed = new JLabel("������(m^3/s)");
			     speed.setFont(new Font("΢���ź�", Font.BOLD, 12));
			     speed.setBounds(21, 26, 90, 15);
			     dataPanel.add(speed);
			     
			     JLabel time = new JLabel("��¼ʱ��");
			     time.setFont(new Font("΢���ź�", Font.BOLD, 12));
			     time.setBounds(21, 57, 90, 15);
			     dataPanel.add(time);
			     
			     tf_speed = new JTextField();
			     tf_speed.setBounds(120, 23, 75, 21);
			     tf_speed.setEditable(false);
			     dataPanel.add(tf_speed);
			     
			     tf_speed2 = new JTextField();
			     tf_speed2.setBounds(120, 54, 130, 21);
			     tf_speed2.setEditable(false);
			     dataPanel.add(tf_speed2);
			
			
	    }  
	  
	    /** 
	     * ����Ӧ�ó������ 
	     */  
	    @SuppressWarnings("deprecation")
		public void createUI() {  
	    	Client.series = new TimeSeries("���", Second.class);
	        TimeSeriesCollection dataset = new TimeSeriesCollection(Client.series);  
	        ChartPanel chartPanel = new ChartPanel(createChart(dataset)); 
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));  	  
	        JPanel buttonPanel = new JPanel();  
	        buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));  
	        getContentPane().add(chartPanel);  
	        //getContentPane().add(buttonPanel, BorderLayout.SOUTH);  
	    }  
	  
	    /** 
	     * ���ݽ��������JFreechart������� 
	     *  
	     * @param dataset 
	     * @return 
	     */  
	    private JFreeChart createChart(XYDataset dataset) {  
	        JFreeChart result = ChartFactory.createTimeSeriesChart("��̬����չʾ", "ʱ��",  
	                "��̬��ֵ�仯", dataset, true, true, false); 
	        result.setBackgroundPaint(Color.WHITE);//���ñ���ɫ
	        result.getTitle().setFont(new Font("΢���ź�",Font.BOLD,20));//���ñ�������
	        result.getLegend().setItemFont(new Font("΢���ź�",Font.BOLD,10));//��������ǩ����

	        XYPlot plot = (XYPlot) result.getPlot();  
	        plot.setBackgroundPaint(Color.BLACK);// �����������򱳾�ɫ
	        DateAxis axis = (DateAxis)plot.getDomainAxis(); 
	        axis.setLabelFont(new Font("΢���ź�",Font.BOLD,15));//����X���ǩ����
	        //axis.setAutoTickUnitSelection(false);
	        //axis.setTickUnit(new DateTickUnit(DateTickUnit.SECOND,1,new SimpleDateFormat("mm:ss")));
	        axis.setAutoRange(true);  
	        axis.setFixedAutoRange(900000.0);  
	        ValueAxis axis1 = plot.getRangeAxis();
	        axis1.setRange(0.0, 1.50);
	        axis1.setLabelFont(new Font("΢���ź�",Font.BOLD,15));//����Y���ǩ����
	        return result;  
	    }  
	  	      	  
	    // ���������  
	    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {  
	        
	    	jsdChart = new Client();
	    	JFrame.setDefaultLookAndFeelDecorated(true);  
			JDialog.setDefaultLookAndFeelDecorated(true); 		
	        jsdChart.setTitle("�ͻ���"); 
	        jsdChart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        jsdChart.setBounds(100, 100, 900, 600);  
	        jsdChart.setVisible(true);
	        series.add(new Second(), (double)0);
	        
	    }
	    //���ļ���д�����ֱ��
		public static void saveParameter(Double diameter) {
		       String realPath = Client.class .getClassLoader().getResource("").getFile();
		       java.io.File file = new java.io.File(realPath);
		       realPath = file.getAbsolutePath();
		       try {
		           realPath = java.net.URLDecoder.decode (realPath, "utf-8");
		       } catch (Exception e) {
		           e.printStackTrace();
		       }
		       File file1 = new File(realPath+"\\"+"parameter.txt");
		       Writer out=null;
				try {
					out=new OutputStreamWriter(new FileOutputStream(file1,false));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}                 		         		
				try {
					out.write(String.valueOf(diameter));
					out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }
		
	    //��ȡ�ļ��еĹ���ֱ��
		public static String getParameter() {
		       String realPath = Client.class .getClassLoader().getResource("").getFile();
		       java.io.File file = new java.io.File(realPath);
		       realPath = file.getAbsolutePath();
		       String diameter = null;
		       try {
		           realPath = java.net.URLDecoder.decode (realPath, "utf-8");
			       File file1 = new File(realPath+"\\"+"parameter.txt");
			       Reader input=null;
				   input=new FileReader(file1);
				   char c[]=new char[100];
				   int len=input.read(c);
				   input.close();				   
				   diameter=new String(c,0,len);				   
		       } catch (Exception e) {
		           //e.printStackTrace();
		       }
			   if(diameter!=null)
			   {
				   return diameter;
			   }
			   else return null;	   
		    }
		       		       
	    class TextListener implements ActionListener
		  {
			public void actionPerformed(ActionEvent e) {	
				String input=textField.getText();
				try {
					double i = Double.parseDouble(input);
					textField.setEnabled(false);
					btnNewButton_1.setEnabled(true);
					saveParameter(i);
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(contentPane, "������Ϸ�����", "����",JOptionPane.ERROR_MESSAGE );
						btnNewButton_1.setEnabled(false);
					} 
		    }
		  }
	    class CloseWindowsListener extends WindowAdapter
		  {
			public void windowClosing(WindowEvent e) {					
				try {
					if (socket!=null&&sd!=null)
					{
					sd.sendData("ֹͣ���1");
					sd.sendData("�Ͽ�����");					
					socket.close();
					}
				} catch (IOException e1) {
					logger.error(e1.getMessage(),e1);
				}
		    }
		  }
		public void run() {
			Connection conn=dbTools.getConn();
			Statement sta;
			try {
				sta=conn.createStatement();
				sta.executeUpdate("delete from table_2");
			} catch (SQLException e2) {
				e2.printStackTrace();
			}			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i=0;i<100;i++)
			{
				InitDialog.progressBar.setValue(i+1);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			sd.sendData("stopУ׼");	
			try {
				sta = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=sta.executeQuery("Select * from table_2");
				rs.last(); //�Ƶ����һ��
				if(rs.getRow()==1)
				{
					sta.close();
					conn.close();
					dialog.dispose();					
					JOptionPane.showMessageDialog(jsdChart, "��ʼ���ɹ� ���������ͻ��ˣ�", "",JOptionPane.INFORMATION_MESSAGE );					
				}
				else
				{
					sta.close();
					conn.close();
					dialog.dispose();
					JOptionPane.showMessageDialog(jsdChart, "��ʼ��ʧ�ܣ�", "",JOptionPane.ERROR_MESSAGE );
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
									
		}
	}


  

