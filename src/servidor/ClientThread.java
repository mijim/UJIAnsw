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
	 
	public ClientThread(Socket clientSocket, BDConnection bd) {
	    this.socket = clientSocket;
	    this.bd = bd;
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
//	    					System.out.println("Logged in");
	    					outObj.writeObject(new Mensaje("succes",usr));;
	    				}else {
	    					outObj.writeObject(new Mensaje("fail"));
	    				}
	    				break;
	    			case "getUltPreguntas":
	    				outObj.writeObject(new Mensaje("success", bd.getUltimasPreguntas(Integer.parseInt(mess.getArgum().get(0)))));
	    				break;
	    			case "getMejPreguntas":
	    				outObj.writeObject(new Mensaje("success", bd.getMejoresPreguntas(Integer.parseInt(mess.getArgum().get(0)))));
	    				break;
	    			case "getTitPreguntas":
	    				outObj.writeObject(new Mensaje("success", bd.getPreguntasContienenTitulo(mess.getArgum().get(0))));
	    				break;
	    			case "newUser":
	    				outObj.writeObject(new Mensaje(bd.newUsuario((Usuario) mess.getObject())));
	    				break;
	    			case "newValRes":
	    				bd.newValoracionRespuesta((ValoracionRespuesta) mess.getObject());
	    				outObj.writeObject(new Mensaje("Success"));
	    				break;
	    			case "newPregunta":
	    				bd.newPregunta((Pregunta)mess.getObject());
	    				outObj.writeObject(new Mensaje("success"));
	    				break;
	    			case "newRespuesta":
	    				bd.newRespuesta((Respuesta)mess.getObject());
	    				outObj.writeObject(new Mensaje("success"));
	    				break;
	    			case "newValPregunta":
	    				bd.newValoracionPregunta((ValoracionPregunta)mess.getObject());
	    				outObj.writeObject(new Mensaje("sucess"));
	    				break;
	    			case "chValPregunta":
	    				bd.cambiarValoracionPregunta((ValoracionPregunta)mess.getObject());
	    				outObj.writeObject(new Mensaje("success"));
	    				break;
	    			case "newValRespuesta":
	    				bd.newValoracionRespuesta((ValoracionRespuesta)mess.getObject());
	    				outObj.writeObject(new Mensaje("sucess"));
	    				break;
	    			case "chValRespuesta":
	    				bd.cambiarValoracionRespuesta((ValoracionRespuesta)mess.getObject());
	    				outObj.writeObject(new Mensaje("success"));
	    				break;
	    			case "getRespuestas":
	    				outObj.writeObject(new Mensaje("success",bd.getRespuestas(Integer.parseInt(mess.getArgum().get(0)))));
	    				break;
	    			case "getUsuarioById":
	    				outObj.writeObject(new Mensaje("success",bd.getUsuarioById(Integer.parseInt(mess.getArgum().get(0)))));
	    				break;
	    			case "getValoracionPregunta":
	    				outObj.writeObject(new Mensaje("success",bd.getValoracionPregunta(Integer.parseInt(mess.getArgum().get(0)), Integer.parseInt(mess.getArgum().get(1)))));
	    				break;
	    			case "getValoracionRespuesta":
	    				outObj.writeObject(new Mensaje("success",bd.getValoracionRespuesta(Integer.parseInt(mess.getArgum().get(0)), Integer.parseInt(mess.getArgum().get(1)),Integer.parseInt(mess.getArgum().get(2)))));
	    				break; 
	    			case "getPreguntasByIdUsuario":
	    				outObj.writeObject(new Mensaje("success", bd.getPreguntasByIdUsuario(Integer.parseInt(mess.getArgum().get(0)))));
	    				break;
	    			default:

	    		}
	    		outObj.flush();
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