/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author apu
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client_thread extends Thread{

    private String FileName;
    private Socket socket;
    private PrintStream writer;
    private PrintWriter pw;
    private Scanner socket_input,user_input;
    private int max_request;
    private int port;
    private String host;
    
    public Client_thread(String fileName) {
        FileName=fileName;
        user_input=new Scanner(System.in);
        host="localhost";
        //host="172.16.5.220";
        port=8080;
        max_request=100;
    }
    public void prnt(String str){
        System.out.print(str);
    }
    public void run()
    {
                 
        try {
            socket = new Socket(host,port);
            writer = new PrintStream(socket.getOutputStream());
            socket_input = new Scanner(socket.getInputStream());
            pw = new PrintWriter(new FileOutputStream(FileName+".txt",true));
			
        } catch (IOException ex) {
            prnt("Exception occur\n");
            Logger.getLogger(Client_thread.class.getName()).log(Level.SEVERE, null, ex);
        }
	//int max_request = 50;
        while(true)
        {
            for(int i=0;i<max_request;i++)
            {
                    //SecureRandom rand_number = new SecureRandom();
                    //int r = rand_number.nextInt(100)+1;

                    writer.println(i+1);
                    //w.println(r);//send
            }

            writer.println(-1);//send
            
            while(socket_input.hasNextInt()==true)
            {

                    String from_master = socket_input.nextLine();//receive

                    if(from_master.compareTo("-1")==0) break;
                    System.out.println(from_master);

                    pw.println(from_master);
            }
            pw.close();

            String ui=user_input.nextLine();//The servers and clients will run forever
            if(Integer.valueOf(ui)==-1){
                break;
            }
        }
    }
}
