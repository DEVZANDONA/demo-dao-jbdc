package model.dao;

import java.util.List;

import entities.Department;
import entities.Seller;

public interface SellerDao {

		void insert(Seller obj);
		void update(Seller obj);
		void deleteById(Seller id);
		Department findById(Seller id);
		List<Seller> findAll();
	
}
