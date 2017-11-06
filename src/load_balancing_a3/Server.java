package load_balancing_a3;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server 
{
	public static void main(String args[]) throws IOException 
	{
		int worker_number = 5;
		
		int worker_buf_size = 20;
		int worker_id[] = new int[worker_number];
		
		for(int i=0;i<worker_number;i++)
		{
			new Worker(worker_buf_size,1235+i);
			worker_id[i] = 1235+i;
		}
		
		
		
		ServerSocket serverSocket = new ServerSocket(1234);//server
		
		int buf_size = 50;
		ArrayList<Integer> buffer = new ArrayList<>();
		
		while(true)
		{
			Socket socket = serverSocket.accept(); //client
			new Server_Thread(socket,buffer,buf_size,worker_id);
		}


	}
}