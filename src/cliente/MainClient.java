package cliente;

import model.Pregunta;

public class MainClient {
	public static void main(String[] args) {
		ClientSocket cs = ClientSocket.getInstance();
		for(Pregunta pr: cs.getMejoresPreguntas()) {
			System.out.println(pr.getTitulo());
		}
		
		System.out.println("");
		
		for(Pregunta pr: cs.getUltimasPreguntas()) {
			System.out.println(pr.getTitulo());
		}
		
		System.out.println(cs.logUsuario("anonimo@anonimo.anonimo", "x"));
	}
}