package model;

import java.io.Serializable;

public class ValoracionPregunta implements Serializable{
	private static final long serialVersionUID = 2041452797346929543L;
	private int idPregunta;
	private int idUsuario;
	private Boolean valoracion;

	public ValoracionPregunta(int id_pregunta, int id_usuario, boolean valoracion) {
		this.idPregunta=id_pregunta;
		this.idUsuario=id_usuario;
		this.valoracion=valoracion;
	}

	public int getIdPregunta() { return idPregunta; }
	public void setIdPregunta(int id_pregunta) { this.idPregunta = id_pregunta; }

	public int getIdUsuario() { return idUsuario; }
	public void setIdUsuario(int id_usuario) { this.idUsuario = id_usuario; }

	public boolean getValoracion() { return valoracion; }
	public void setValoracion(boolean valoracion) { this.valoracion = valoracion; }
}