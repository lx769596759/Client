package dbUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import Client.Client;


public class dbTools {
	
	/**
     * @param args
     * 实现增删改查
     */
    static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dbUrl;
    static String us="sa";
    static String pw="dbtest";
    static Connection conn=dbTools.getConn();
    //连接数据库构造构造方法
    public static Connection getConn(){
               Connection conn=null;
              try {
                 Class.forName(driverName);
                 dbUrl=MessageFormat.format("jdbc:sqlserver://{0}:1433;DatabaseName=Product", Client.ip);
                 conn=DriverManager.getConnection(dbUrl,us,pw);
            } catch (ClassNotFoundException  e) {
                e.printStackTrace();
            }catch(SQLException e1){
                e1.printStackTrace();
            }
            return conn;
    }
    //创建表
    public void dbCreate(Connection conn) throws SQLException {       
        Statement stat = null;
        stat = conn.createStatement();
        stat
      .executeUpdate("create table UserInfo" +
            "(userId int," +
            "userName varchar(20)," +
            "userAddress varchar(20)," +
            "userAge int check(userAge between 0 and 150)," +
            "userSex varchar(20) default 'M' check(userSex='M' or userSex='W')" +
            ")");
 
    }
    //向表中添加数据
    public void insert(String sql,Connection conn) throws Exception  {  
         Statement sta = conn.createStatement();  
         sta.executeUpdate(sql);
         sta.close();
         //conn.close();
            }
    
    //修改表中的数据
    public void modify(String sql,Connection conn) throws Exception  {  
        Statement sta = conn.createStatement(); 
        //String sql1 = "insert into table_1 (检测点,测量值,READFLAG) values(1, '20',0)";  
        sta.executeUpdate(sql);
        //System.out.println("修改成功！");
        sta.close();
        //conn.close();
           }
    
    //查询表select 
    public String  ddlSelect(String sql,Connection conn,String title1,String title2) throws SQLException{
         //Connection conn=getConn(driverName, dbUrl, us, pw);
         Statement sta=conn.createStatement();
         ResultSet rs=sta.executeQuery(sql);
         String value = null;
         String logTime = null;
         while(rs.next()){
              value=rs.getString(title1);
              logTime=rs.getString(title2);
              //System.out.println(logTime+","+value);            
         }
         sta.close();
         //conn.close();
         if (value!=null&&logTime!=null)
         {
		 return logTime+","+value;}
         else return null;
          
    }
    //删除数据方法
//    public void ddlDel(int index)throws SQLException{
//         String ddlDelsql="delete from UserInfo where userId="+index;
//         Connection conn=getConn(driverName, dbUrl, us, pw);
//         Statement sta=conn.createStatement();
//         sta.executeUpdate(ddlDelsql);
//         sta.close();
//         conn.close();
//          
//    }
//    //修改方法
//    public void ddlUpdate(String name,
//        String Address,int age,String sex,int id)throws SQLException{
//        String ddlUpSql="update UserInfo set userName=?,userAddress=?,userAge=?,userSex=? where userId=?";
//        Connection conn=getConn(driverName, dbUrl, us, pw);
//        PreparedStatement psta=conn.prepareStatement(ddlUpSql);
//        psta.setString(1, name);
//        psta.setString(2, Address);
//        psta.setInt(3, age);
//        psta.setString(4, sex);
//        psta.setInt(5, id);
//        psta.addBatch();
//        psta.executeBatch();
//        psta.close();
//        conn.close();
//    }
    public static void main(String[] args) throws SQLException {}

}
