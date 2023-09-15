package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
	
	public static Connection obterConexao() {

		String url = "jdbc:postgresql://localhost:5432/Escola";
		String usuario = "postgres";
		String senha = "serratec";

		Connection conexao = null;

		try {
			conexao = DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException e) {
			System.out.println("Não foi possivel obter conexão!");
			System.out.println("Erro " + e.getMessage());
		}
		return conexao;
	}
}