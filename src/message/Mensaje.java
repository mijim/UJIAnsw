package message;

import java.io.Serializable;

public class Mensaje implements Serializable
{

	private static final long serialVersionUID = -7048407600797927388L;
	private Object obj;
	private String message;
	private String arg;
	
	public Mensaje(String message, String arg, Object obj) {
		this.message = message;
		this.arg = arg;
		this.obj = obj;
	}
	
	public Mensaje(String message) {
		this.message = message;
		this.arg = "";
		this.obj = null;
	}
	
	public Mensaje(String message, String arg) {
		this.message = message;
		this.arg = arg;
		this.obj = null;
	}
	
	public Mensaje(String message, Object obj) {
		this.message = message;
		this.arg = "";
		this.obj = obj;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getArgum() {
		return arg;
	}
	
	public Object getObject() {
		return obj;
	}
}
