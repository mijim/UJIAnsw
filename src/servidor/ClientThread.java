package servidor;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import db.BDConnection;
import message.Mensaje;
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
		Mensaje mess = null;
	    try {
			outObj = new ObjectOutputStream(socket.getOutputStream());
	    	inObj = new ObjectInputStream(socket.getInputStream());
	    	mess = (Mensaje) inObj.readObject();
	    	while(!mess.getMessage().equals("logOff")) {
	    		switch(mess.getMessage()) {
	    			//Acciones del usuario
	    			case "login":
	    				List<String> auth = mess.getArgum();
	    				Usuario usr = bd.logUsuario(auth.get(0), auth.get(1));
	    				if(usr != null) {
	    					System.out.println("Logged in");
	    					user = usr;
	    					outObj.writeObject(new Mensaje("fail"));;
	    				}else {
	    					outObj.writeObject(new Mensaje("success"));
	    				}
	    				outObj.flush();
	    				break;
	    			case "getLastQuestions":
	    				outObj.writeObject(new Mensaje("success", bd.getUltimasPreguntas(Integer.parseInt(mess.getArgum().get(0)))));
	    				outObj.flush();
	    				break;
	    			case "getBestQuestions":
	    				outObj.writeObject(new Mensaje("success", bd.getMejoresPreguntas(Integer.parseInt(mess.getArgum().get(0)))));
	    				outObj.flush();
	    				break;
	    			case "getQuestionsByTitle":
	    				String palabra = inObj.readUTF();
	    				outObj.writeObject(bd.getPreguntasContienenTitulo(palabra));
	    				outObj.flush();
	    				break;
	    			default:
	    		}
	    		mess = (Mensaje) inObj.readObject();
	    	}
	    	socket.close();
	    	System.out.println("Conexión con el cliente cerrada... ");
	    }
		catch (IOException | ClassNotFoundException e) {
			System.err.println("Conexión cerrada inesperadamente con el cliente... ");
			e.printStackTrace();
		}
	    
	    
	}
}