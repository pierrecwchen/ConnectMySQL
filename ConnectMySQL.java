//**************************************************************************************************
//
//	Assignment #5		Pierre Chen		ID: 102921
//
//	Class for call-level interface.
//
//***************************************************************************************************

import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class ConnectMySQL extends JFrame{

	public ConnectMySQL(){
	
		super ("Computer Hardware Information from tables in cwchen Database"); // Call the parent constructor.

		boolean isKeepRunning = true;
	
		Scanner scan  = new Scanner(System.in);
	
		while(isKeepRunning){
		
			System.out.println("Choose one option by type in a number \n");
			System.out.println("a: Find a character who has skill range greater than a given range and price less than a given price.");
			System.out.println("b: Find a character from temporary view whose level is less than a given integer.");
			System.out.println("c: Find characters whose weapon has attack speed greater than given number and attack range less than given number.");
			System.out.println("d: List members of given party");
			System.out.println("e: Calculate and show the avrage speed or average range of a given weapon type");
			System.out.println("q: Quit\n");

			String option = scan.nextLine();

			try{
		   
				Class.forName("com.mysql.jdbc.Driver"); // Load database driver class.
				
				// Connect to database.
				Connection connection = DriverManager.getConnection("jdbc:mysql://tux.csit.upei.ca/cwchen?user=cwchen&password=gnu31");

				
				// If option is "a", find a character who has skill range greater than a given range and price less than a given price.
				if(option.equals("a")){
			
					PreparedStatement characters = connection.prepareStatement("select characterName, skillName, skillLevel, skillRange, price " + 
												"from characters natural join learns natural join skills " +
												"where skillRange > ? and price < ? " +
												"order by characterName, skillRange");
			
					System.out.println("Please type in a number for skill range: ");
			
					float skillRange = scan.nextFloat();

					System.out.println("Please type in a number for price: ");
			
					float price = scan.nextFloat();

					characters.setFloat(1, skillRange);

					characters.setFloat(2, price);

					System.out.println("\n\n" + "skill range less than " + skillRange + ", and price more than: " + price + " are: \n\n");

			

					ResultSet resultSet = characters.executeQuery();
			
					ResultSetMetaData metaData = resultSet.getMetaData();

					int numColumns = metaData.getColumnCount();
			
			
					for(int i=1; i<=numColumns; i++){

						System.out.print(metaData.getColumnName(i) + "\t");

					}
			
					System.out.println();
			
		
					while(resultSet.next()){
			    
						for(int col = 1; col <= numColumns; col++){

							System.out.print(resultSet.getObject(col)+"\t");

						}


						System.out.println();
					}
		    	
					characters.close();

		    		}
				
				// If option is "b", find a character from temporary view whose level is less than a given integer.
				else if(option.equals("b")){
			
					PreparedStatement characters = connection.prepareStatement("select * from otherCharacter where character_level < ?");
			
					System.out.println("\nPlease type in an integer: ");
			
					String level = scan.nextLine();

					characters.setString(1, level);

					System.out.println("\n\n" + "Characters with level which less than" + level + " are: \n\n");

			

					ResultSet resultSet = characters.executeQuery();
			
					ResultSetMetaData metaData = resultSet.getMetaData();

					int numColumns = metaData.getColumnCount();
			
			
					for(int i=1; i<=numColumns; i++){

						System.out.print(metaData.getColumnName(i) + "\t");

					}
			
					System.out.println();
			
		
					while(resultSet.next()){
			    
						for(int col = 1; col <= numColumns; col++){

							System.out.print(resultSet.getObject(col)+"\t");

						}


						System.out.println();
					}
		    	
					characters.close();

		    		}

				// If option is "c", find characters whose weapon has attack speed greater than given number and attack range less than given number.
				else if(option.equals("c")){
			
					PreparedStatement characters = connection.prepareStatement("select characterName, characterLevel, raceName, weaponName " +
												"from characters natural join characterList " +
												"where weaponName in (select weaponName " +
															"from weapons " +
												"where attackSpeed > ? and attackRange < ? )");
			
					System.out.println("Please type in a float number as attack speed: ");
			
					float attackSpeed = scan.nextFloat();

					System.out.println("Please type in a float number as attack range: ");
			
					float attackRange = scan.nextFloat();


					characters.setFloat(1, attackSpeed);
					characters.setFloat(2, attackRange);

					System.out.println("\n\n" + "Attack speed greater than " + attackSpeed + " and attack range less than" + attackRange + " are: \n\n");

					characters.executeQuery();

					ResultSet resultSet = characters.executeQuery();
			
					ResultSetMetaData metaData = resultSet.getMetaData();

					int numColumns = metaData.getColumnCount();
			
			
					for(int i=1; i<=numColumns; i++){

						System.out.print(metaData.getColumnName(i) + "\t");

					}
			
					System.out.println();
			
		
					while(resultSet.next()){
			    
						for(int col = 1; col <= numColumns; col++){

							System.out.print(resultSet.getObject(col)+"\t");

						}


						System.out.println();
					}
		    	
					characters.close();

		    		}
				
				// If option is "d", list members of given party name.
				else if(option.equals("d")){
			
					PreparedStatement characters = connection.prepareStatement("select characterName, characterLevel, positionC " +
												"from characters natural join partyMembers " +
												"where partyID=(select partyID " +
												"from parties " +
												"where partyName = ?)");
			
					System.out.println("Please type in a party name: ");
			
					String partyName = scan.nextLine();

					characters.setString(1, partyName);

					System.out.println("\n\n" + "List of people whose party is " + partyName + ": \n\n");

			

					ResultSet resultSet = characters.executeQuery();
			
					ResultSetMetaData metaData = resultSet.getMetaData();

					int numColumns = metaData.getColumnCount();
			
			
					for(int i=1; i<=numColumns; i++){

						System.out.print(metaData.getColumnName(i) + "\t");

					}
			
					System.out.println();
			
		
					while(resultSet.next()){
			    
						for(int col = 1; col <= numColumns; col++){

							System.out.print(resultSet.getObject(col)+"\t");

						}


						System.out.println();
					}
		    	
					characters.close();

		    		}
				
				// If option is "e", calculate and show the avrage speed or average range of a given weapon type.
				else if(option.equals("e")){
			
					PreparedStatement characters;
			
					System.out.println("Please type in what average you want: (attack_range or attack_speed)");
			
					String averageOfSomething = scan.nextLine();


					if(averageOfSomething.equals("attack_speed")){

						characters = connection.prepareStatement("select weaponType, avg(attackSpeed) as ? " + "from weapons group by weaponType");
						characters.setString(1, "avg_attack_speed");

					}
					
					else if(averageOfSomething.equals("attack_range")){

						characters = connection.prepareStatement("select weaponType, avg(attackRange) as ? " + "from weapons group by weaponType");
						characters.setString(1, "avg_attack_range");

					}
					
					else{
						System.out.println("No matched option, kill the program");
						break;
					}
					
					System.out.println("\n\n" + "Average " + averageOfSomething + " of each weapon type are: \n\n");

			
					
					ResultSet resultSet = characters.executeQuery();
			
					ResultSetMetaData metaData = resultSet.getMetaData();

					int numColumns = metaData.getColumnCount();
			
			
					for(int i=1; i<=numColumns; i++){

						System.out.print(metaData.getColumnName(i) + "\t");

					}
			
					System.out.println();
			
		
					while(resultSet.next()){
			    
						for(int col = 1; col <= numColumns; col++){

							System.out.print(resultSet.getObject(col)+"\t");

						}


						System.out.println();
					}
		    	
					characters.close();

		    		}
				
				// If option is "q", quit the program.
				else if(option.equals("q")){
			
					System.out.println("\nShutting down");
			
					isKeepRunning = false;
				}

				    connection.close();

			}

			catch(SQLException sqlException){
		
		    		JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
		    		System.exit(1);
			}

			catch(ClassNotFoundException classNotFound){
		    
		    		JOptionPane.showMessageDialog(null, classNotFound.getMessage(), "Driver Not Found", JOptionPane.ERROR_MESSAGE);

			}

			System.out.println();

		}
	}



	public static void main(String args[]){

	ConnectMySQL window = new ConnectMySQL();
	
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
