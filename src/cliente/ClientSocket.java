package cliente;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import message.Mensaje;
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
	public List<Pregunta> getUltimasPreguntas(int numPreg){
		try {
			List<String> ls = new LinkedList<String>();
			ls.add(numPreg+"");
			outObj.writeObject(new Mensaje("getUltPreguntas", ls));
			outObj.flush();
			try {
				Mensaje ret = (Mensaje) inObj.readObject();
				return (List<Pregunta>) ret.getObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getMejoresPreguntas(int numPreg){
		try {
			List<String> ls = new LinkedList<String>();
			ls.add(numPreg+"");
			outObj.writeObject(new Mensaje("getMejPreguntas", ls));
			outObj.flush();
			try {
				Mensaje ret = (Mensaje) inObj.readObject();
				return (List<Pregunta>) ret.getObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getPreguntasContienenTitulo(String palabra){
		try {
			List<String> ls = new LinkedList<String>();
			ls.add(palabra);
			outObj.writeObject(new Mensaje("getTitPreguntas", ls));
			outObj.flush();
			try {
				Mensaje ret = (Mensaje) inObj.readObject();
				return (List<Pregunta>) ret.getObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String newPregunta(Pregunta preg){
		try {
			outObj.writeObject(new Mensaje("newPregunta", preg));
			outObj.flush();
			try {
				return ((Mensaje)inObj.readObject()).getMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String newRespuesta(Respuesta res){
		try {
			outObj.writeObject(new Mensaje("newRespuesta", res));
			outObj.flush();
			try {
				return ((Mensaje)inObj.readObject()).getMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String newValoracionPregunta(ValoracionPregunta vp) {
		try {
			outObj.writeObject(new Mensaje("newValPregunta", vp));
			outObj.flush();
			try {
				return ((Mensaje)inObj.readObject()).getMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String chValoracionPregunta(ValoracionPregunta vp) {
		try {
			outObj.writeObject(new Mensaje("chValPregunta", vp));
			outObj.flush();
			try {
				return ((Mensaje)inObj.readObject()).getMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}

	
	public String chValoracionRespuesta(ValoracionRespuesta vs) {
		try {
			outObj.writeObject(new Mensaje("chValRespuesta", vs));
			outObj.flush();
			try {
				return ((Mensaje)inObj.readObject()).getMessage();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String logUsuario(String email, String pass) {
			try {
				List<String> ls = new LinkedList<String>();
				ls.add(email);
				ls.add(pass);
				outObj.writeObject(new Mensaje("login", ls));
				outObj.flush();
				return ((Mensaje)inObj.readObject()).getMessage();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return "error";
	}
	
	public String newUsuario(Usuario user) {
		try {
			outObj.writeObject(new Mensaje("newUser", user));
			outObj.flush();
			return ((Mensaje)inObj.readObject()).getMessage();
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String newValoracionRespuesta(ValoracionRespuesta vr) {
		try {
			outObj.writeObject(new Mensaje("newValRes", vr));
			outObj.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "error";
	}
}
