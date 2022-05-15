package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bill {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, billname, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/management", "root", "y1o2h3a4n5@#");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertBill(String billAmount, String contactNo, String userEmail) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into BILLDATA(`ID`,`BILLAMOUNT`,`CONTACTNO`,`USEREMAIL`)" + " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, billAmount);
			preparedStmt.setString(3, contactNo);
			preparedStmt.setString(4, userEmail);

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newBill = readBill();
			output = "Inserted successfully";
			output = "{\"status\":\"success\", \"data\": \"" +newBill + "\"}";
			
		} catch (Exception e) {
			output = "Error while inserting the Bill.";
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the Bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readBill() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\" class=\"table\"><tr><th>Bill Amount</th><th>Contact No</th><th>Email</th><th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from BILLDATA";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs =  stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String billAmount = rs.getString("billAmount");
				String contactNo = rs.getString("contactNo");
				String userEmail = rs.getString("userEmail");

				output += "<tr><td><input id='hidbill_IDUpdate' name='hidbill_IDUpdate' type='hidden' value='"+id+"'>"+billAmount+"</td>"; 
				output += "<td>" + contactNo + "</td>";
				output += "<td>" + userEmail + "</td>";
				// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-iD='" + id + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-iD='" + id + "'></td></tr>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the BILLDATA.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateBill(String id, String billAmount, String contactNo, String userEmail) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE BILLDATA SET BILLAMOUNT=?,CONTACTNO=?,USEREMAIL=? WHERE ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, billAmount);
			preparedStmt.setString(2, contactNo);
			preparedStmt.setString(3, userEmail);
			preparedStmt.setInt(4, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newBill = readBill();
			output = "{\"status\":\"success\", \"data\": \"" +newBill + "\"}";
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Bill.";
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteBill(String id) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from BILLDATA where ID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newBill = readBill();
			output = "{\"status\":\"success\", \"data\": \"" +newBill + "\"}";
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Bill.";
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the Bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
