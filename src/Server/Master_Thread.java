package Server;
import load_balancing_a3.*;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Master_Thread extends Thread
{
    int number;
    int worker_ind_id;
    Socket socket;
    public Master_Thread(int number,int id,Socket socket) 
    {
            this.number = number;//requested number
            this.worker_ind_id = id;//worker id
            this.socket = socket;//requested client
            start();

    }

    @Override
    public void run() 
    {
        try 
        {

            Socket worker = new Socket("localhost", worker_ind_id);//socket for worker
            PrintStream writer = new PrintStream(worker.getOutputStream());
            Scanner scanner = new Scanner(worker.getInputStream());

            String line = "";

            while(true)
            {
                    writer.println("Y");
                    line = scanner.nextLine();
                    if(line.compareTo("Y")==0) break;
                    else
                    {
                            sleep(100);
                    }
            }


            writer.println(number);//sent to worker 

            line = scanner.nextLine();//receive from worker
            prnt("Receive from worker="+line+"\n");
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
    public void prnt(String str)
    {
        System.out.print(str);
    }
}
