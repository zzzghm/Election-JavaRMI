

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import java.util.HashMap;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
    
    // generate a random 8-digit ID 
    int registerVoter(String x) throws RemoteException;
    
    // dispaly the candidates 
	ArrayList<String> names() throws RemoteException;
	
	// verify ID
    boolean VerifyID(int x) throws RemoteException;
    
    // vote with ID and candidate's name
    //String Vote(int id,String candidate) throws RemoteException;
    // tried using string insead of int, can't figure out what's wrong, turn to int, see details in report
    String Vote(int id,int candidate) throws RemoteException;
    
    // return the result with candidates and the number of votes
    HashMap<String, Integer> results() throws RemoteException;
  
}
