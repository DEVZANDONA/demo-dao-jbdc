package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

public class SellerDaoJBDC implements SellerDao {

	Connection conn = null;

	public SellerDaoJBDC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("INSERT INTO SELLER (NAME,EMAIL,BIRTHDATE,BASESALARY,DEPARTMENTID) VALUES(?,?,?,?,?)", st.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			int rows = st.executeUpdate();
			
			if(rows>0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else
				throw new DbException("Erro inesperado nenhuma linha foi afetada");
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public void update(Seller obj) {

	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"SELECT seller.*, department.name as depname FROM seller INNER JOIN department ON "
					+ "seller.departmentId = department.Id where seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) { // Veio algum Resuldato?
				Department dp = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dp);
				return seller;
				
			} else
				return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}
	
	@Override
	public List<Seller> findByDepartment(Department id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("SELECT seller.*, department.name as depName FROM seller INNER JOIN department "
					+ "ON seller.departmentID = department.id"
					+ " WHERE seller.DepartmentId = ? ORDER BY Name");
			st.setInt(1, id.getId());
			rs = st.executeQuery();
			
			List<Seller> sellers = new ArrayList<Seller>();
			Map<Integer,Department> map = new HashMap<>();

				while(rs.next()) {
					Department dp = map.get(rs.getInt("DepartmentId"));
					if(dp == null) {
						dp = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dp);
					}
					sellers.add(instantiateSeller(rs, dp));
				}
				return sellers;
				
				
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("SELECT seller.*, department.name as depName FROM seller INNER JOIN department "
					+ "ON seller.departmentID = department.id"
					+ " ORDER BY Name");
			rs = st.executeQuery();
			
			List<Seller> sellers = new ArrayList<Seller>();
			Map<Integer,Department> map = new HashMap<>();

				while(rs.next()) {
					Department dp = map.get(rs.getInt("DepartmentId"));
					if(dp == null) {
						dp = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dp);
					}
					sellers.add(instantiateSeller(rs, dp));
				}
				return sellers;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller seller = new Seller();

		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setEmail(rs.getString("Email"));
		seller.setDepartment(dp);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("depname"));
		return dp;
	}


}
