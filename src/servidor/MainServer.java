package servidor;

import db.BDConnection;
import model.*;

public class MainServer {
	public static void main(String[] args) {
		BDConnection bd = BDConnection.getInstance();
		Server server = Server.getInstance(bd);
	}
} 