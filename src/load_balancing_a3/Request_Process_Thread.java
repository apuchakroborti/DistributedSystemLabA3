package load_balancing_a3;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Request_Process_Thread extends Thread
{
	Socket socket;
	int buf_size;
	ArrayList<Integer> buffer;
	public Request_Process_Thread(Socket socket,ArrayList<Integer> buffer,int buf_size) 
	{
		this.socket = socket;
		this.buf_size = buf_size;
		this.buffer = buffer;
		start();
	}
	
	@Override
	public void run() 
	{
		try 
		{
			
			PrintStream writer = new PrintStream(socket.getOutputStream());
			Scanner scanner = new Scanner(socket.getInputStream());
			String line = "";
			
			while(scanner.hasNextLine()==true)
			{
				line = scanner.nextLine();
				
				if(line.compareTo("ACK")==0)
				{
					synchronized (buffer) 
					{
						if(buffer.size()==buf_size)  line = "N"+line;
					}
					
					writer.println(line);
					
				}
				else break;
				
			}
			
			
			
			
			int number = Integer.parseInt(line);
			
			synchronized (buffer) 
			{
				buffer.add(number);
			}
			
			sleep(1000);
			
			synchronized (buffer) 
			{
				buffer.remove(new Integer(number));
			}
			
			
			writer.println(number+" "+number*number);
			scanner.close();
			
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
