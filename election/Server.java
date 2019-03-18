import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.awt.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.Scanner;
import java.util.HashMap;


public class Server implements Hello {
	
	public Server() {
    
    }

    public String sayHello() {
        return "Hello, Election!";
    }
    
	// return the result with candidates and the number of votes
	public static HashMap<String, Integer> result = new HashMap<String,Integer>(); 
    
	// Voter ID and register name
	public static ArrayList<Integer> VoterID= new ArrayList<Integer>();
	public static ArrayList<String> Voter= new ArrayList<String>();
	
    // check if vote before
	public static ArrayList<String> Vote= new ArrayList<String>();
	
	// store voter ID and candidate name
	public static  ArrayList<String> temps = new ArrayList<String>();

	// generate a random 8-digit ID
    private int  RandomID() 
    {       
        Random ran =new Random();
        int num = 10000000 + ran.nextInt(90000000);
        return num;
	}
 
    
    
    // input your name to register a vote ID
    public int registerVoter(String Vname) throws RemoteException 
	{
		
       	Voter.add(Vname);
        int x=RandomID();
    	VoterID.add(x);
		Vote.add(null);
		System.out.println("Registered Voters: ");
		for (int i=0; i<Voter.size();i++) {
			System.out.print(Voter.get(i));
			System.out.print(":");
			System.out.println(VoterID.get(i));
		}
        return x;
    }
	
    // return the candidates name list
    public ArrayList<String> names() throws RemoteException 
	{	 
		return temps;
	}   
 
    // verify ID
    public boolean VerifyID(int id) 
    {	
			int a = VoterID.indexOf(id);
    		if (a != -1){
                String name=Voter.get(a);
                return false;
            }
			return true;
    }
    // vote with ID and candidate's name
	public String Vote(int id,int candidate)
 	{
		int a = VoterID.indexOf(id);

		// Not verified
    	if (a == -1){
				return "The Voter ID does not exit, Please check again.";
		}
		
		// Already voted
		if(Vote.get(a) != null){
			return "The Voter ID has already voted, each ID can vote only once.";
		}

		// Vote
		String cand = temps.get(candidate);
		Vote.set(a, cand);
		result.put(cand, result.get(cand) + 1);
	
		return "Your vote has been submited, thank you for voting.";
 	} 
 
	// return the result with candidates and the number of votes
    public HashMap<String, Integer> results() throws RemoteException 
        {	
    	  System.out.println("-----------------------------");
          System.out.println("Current Voting Result: ");
          System.out.println(result);
       // store the result to a file 
          /*FileOutputStream f=new FileOutputStream("VoteResult.ser");
          ObjectOutputStream s=new ObjectOutputStream(f);
          s.writeObject(result);
          s.flush();
          s.close();
          f.close();*/
          
            return result;
        }   

    public static void main(String args[]) throws IOException {
        
        try {
        	
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.err.println("Server ready");
             
            // read the candidate pool list
            Scanner txtFile = new Scanner(new File("candidate.txt")).useDelimiter(",\\s*");
            String list="";
         
            while (txtFile.hasNext()) 
            {
             
              list = txtFile.next();
              temps.add(list);
            }
            txtFile.close();
            
            String[] tempsArray = temps.toArray(new String[0]);
            System.out.println("-----------------------------");
            System.out.println("Candidates Pool: ");
            int i=1;
            for (String s : tempsArray) 
            {
              System.out.print(i);
              i=i+1;
              System.out.print(".");
              System.out.println(s); 
			  result.put(s,0);
            }
           System.out.println(" ");
           System.out.println("-----------------------------");
           System.out.println("Current Voting Result: ");
           System.out.println(result);
           
           // store the result to a file 
           FileOutputStream f=new FileOutputStream("VoteResult.ser");
           ObjectOutputStream s=new ObjectOutputStream(f);
           s.writeObject(result);
           s.flush();
           s.close();
           f.close();
	    }
       	 catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}