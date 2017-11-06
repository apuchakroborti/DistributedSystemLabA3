package load_balancing_a3;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Send_Receive_Thread extends Thread
{
	int number,id;
	Socket socket;
	public Send_Receive_Thread(int number,int id,Socket socket) 
	{
		this.number = number;
		this.id = id;
		this.socket = socket;
		start();
		
	}
	
	@Override
	public void run() 
	{
		try 
		{
			
			Socket worker = new Socket("localhost", id);
			PrintStream writer = new PrintStream(worker.getOutputStream());
			Scanner scanner = new Scanner(worker.getInputStream());
			
			String line = "";
			
			while(true)
			{
				writer.println("ACK");
				line = scanner.nextLine();
				if(line.compareTo("ACK")==0) break;
				else
				{
					sleep(100);
				}
			}
			
			
			writer.println(number);
			
			line = scanner.nextLine();
			
			scanner.close();
			worker.close();
			
			synchronized (socket) 
			{
				PrintStream printStream = new PrintStream(socket.getOutputStream());
				printStream.println(line);//send
			}
			
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println(e.getMessage() );
		}
		
		
	}
}
