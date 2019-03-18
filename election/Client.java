import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.*;



public class Client {

    private Client() {}

    public static void main(String[] args) 
    
    {
 
    	int flag=0;
    	Scanner s=new Scanner(System.in); 
    	
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response: " + response);
            
            // display catalog
            while (flag==0)
		     {
		     	System.out.println("Welcome to Vote System!");	
		     	System.out.println("Please follow the three steps:");
		     	System.out.println("1. Register");
		     	System.out.println("2. Vote");
		     	System.out.println("3. Voting Results");
		     	System.out.println("0. Quit");
				System.out.println("-----------------------------");
				
		     	int choice=s.nextInt();
		       
		     	{
		     		switch (choice)
		     		{
		     			case 1:
		 
		     				    System.out.print("Please enter your name:");
		     					String VName=s.next();
								System.out.println("Your are registered with the id: ");
								int VID=stub.registerVoter(VName);
								System.out.println(VID);
								System.out.println("Please remember ID for voting");
		     					System.out.println("-----------------------------");
					 	
		     				    break;
		     			
		     			case 2:
		     				
							
							System.out.println("Please Enter you VoterId: ");
							int id=s.nextInt();
							
							// verify ID
						    while(stub.VerifyID(id)) {
							System.out.println("The ID is wrong, please check again.");
							System.out.println("Please Enter you VoterId: ");
							id=s.nextInt();
							}
						    System.out.println("The ID is correct, you can proceed to vote.");
							System.out.println("Here is Candidate List");
							
							ArrayList<String> names= stub.names();
							for(int i=0;i<names.size();i++)
							{	System.out.print(i+1);
							    System.out.print(".");
								System.out.println(names.get(i));
							}
							System.out.println(" ");
							System.out.println("Please Enter the name of the candiate that you would like make a vote: ");
							int candidate=s.nextInt()-1;
							System.out.println(stub.Vote(id,candidate));
							System.out.println("-----------------------------");
		     				break;
		     			
		     			case 3:

		     				
		     				 System.out.println("The votes each of the candidates received are:");
							 System.out.println(" ");
							 System.out.println(stub.results());
							 System.out.println("-----------------------------");
		     				break;
		     			
		     			case 0:
		     				flag++;
		     				System.out.println("Vote System Closed");
		     				System.out.println("-----------------------------");
		     		}		     		
		         	}		
		     	}
		     
            s.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}