package Server;

import load_balancing_a3.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Worker extends Thread 
{
	int buf_size,id;
	ArrayList<Integer> buffer;
	
	public Worker(int buf_size,int id) 
	{
		this.buf_size = buf_size;
		this.id = id;
		buffer = new ArrayList<>();
		start();
	}
	
	@Override
	public void run() 
	{
		try 
		{
			ServerSocket serverSocket = new ServerSocket(id);//worker server
			
			while(true)
			{
                                //worker client//waiting for for master request
				Socket socket = serverSocket.accept(); 
				//new Request_Processing(socket,buffer,buf_size);//sending 
				new Request_Processing(socket, buffer, buf_size,id);
			}
			
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
