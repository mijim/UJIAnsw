package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import db.BDConnection;
import model.*;

public class ClientThread extends Thread {
	
	private Socket socket;
	private BDConnection bd;
	private Usuario user;
	 
	public ClientThread(Socket clientSocket, BDConnection bd) {
	    this.socket = clientSocket;
	    this.bd = bd;
	    this.user = new Usuario(1, "anonimo", "anonimo@anonimo.anonimo", "x");
	}
	
	public void run() {
		ObjectInputStream inObj = null;
		ObjectOutputStream outObj = null;
		String accion = "";
	    try {
			outObj = new ObjectOutputStream(socket.getOutputStream());
	    	inObj = new ObjectInputStream(socket.getInputStream());
	    	accion = inObj.readUTF();
	    	while(!accion.equals("logOff")) {
	    		System.out.println(accion);
	    		switch(accion) {
	    			//Acciones del usuario
	    			case "login":
	    				String[] auth = inObj.readUTF().split(":");
	    				Usuario usr = bd.logUsuario(auth[0], auth[1]);
	    				if(usr != null) {
	    					System.out.println("Logged in");
	    					user = usr;
	    					outObj.writeBoolean(true);
	    				}else {
	    					outObj.writeBoolean(false);
	    				}
	    				outObj.flush();
	    				break;
	    			case "getLastQuestions":
	    				outObj.writeObject(bd.getUltimasPreguntas(10));
	    				outObj.flush();
	    				break;
	    			case "getBestQuestions":
	    				outObj.writeObject(bd.getMejoresPreguntas(10));
	    				outObj.flush();
	    				break;
	    			case "getQuestionsByTitle":
	    				String palabra = inObj.readUTF();
	    				outObj.writeObject(bd.getPreguntasContienenTitulo(palabra));
	    				outObj.flush();
	    				break;
	    			default:
	    		}
	    		accion = inObj.readUTF();
	    	}
	    	socket.close();
	    	System.out.println("Conexión con el cliente cerrada");
	    }
		catch (IOException e) {
			System.err.println("Conexión cerrada con el cliente... ");
			e.printStackTrace();
		}
	    
	    
	}
}