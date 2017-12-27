package cliente;

import java.io.IOException;

import excepciones.UserAlreadyCreateException;
import model.Pregunta;
import model.Usuario;

public class MainClient {
	public static void main(String[] args) {
		try {
			ClientSocket cs = ClientSocket.getInstance();
			for(Pregunta pr: cs.getMejoresPreguntas(10)) {
				System.out.println(pr.getTitulo());
			}

			System.out.println("");

			for(Pregunta pr: cs.getUltimasPreguntas(10)) {
				System.out.println(pr.getTitulo());
			}

			System.out.println(cs.logUsuario("anonimo@anonimo.anonimo", "x"));

			System.out.println("PRUEBAS DE RAUL");
			for(Pregunta pr: cs.getPreguntasContienenTitulo("hora")) {
				System.out.println(pr.getIdPregunta());
				System.out.println(pr.getTitulo());
			}
			System.out.println("Inserto un user");
			try {
				System.out.println(cs.newUsuario(new Usuario("Paco Perez", "paco@gmail.com", "a")));
			} catch (UserAlreadyCreateException e) {
				System.err.println("El usuario ya existe");
			}

		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}