package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ComTransRecord 
{
	public static void main(String[] args) 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","");
			Statement s = con.createStatement();
			
			Scanner sc = new Scanner(System.in);
			int w=1;
			System.out.println("**************Someshwar Industries LTD.********************");
			System.out.println("\n"+"Enter The Four Digit Password");
			int pass=sc.nextInt();
			
			if(pass==3690)
			{
				while(w==1)
				{
					System.out.println(" ");
					System.out.println(" Choose an Option \n"+" 1. Deposit Ammount\n"+" 2. Withdraw Ammount\n"+" 3. Balance Details of Company\n"+" 4. Show All Transaction Details of Company\n"+" 5. Show Transaction Details of Selected Date \n"+" 6. Exit");
					
					int choice = sc.nextInt();
					
					switch(choice) {
					
					case 1: //Deposit
						
						int amt = 0;
						ResultSet rs = s.executeQuery("select Balance from accounts where Sr_No=(select max(Sr_No) from accounts);");
						while (rs.next())
						{
							amt = rs.getInt("Balance");
						}
						
						System.out.println("\n"+"Enter Details of Transaction : ");
						String detail = sc.next();
						
						System.out.println("\n"+" Ammount Deposited : ");
						int deposit = sc.nextInt();
						
						DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
						LocalDateTime now = LocalDateTime.now();
						String tdate = date.format(now);
						
						DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");  
						String ttime = time.format(now);
						
						int balance = 0;
						
						if(deposit>0)
						{
							balance = amt + deposit;
						}
						else
						{
							System.out.println("\n"+"Invalid Ammount");
							break;
						}
						
						
						System.out.println(detail + "  " + deposit + "  "+tdate+"  "+ ttime+"  "+balance);
						
						String si ="insert into accounts (Trans_Details,Deposit,Date,Time,Balance) values (?,?,?,?,?);";
						PreparedStatement ps = con.prepareStatement(si);
						
						ps.setString(1,detail);
						ps.setInt(2, deposit);
						ps.setString(3, tdate);
						ps.setString(4,ttime);
						ps.setInt(5, balance);
						
						int i = ps.executeUpdate();
						System.out.println("\n"+"Transaction noted");
						
						break;
						
					case 2:
						
						int wamt = 0;
						ResultSet wrs = s.executeQuery("select Balance from accounts where Sr_No=(select max(Sr_No) from accounts);");
						while (wrs.next())
						{
							wamt = wrs.getInt("Balance");
						}
						
						System.out.println("\n"+"Enter Details of Transaction : ");
						String wdetail = sc.next();
						
						System.out.println("\n"+" Ammount Withdrawn : ");
						int withdraw = sc.nextInt();
						
						DateTimeFormatter wdate = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
						LocalDateTime wnow = LocalDateTime.now();
						String wtdate = wdate.format(wnow);
						
						DateTimeFormatter wtime = DateTimeFormatter.ofPattern("HH:mm:ss");  
						String wttime = wtime.format(wnow);
						
						int wbalance = 0;
						
						if(wamt>withdraw)
						{
							wbalance = wamt - withdraw;
						}
						else
						{
							System.out.println("\n"+"Not Enough Balance");
							break;
						}
						
						String wsi ="insert into accounts (Trans_Details,Withdraw,Date,Time,Balance) values (?,?,?,?,?);";
						PreparedStatement wps = con.prepareStatement(wsi);
						
						wps.setString(1,wdetail);
						wps.setInt(2, withdraw);
						wps.setString(3, wtdate);
						wps.setString(4,wttime);
						wps.setInt(5, wbalance);
						
						int wi = wps.executeUpdate();
						System.out.println("\n"+"Transaction noted");
					
						
						break;
						
					case 3:
						int bamt = 0;
						ResultSet brs = s.executeQuery("select Balance from accounts where Sr_No=(select max(Sr_No) from accounts);");
						while (brs.next())
						{
						bamt = brs.getInt("Balance");
						System.out.println("\n"+"Balance Ammount of Company is Rs" + bamt);
						}
						
						break;
						
					case 4:
						
						ResultSet ars = s.executeQuery("select * from accounts;");
						
						while (ars.next())
						{
							int srno =ars.getInt("Sr_No");
							String r = ars.getString("Trans_Details");
							int dep = ars.getInt("Deposit");
							int wit=ars.getInt("Withdraw");
							String td= ars.getString("Date");
							String tt= ars.getString("Time");
							int bal= ars.getInt("Balance");
							
							System.out.println("\n"+"Tansaction Id: " +srno + "\n"+"Transaction Details: "+r+"\n"+"Deposited Ammount: Rs"+dep+"\n"+"Withdrawal Ammount : Rs"+wit+"\n"+"Transaction Date: "+ td + "\n"+"Transaction Time: "+tt+"\n"+"Balance Ammount: Rs"+bal );
							System.out.println("\n");
						}
						break;
					
					case 5:
						System.out.println("\n"+"Enter the date of Transaction: yyyy/MM/dd");
						String fdate =sc.next();
						int a = 0;
						ResultSet frs = s.executeQuery("select * from accounts;");
						
						while (frs.next())
						{
								int srno =frs.getInt("Sr_No");
								String r = frs.getString("Trans_Details");
								int dep = frs.getInt("Deposit");
								int wit=frs.getInt("Withdraw");
								String td= frs.getString("Date");
								String tt= frs.getString("Time");
								int bal= frs.getInt("Balance");
								
								if(fdate.equals(td))
								{
									System.out.println("\n"+"Tansaction Id: " +srno + "\n"+"Transaction Details: "+r+"\n"+"Deposited Ammount: Rs"+dep+"\n"+"Withdrawal Ammount: Rs"+wit+"\n"+"Transaction Date: "+ td + "\n"+"Transaction Time: "+tt+"\n"+"Balance Ammount: Rs"+bal );
									System.out.println("\n");
									a=1;
								}
						}
						if(a==0)
						{
							System.out.println("\n"+"No Data Records of "+fdate+" found.");
						}
						break;
						
					case 6:
						
						w=0;
						System.out.println("\n"+"Transaction Done");
						break;
					}
				}
			}
			else
			{
				System.out.println("/n"+"Wrong Password");
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}

}
