package servidor;

import db.BDConnection;

public class MainServer {
	public static void main(String[] args) {
		BDConnection bd = BDConnection.getInstance();
		Server.getInstance(bd);
	}
} 