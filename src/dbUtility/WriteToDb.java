package dbUtility;

import java.sql.Connection;
import java.text.MessageFormat;
import Client.Client;

public class WriteToDb implements Runnable {
    
	  String value;
	  Connection conn;
	  dbTools db=new dbTools();
	  
	  public WriteToDb(String value,Connection conn)
	  {
		  this.value=value;
		  this.conn=conn;
	  }
	  
	  public void run(){ 		  
		  try {
			    String sql=MessageFormat.format("insert into table_4 (出土速度) values({0})",value);
				db.insert(sql,conn);
			} catch (Exception e) {
				Client.logger.error(e.getMessage(),e);
				}	
	  }
	 }