package load_balancing_a3;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server_Thread extends Thread
{
	Socket socket;
	int buf_size;
	ArrayList<Integer> buffer;
	int worker_id[];
	public Server_Thread(Socket socket,ArrayList<Integer> buffer,int buf_size,int worker_id[]) 
	{
		this.socket = socket;
		this.buffer = buffer;
		this.buf_size = buf_size;
		this.worker_id = worker_id;
		start();
	}
	
	public void receive()
	{
		try 
		{
			
			Scanner sc = new Scanner(socket.getInputStream());
			PrintStream printStream = new PrintStream(socket.getOutputStream());
			
			while(true)
			{
				
				
				int i = buffer.size();
				int k = i;
				while(sc.hasNextInt()==true)
				{
					
					int number = sc.nextInt();//receive
					if(number==-1) break;
					if(buffer.size()<buf_size) buffer.add(number);
				}
				
				/*********************************************/
				
				ArrayList<Send_Receive_Thread> arrayList = new ArrayList<>();
				
				for(;i<buffer.size();i++)//load balance
				{
					
					int square = buffer.get(i);
					
					int select = worker_id[i%worker_id.length];
					
					arrayList.add(new Send_Receive_Thread(square, select,socket));
					
				}
				
				for (Send_Receive_Thread send_Receive_Thread : arrayList) 
				{
					send_Receive_Thread.join();
				}
				
				
				printStream.println("-1");//send
				
				/*********************************************/
				
				for(i=buffer.size()-1;i>=k;i--)
				{
					buffer.remove(i);
				}
					
				
				
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	
	@Override
	public void run() 
	{
		
		receive();
		
	}
}
