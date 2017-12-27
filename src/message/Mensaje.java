package message;

import java.io.Serializable;
import java.util.List;

public class Mensaje implements Serializable
{

	private static final long serialVersionUID = -7048407600797927388L;
	private Object obj;
	private String message;
	private List<String> arg;
	
	public Mensaje(String message, List<String> arg, Object obj) {
		this.message = message;
		this.arg = arg;
		this.obj = obj;
	}
	
	public Mensaje(String message) {
		this.message = message;
		this.arg = null;
		this.obj = null;
	}
	
	public Mensaje(String message, List<String> arg) {
		this.message = message;
		this.arg = arg;
		this.obj = null;
	}
	
	public Mensaje(String message, Object obj) {
		this.message = message; 
		this.arg = null;
		this.obj = obj;
	}
	
	public String getMessage() {
		return message;
	}
	
	public List<String> getArgum() {
		return arg;
	}
	
	public Object getObject() {
		return obj;
	}
}
