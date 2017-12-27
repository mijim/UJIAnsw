package cliente;

import java.io.*;
import java.net.Socket;
import java.util.List;

import model.*;

public class ClientSocket {
	
	private volatile static ClientSocket unicInstance;
	
	private ObjectOutputStream outObj;
	private ObjectInputStream inObj;
	private Socket clientSocket;
	
	private ClientSocket() {
		 try {
			clientSocket = new Socket("localhost", 1978);
			System.out.println("Conexión con el servidor realizada con éxito");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 try {
			outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			inObj = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ClientSocket getInstance() {
		if(unicInstance == null) {
			synchronized(ClientSocket.class) {
				if(unicInstance == null) {
					unicInstance = new ClientSocket();
				}
			}
		}
		return unicInstance;
	}
	
	
	//Acciones del usuario
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getUltimasPreguntas(){
		try {
			outObj.writeUTF("getLastQuestions");
			outObj.flush();
			try {
				return (List<Pregunta>) inObj.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getMejoresPreguntas(){
		List<Pregunta> ls = null;
		try {
			outObj.writeUTF("getBestQuestions");
			outObj.flush();
			try {
				ls = (List<Pregunta>) inObj.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ls;
		}
		return ls;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getPreguntasContienenTitulo(String palabra){
		try {
			outObj.writeUTF("getQuestionsByTitle");
			outObj.flush();
			outObj.writeUTF(palabra);
			outObj.flush();
			try {
				return (List<Pregunta>) inObj.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean logUsuario(String email, String pass) {
			try {
				outObj.writeUTF("login");
				outObj.flush();
				outObj.writeUTF(email + ":" + pass);
				outObj.flush();
				return inObj.readBoolean();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
	}
}
