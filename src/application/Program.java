package application;

import java.util.List;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;


public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
		System.out.println("==TESTE1== findByID:");
		Seller seller = sellerDao.findById(6);
		System.out.println(seller);
		
		System.out.println("==TESTE2== findByDepartmentId");
		Department departmente = new Department(1,null);
		List<Seller> sellers = sellerDao.findByDepartment(departmente);
		for(Seller s : sellers) {
			System.out.println(s);
		}

	}

}
