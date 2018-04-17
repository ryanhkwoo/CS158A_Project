//Ryan Woo 
//CS158A Group Project
import java.util.*;
import java.io.*;
import java.sql.*;

class bank {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static String create_sql;
	private static Connection connection;

	public static void main(String argv[]) throws SQLException {
		if (argv.length != 1) {
			System.out.println("Need database properties filename");
			return;
		} 
		
		init(argv[0]);
		main_menu();
		deinit();
	}

	static void init(String filename) throws SQLException {
		try {
			//load .properties
			Properties props = new Properties();
			props.load(new FileInputStream(filename));
			driver = props.getProperty("jdbc.driver");
			url = props.getProperty("jdbc.url");
			username = props.getProperty("jdbc.username");
			password = props.getProperty("jdbc.password");
			create_sql = props.getProperty("jdbc.create_sql");

			//load driver and establish connection
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			
			//checking if the schema is setup, if it is not this will throw com.ibm.db2.jcc.am.SqlSyntaxErrorException
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM Customer";
			// System.out.println(query);
			stmt.executeQuery(query);
			stmt.close();
		} 
		catch(com.ibm.db2.jcc.am.SqlSyntaxErrorException e) {
			System.out.println("Initializing schema");
			init_schema(create_sql);
		}
		catch (Exception e) {
			System.err.println("Initialization error");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static void init_schema(String create_sql) throws SQLException {
		String[] stringArray = create_sql.split(";");
		for(int i = 0; i < stringArray.length; i++){
			Statement stmt = connection.createStatement();
			String query = stringArray[i];
			// System.out.println(query);
			stmt.executeUpdate(query);
			stmt.close();
		}
	}

	static void deinit() {
		try{
			connection.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	static void main_menu(){
		boolean quit = false;
		Scanner keyboard = new Scanner(System.in);
		while(!quit){
			System.out.println("\nWelcome to the Self Services Banking System! - Main Menu");
			System.out.println("--------------------------------------------------------\n");
			System.out.println("1. New Customer");
			System.out.println("2. Customer Login");
			System.out.println("3. Exit");
			System.out.print  ("\nYour choice: ");
			int selection = keyboard.nextInt();
			switch(selection){
				case 1:
					new_customer();
					break;
				case 2:
					customer_login();
					break;
				case 3:
					quit = true;
					System.out.println("Thanks for using the Self Services Banking System!");
					break;
				default:
					System.err.println("Invalid choice");
			}
		}
	}

	static void new_customer(){
		try {
			Scanner keyboard = new Scanner(System.in);

			System.out.print("Name: ");
			String name = keyboard.nextLine();

			System.out.print("Gender M (or) F: ");
			char gender = keyboard.next().charAt(0);

			System.out.print("Age (Number Only): ");
			int age = keyboard.nextInt();

			System.out.print("Pin (Number Only): ");
			int pin = keyboard.nextInt();

			Statement stmt = connection.createStatement();
			String query = "INSERT INTO Customer (Name, Gender, Age, Pin) VALUES ('"+name+"', '"+gender+"', "+age+", "+pin+")";
			// System.out.println(query);
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			stmt.close();

			System.out.println("Customer succefully created:");
			System.out.println("Name: "+name+", Gender: "+gender+", Age: "+age+", Pin: "+pin);
			System.out.println("Customer ID: #"+id);
		}
		catch(Exception e) {
			System.err.println("Customer account creation failed: "+e);
		}
	}

	static void customer_login(){
		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("ID: ");
			int id = keyboard.nextInt();

			System.out.print("Pin: ");
			int pin = keyboard.nextInt();

			if(id == 0 && pin == 0){
				admin_menu();
			}
			else {
				Statement stmt = connection.createStatement();
				String query = "SELECT Name, Pin FROM Customer WHERE ID="+id+" AND pin="+pin;
				// System.out.println(query);
				ResultSet rs = stmt.executeQuery(query);
				if(rs.next()){
					String name = rs.getString("Name");
					customer_menu(id, name);
				}
				else {
					System.err.println("Invalid ID-Pin Combination. Login failed");
					return;
				}
				stmt.close();
				rs.close();
			}
		}
		catch(Exception e) {
			System.err.println("Database error: "+e);
		}
	}

	static void customer_menu(int id, String name){
		boolean quit = false;
		Scanner keyboard = new Scanner(System.in);

		while(!quit){
			System.out.println("\nSelf Services Banking System - Customer Menu");
			System.out.println("--------------------------------------------------------\n");
			System.out.println("Welcome "+name+" (#"+id+")");

			System.out.println("1. Open Account");
			System.out.println("2. Close Account");
			System.out.println("3. Deposit");
			System.out.println("4. Withdraw");
			System.out.println("5. Transfer");
			System.out.println("6. Account Summary");
			System.out.println("7. Exit");
			System.out.print  ("\nYour choice: ");
			int selection = keyboard.nextInt();
			switch(selection){
				case 1:
					new_account(id);
					break;
				case 2:
					close_account(id);
					break;
				case 3:
					deposit(id);
					break;
				case 4:
					withdraw(id);
					break;
				case 5:
					transfer(id);
					break;
				case 6:
					account_summary(id);
					break;
				case 7:
					quit = true;
					break;
				default:
					System.out.println("Invalid choice");
			}
		}
	}

	static void new_account(int id){
		try {
			Scanner keyboard = new Scanner(System.in);
			
			System.out.print("Customer ID (Yours is #"+id+"): ");
			int customer_id = keyboard.nextInt();

			System.out.print("Account type [C]hecking/[S]avings: ");
			char type = keyboard.next().charAt(0);

			System.out.print("Initial deposit: ");
			int deposit = keyboard.nextInt();

			Statement stmt = connection.createStatement();
			String query = "INSERT INTO Account (ID, Balance, Type, Status) VALUES ("+customer_id+", "+deposit+", '"+type+"', 'A')";
			// System.out.println(query);
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int account_no = rs.getInt(1);
			stmt.close();

			
			System.out.println("Account succefully created!");
			System.out.println("Customer ID: "+id+", Status: A, Type: "+type);
			System.out.println("Account no:"+account_no);
		}
		catch(Exception e) {
			System.err.println("Account creation failed: "+e);
		}
	}

	static void close_account(int id){
		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Account no: ");
			int account_no = keyboard.nextInt();

			Statement stmt = connection.createStatement();
			String query = "UPDATE Account SET Status='I', Balance=0 WHERE ID="+id+" AND Number="+account_no;
			// System.out.println(query);
			int affected_rows = stmt.executeUpdate(query);
			stmt.close();

			System.out.println(affected_rows > 0?"Account successfully closed":"Account failed to close");
		}
		catch(Exception e) {
			System.err.println("Account failed to close: "+e);
		}
	}

	static void deposit(int id){
		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Account no: ");
			int account_no = keyboard.nextInt();
			
			System.out.print("Deposit amount: ");
			int deposit = keyboard.nextInt();
			if(deposit <= 0){
				System.err.println("Invalid amount");
				return;
			}

			Statement stmt = connection.createStatement();
			String query = "UPDATE Account SET Balance=Balance+"+deposit+" WHERE Status <> 'I' AND Number="+account_no;
			// System.out.println(query);
			int affected_rows = stmt.executeUpdate(query);
			stmt.close();

			System.out.println(affected_rows > 0?"Deposit successful":"Deposit failed");
		}
		catch(Exception e) {
			System.err.println("Deposit failed: "+e);
		}
	}

	static void withdraw(int id){
		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Account no: ");
			int account_no = keyboard.nextInt();
			
			System.out.print("Withdrawal amount: ");
			int amount = keyboard.nextInt();
			if(amount <= 0){
				System.err.println("Invalid amount");
				return;
			}

			Statement stmt = connection.createStatement();
			String query = "UPDATE Account SET Balance=Balance-"+amount+" WHERE Status <> 'I' AND ID="+id+" AND Number="+account_no;
			// System.out.println(query);
			int affected_rows = stmt.executeUpdate(query);
			stmt.close();

			System.out.println(affected_rows > 0?"Withdraw successful":"Withdraw failed");
		}
		catch(Exception e) {
			System.err.println("Withdraw failed: "+e);
		}
	}

	static void transfer(int id){
		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Account From no: ");
			int account_from = keyboard.nextInt();
			
			System.out.print("Account To no: ");
			int account_to = keyboard.nextInt();

			System.out.print("Transfer amount: ");
			int amount = keyboard.nextInt();
			if(amount <= 0){
				System.err.println("Invalid amount");
				return;
			}

			Statement stmt = connection.createStatement();
			String query = "SELECT Balance FROM Account WHERE Status <> 'I' AND Number IN ("+account_from+","+account_to+")";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			int count = 0;
			while(rs.next())
				count++;
			
			rs.close();
			if(count < 2) {
				System.err.println("Invalid Accounts");
				return;
			}

			query = "UPDATE Account SET Balance=Balance-"+amount+" WHERE ID="+id+" AND Number="+account_from;
			int affected_rows = stmt.executeUpdate(query);
			
			if(affected_rows > 0){
				query = "UPDATE Account SET Balance=Balance+"+amount+" WHERE Number="+account_to;
				affected_rows += stmt.executeUpdate(query);
				stmt.close();

				System.out.println(affected_rows > 0?"Transfer successful":"Transfer failed");
			}
			else
				System.err.println("Transfer failed: Not your account");
		}
		catch(Exception e) {
			System.err.println("Transfer failed: "+e);
		}
	}

	static void account_summary(int id){
		try {
			System.out.println("Account summary for Customer with ID: "+id);

			Statement stmt = connection.createStatement();
			String query = "SELECT Number, Balance FROM Account WHERE ID="+id+" AND Status <> 'I'";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			
			int total = 0;
			while(rs.next()){
				int account_no = rs.getInt("Number");
				int balance = rs.getInt("Balance");
				System.out.println("#"+account_no+" - "+balance+" $");
				total += balance;
			}
			
			System.out.println("Total: "+total+" $");
			rs.close();
			stmt.close();
		}
		catch(Exception e) {
			System.err.println("Database error");
			e.printStackTrace();
		}
	}


	static void admin_menu(){
		boolean quit = false;
		Scanner keyboard = new Scanner(System.in);
		
		while(!quit){
			int customer_id;
			System.out.println("\nSelf Services Banking System - Administrator Main Menu");
			System.out.println("--------------------------------------------------------\n");

			System.out.println("1. Account Summary for a Customer");
			System.out.println("2. Report A :: Customer information with total Balance in Decreasing Order");
			System.out.println("3. Report B :: Find the Average Total Balance Between Age Groups");
			System.out.println("4. Exit");

			System.out.print  ("\nYour choice: ");
			int selection = keyboard.nextInt();
			switch(selection){
				case 1:
					System.out.print("Customer ID: ");
					customer_id = keyboard.nextInt();
					account_summary(customer_id);
					break;
				case 2:
					report_a();
					break;
				case 3:
					report_b();
					break;
				case 4:
					quit = true;
					break;
				default:
					System.out.println("Invalid choice");
			}
		}
	}

	
	static void report_a(){
		try {
			System.out.println("Report A :: Customer information with total Balance in Decreasing Order");
			System.out.println("-----------------------------------------------------------------------\n");

			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM CustomerAccountsSummary ORDER BY BalanceTotal DESC";
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				int id = rs.getInt("ID");
				int age = rs.getInt("Age");
				int balance_total = rs.getInt("BalanceTotal");
				String name = rs.getString("Name");
				String gender = rs.getString("Gender");

				System.out.println("#"+id+": "+name+"  "+gender+" "+age+"yrs old, Total Balance: "+balance_total+" $");
			}
			
			rs.close();
			stmt.close();
		}
		catch(Exception e) {
			System.err.println("Database error");
			e.printStackTrace();
		}
	}

	
	static void report_b(){
		try {
			System.out.println("Report B :: Find the Average Total Balance Between Age Groups");
			System.out.println("-----------------------------------------------------------------------\n");
			
			Scanner keyboard = new Scanner(System.in);

			System.out.print("Min age: ");
			int min_age = keyboard.nextInt();
			if(min_age < 0){
				System.err.println("Invalid age");
				return;
			}

			
			System.out.print("Max age: ");
			int max_age = keyboard.nextInt();
			if(max_age < 0 || min_age > max_age){
				System.err.println("Invalid age");
				return;
			}
			
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM CustomerAccountsSummary WHERE Age >= "+min_age+" AND Age <= "+max_age;
			// System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			int total = 0;
			int count = 0;
			
			while(rs.next()){
				int balance_total = rs.getInt("BalanceTotal");
				total += balance_total;
				count++;
			}
			
			if(count == 0)
				System.out.println("No customers in this age group");
			else
				System.out.println("Average balance: "+(float)total/count+" $");
			rs.close();
			stmt.close();
		}
		catch(Exception e) {
			System.err.println("Report error");
			e.printStackTrace();
		}
	}
}