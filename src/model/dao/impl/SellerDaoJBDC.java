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
					"SELECT seller.*, department.name as depname FROM seller INNER JOIN department ON seller.departmentId = department.Id where seller.Id = ?");
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

		return null;
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
