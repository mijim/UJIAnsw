package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.*;

public class BDConnection {

	private volatile static BDConnection unicInstance;
	private Connection connBd;

	//Constructor privado para evitar instanciar más de una vez la clase
	private BDConnection() {
		try {
			String url = "jdbc:postgresql://rubenmarti.com:8080/dbdisenoraul";
			this.connBd = DriverManager.getConnection(url, "raul","PrsUqbW1");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Obtención de la instancia de clase
	public static BDConnection getInstance() {
		if(unicInstance == null) {
			synchronized (BDConnection.class) {
				if(unicInstance == null) {
					unicInstance = new BDConnection();
				}
			}
		}
		return unicInstance;
	}

	//Comprobar si el usuario existe con el mail y la contraseña para el login
	public Usuario logUsuario(String mail, String pass) {
		PreparedStatement st;
		Usuario user = null;
		try {
			st = connBd.prepareStatement("SELECT * FROM Usuario WHERE email=? AND pass=?");
			st.setString(1, mail);
			st.setString(2, pass);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				user = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	//Obtención de las x ultimas preguntas formuladas
	public List<Pregunta> getUltimasPreguntas(int numPreg) {
		List<Pregunta> ls = new LinkedList<Pregunta>();
		PreparedStatement st;
		Pregunta pr;
		try {
			st = connBd.prepareStatement("SELECT * FROM Pregunta ORDER BY fecha ASC LIMIT ?");
			st.setInt(1, numPreg);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				pr = new Pregunta(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
				ls.add(pr);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	//Obtención de las x preguntas con mejor valoración
	public List<Pregunta> getMejoresPreguntas(int numPreg){
		List<Pregunta> ls = new LinkedList<Pregunta>();
		PreparedStatement st;
		Pregunta pr;
		try {
			st = connBd.prepareStatement("SELECT * FROM Pregunta ORDER BY valoracion DESC LIMIT ?");
			st.setInt(1, numPreg);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				pr = new Pregunta(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
				ls.add(pr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	//Obtencion de las respuestas de una pregunta
	public List<Respuesta> getRespuestas(int idPreg){
		List<Respuesta> ls = new LinkedList<Respuesta>();
		PreparedStatement st;
		Respuesta res;
		try {
			st = connBd.prepareStatement("SELECT * FROM Respuesta WHERE id_pregunta = ?");
			st.setInt(1, idPreg);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				res = new Respuesta(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getDate(4), rs.getString(5), rs.getInt(6));
				ls.add(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	public List<Pregunta> getPreguntasContienenTitulo(String palabra){
		List<Pregunta> ls = new LinkedList<Pregunta>();
		PreparedStatement st;
		Pregunta pr;
		try {
			st = connBd.prepareStatement("SELECT * FROM pregunta WHERE UPPER(titulo) LIKE UPPER('%"+palabra+"%')");
			//st.setString(1, palabra);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				pr = new Pregunta(rs.getInt(1), rs.getInt(2),rs.getDate(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
				ls.add(pr);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}



	//Registrar un nuevo usuario
	public String newUsuario(Usuario user) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("INSERT INTO Usuario(nombre, email, pass) VALUES(?,?,?)");
			st.setString(1, user.getNombre());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPass());
			st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}

	//Añadir una nueva pregunta
	public String newPregunta(Pregunta preg) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("INSERT INTO Pregunta(id_usuario, fecha, titulo, subtitulo, valoracion, n_respuestas) VALUES(?,?,?,?,?,?)");
			st.setInt(1, preg.getIdUsuario());
			st.setDate(2, (Date) preg.getFecha());
			st.setString(3, preg.getTitulo());
			st.setString(4, preg.getSubtitulo());
			st.setInt(5, preg.getValoracion());
			st.setInt(6, preg.getNRespuestas());
			st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}

	//Añadir una respuesta a una pregunta
	public String newRespuesta(Respuesta res) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("INSERT INTO Respuesta(id_pregunta, id_usuario, fecha, txt_respuesta, valoracion) VALUES(?,?,?,?,?)");
			st.setInt(1, res.getIdPregunta());
			st.setInt(2, res.getIdUsuario());
			st.setDate(3, (Date) res.getFecha());
			st.setString(4, res.getTxtRespuesta());
			st.setInt(5, res.getValoracion());
			st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}

	//Añadir una nueva valoracion a una pregunta
	public String newValoracionPregunta(ValoracionPregunta vp) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("INSERT INTO valoracion_preg VALUES(?,?,?)");
			st.setInt(1, vp.getIdPregunta());
			st.setInt(2, vp.getIdUsuario());
			st.setBoolean(3, vp.getValoracion());
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}

	//Cambiar la valoracion de una pregunta
	public String cambiarValoracionPregunta(ValoracionPregunta vp) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("UPDATE valoracion_preg SET valoracion = NOT valoracion WHERE id_pregunta = ? AND id_usuario = ?");
			st.setInt(1, vp.getIdPregunta());
			st.setInt(2, vp.getIdUsuario());
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}

	//Añadir una nueva valoracion de una respuesta
	public String newValoracionRespuesta(ValoracionRespuesta vr) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("INSERT INTO valoracion_res VALUES(?,?,?,?)");
			st.setInt(1, vr.getIdPregunta());
			st.setInt(2, vr.getIdRespuesta());
			st.setInt(3, vr.getIdUsuario());
			st.setBoolean(4, vr.getValoracion());
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}

	//Cambiar una valoracion de una respuesta
	public String cambiarValoracionRespuesta(ValoracionRespuesta vr) {
		PreparedStatement st;
		try {
			st = connBd.prepareStatement("UPDATE valoracion_preg SET valoracion = NOT valoracion WHERE id_pregunta = ? AND id_usuario = ? AND id_respuesta = ?");
			st.setInt(1, vr.getIdPregunta());
			st.setInt(2, vr.getIdUsuario());
			st.setInt(3, vr.getIdRespuesta());
		} catch (SQLException e) {
			e.printStackTrace();
			return "error:x"; 			//TODO definir errores
		}
		return "correct";
	}
}
