package cliente;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import excepciones.UserAlreadyCreateException;
import message.Mensaje;
import model.*;

public class ClientSocket {

	private volatile static ClientSocket unicInstance;

	private ObjectOutputStream outObj;
	private ObjectInputStream inObj;
	private Socket clientSocket;

	private ClientSocket() throws IOException{
		clientSocket = new Socket("localhost", 1978);
		System.out.println("Conexión con el servidor realizada con éxito");
		outObj = new ObjectOutputStream(clientSocket.getOutputStream());
		inObj = new ObjectInputStream(clientSocket.getInputStream());
	}

	public static ClientSocket getInstance() throws IOException {
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
	public List<Pregunta> getUltimasPreguntas(int numPreg) throws IOException, ClassNotFoundException{
		List<String> ls = new LinkedList<String>();
		ls.add(numPreg+"");
		outObj.writeObject(new Mensaje("getUltPreguntas", ls));
		outObj.flush();
		Mensaje ret = (Mensaje) inObj.readObject();
		return (List<Pregunta>) ret.getObject();
	}

	@SuppressWarnings("unchecked")
	public List<Pregunta> getMejoresPreguntas(int numPreg) throws IOException, ClassNotFoundException{
		List<String> ls = new LinkedList<String>();
		ls.add(numPreg+"");
		outObj.writeObject(new Mensaje("getMejPreguntas", ls));
		outObj.flush();
		Mensaje ret = (Mensaje) inObj.readObject();
		return (List<Pregunta>) ret.getObject();
	}


	@SuppressWarnings("unchecked")
	public List<Pregunta> getPreguntasContienenTitulo(String palabra) throws IOException, ClassNotFoundException{
		List<String> ls = new LinkedList<String>();
		ls.add(palabra);
		outObj.writeObject(new Mensaje("getTitPreguntas", ls));
		outObj.flush();
		Mensaje ret = (Mensaje) inObj.readObject();
		return (List<Pregunta>) ret.getObject();
	}

	public String newPregunta(Pregunta preg) throws IOException, ClassNotFoundException{
		outObj.writeObject(new Mensaje("newPregunta", preg));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();
	}

	public String newRespuesta(Respuesta res) throws IOException, ClassNotFoundException{
		outObj.writeObject(new Mensaje("newRespuesta", res));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();
	}

	public String newValoracionPregunta(ValoracionPregunta vp) throws IOException, ClassNotFoundException{
		outObj.writeObject(new Mensaje("newValPregunta", vp));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();
	}

	public String chValoracionPregunta(ValoracionPregunta vp) throws IOException, ClassNotFoundException{
		outObj.writeObject(new Mensaje("chValPregunta", vp));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();
  	}
 

	public String chValoracionRespuesta(ValoracionRespuesta vs) throws IOException, ClassNotFoundException{
		outObj.writeObject(new Mensaje("chValRespuesta", vs));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();
	}

	public String logUsuario(String email, String pass) throws IOException, ClassNotFoundException{
		List<String> ls = new LinkedList<String>();
		ls.add(email);
		ls.add(pass);
		outObj.writeObject(new Mensaje("login", ls));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();

	}

	public String newUsuario(Usuario user) throws IOException, ClassNotFoundException, UserAlreadyCreateException{
		outObj.writeObject(new Mensaje("newUser", user));
		outObj.flush();
		if(((Mensaje)inObj.readObject()).getMessage().equals("error")){
			throw new UserAlreadyCreateException("Usuario con correo " + user.getEmail() + " ya existente");
		}
		return ((Mensaje)inObj.readObject()).getMessage();

	}

	public String newValoracionRespuesta(ValoracionRespuesta vr) throws IOException, ClassNotFoundException {
		outObj.writeObject(new Mensaje("newValRes", vr));
		outObj.flush();
		return ((Mensaje)inObj.readObject()).getMessage();

	}
}
