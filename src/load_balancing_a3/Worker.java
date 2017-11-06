package load_balancing_a3;
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
			ServerSocket serverSocket = new ServerSocket(id);//server
			
			while(true)
			{
				Socket socket = serverSocket.accept(); //client
				new Request_Process_Thread(socket,buffer,buf_size);
				
			}
			
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
