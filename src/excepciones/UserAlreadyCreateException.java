package excepciones;

public class UserAlreadyCreateException extends Exception{
	private static final long serialVersionUID = -1193464333264796164L;
	public UserAlreadyCreateException() { super(); }
	public UserAlreadyCreateException(String message) { super(message); }
}
