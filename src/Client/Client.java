package Client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;


public class Client 
{
	public static void main(String args[]) throws IOException 
	{
            
            Client_thread[] client=new Client_thread[3];
            Timer time=new Timer();
            int max_client=1;
            for(int i=0;i<max_client;i++)
            {
                client[i]=new Client_thread("result_client_no_"+(i+1));
                client[i].start();
  
            }
            
	}
}