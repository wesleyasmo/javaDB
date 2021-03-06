package br.edu.etec.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.etec.model.Cliente;
import br.edu.etec.model.Id;

public class ClienteJdbcDAO {

	private Connection conn;

	public ClienteJdbcDAO(Connection conn) {
		this.conn = conn;
	}

	public void salvar(Cliente c) throws SQLException {
		String sql = "insert into tbClientes (nome, endereco, fone, email) values ('" + c.getNome() + "','"
				+ c.getEndereco() + "','" + c.getFone() + "','" + c.getEmail() + "')";
		System.out.println(sql);
		PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
		prepareStatement.executeUpdate();
		prepareStatement.close();
	}

	public void alterar(Cliente cExample) {
		String sql = "update tbClientes set nome='" + cExample.getNome() + "',endereco='" + cExample.getEndereco()
				+ "',fone='" + cExample.getFone() + "',email='" + cExample.getEmail() + "' where pk_idCliente='"
				+ cExample.getId() + "';";
		System.out.println(sql);
		PreparedStatement prepareStatement;
		try {
			prepareStatement = this.conn.prepareStatement(sql);
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void excluir(int id) {
		String sql = "delete from tbClientes where pk_idCliente='" + id + "';";
		System.out.println(sql);
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Cliente> listar() {
		String sql = "select * from tbClientes";
		System.out.println(sql);
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("pk_idCliente");
				String nome = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String fone = rs.getString("fone");
				String email = rs.getString("email");
				Cliente cliente = new Cliente();
				cliente.setId(id);
				cliente.setNome(nome);
				cliente.setEndereco(endereco);
				cliente.setFone(fone);
				cliente.setEmail(email);
				clientes.add(cliente);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	public List<Id> listarIds() {
		String sql = "select pk_idCliente from tbClientes";
		System.out.println(sql);
		List<Id> ids = new ArrayList<Id>();
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				int pk = rs.getInt("pk_idCliente");
				Id id= new Id();
				id.setId(pk);
				ids.add(id);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}

	public Cliente findById(Integer id) {
		String sql = "select * from tbClientes where pk_idCliente = " + id;
		System.out.println(sql);
		Cliente cliente = null;
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				cliente = new Cliente();
				String nome = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String fone = rs.getString("fone");
				String email = rs.getString("email");

				cliente.setId(id);
				cliente.setNome(nome);
				cliente.setEndereco(endereco);
				cliente.setFone(fone);
				cliente.setEmail(email);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}
}