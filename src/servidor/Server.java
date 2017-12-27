package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import db.BDConnection;

public class Server {
	
	    private static final int PORT = 1978;
	    private static List<Socket> socketList = new LinkedList<Socket>();
	    private volatile static Server unicInstance;
	    private BDConnection bd;
	   
	    private Server(BDConnection bd){
	    	this.bd = bd;
	        ServerSocket serverSocket = null;
	        Socket socket = null;
	        System.out.println("Arrancando servidor...");
	        try {
	            serverSocket = new ServerSocket(PORT);
	        } catch (IOException e) {
	            e.printStackTrace();

	        }
	        System.out.println("Servidor abierto en el puerto: " + PORT + " direccion: " + serverSocket.getInetAddress().toString());
	        while (true) {
	            try {
	                socket = serverSocket.accept();
	                socketList.add(socket);
	               
	            } catch (IOException e) {
	                System.out.println("I/O error: " + e);
	            }
	            // new thread for a client
	            new ClientThread(socket, bd).start();
	        }
	    }
	    
	    public static Server getInstance(BDConnection bd) {
	    	if(unicInstance == null) { 
	    		synchronized(Server.class) {
	    			if(unicInstance == null) {
	    				unicInstance = new Server(bd);
	    			}
	    		}
	    	}
	    	return unicInstance;
	    }
	    
	    public static List<Socket> getSocketList(){
	    	return socketList;
	    }
	    
}

