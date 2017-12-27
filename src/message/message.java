package message;

import java.io.Serializable;

public class message implements Serializable
{

	private static final long serialVersionUID = -7048407600797927388L;
	private Object obj;
	private String message;
	
	public message(String message, Object obj) {
		this.message = message;
		this.obj = obj;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getObject() {
		return obj;
	}
}
