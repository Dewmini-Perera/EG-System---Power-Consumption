package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Consumption {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3308/consumption?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertConsumption(String UserAccNo, String UnitConsumed, String Date, String UsageTime)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into Consumption(`UserID`,`UserAccNo`,`UnitConsumed`,`Date`,`UsageTime`,)" + " values (?, ?, ?, ?, ?,)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, UserAccNo);
			 preparedStmt.setString(3, UnitConsumed);
			 preparedStmt.setString(4, Date);
			 preparedStmt.setString(5, UsageTime);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newConsumption = readConsumption(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newConsumption + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Consumption.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readConsumption()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Account No</th><th>Unit Consumed</th><th>Date</th><th>UsageTime</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from Consumption";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String UserID = Integer.toString(rs.getInt("UserID"));
				 String UserAccNo = rs.getString("UserAccNo");
				 String UnitConsumed = rs.getString("UnitConsumed");
				 String Date = rs.getString("Date");
				 String UsageTime = rs.getString("UsageTime");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidConsumptionIDUpdate\' name=\'hidConsumptionIDUpdate\' type=\'hidden\' value=\'" + UserID + "'>" 
							+ UserAccNo + "</td>"; 
				output += "<td>" + UnitConsumed + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + UsageTime + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-Consumptionid='" + UserID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Consumption.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateConsumption(String UserID, String UserAccNo, String UnitConsumed, String Date, String UsageTime)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE Consumption SET UserAccNo=?,UnitConsumed=?,Date=?,UsageTime=?"  + "WHERE UserID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setString(1, UserAccNo);
			 preparedStmt.setString(2, UnitConsumed);
			 preparedStmt.setString(3, Date);
			 preparedStmt.setString(4,UsageTime);
			 preparedStmt.setInt(5, Integer.parseInt(UserID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newConsumption = readConsumption();    
			output = "{\"status\":\"success\", \"data\": \"" + newConsumption + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the Consumption.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteConsumption(String UserID)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 			
			} 
	 
			// create a prepared statement    
			String query = "delete from Consumption where UserID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(UserID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newConsumption = readConsumption();    
			output = "{\"status\":\"success\", \"data\": \"" +  newConsumption + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Consumption.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
