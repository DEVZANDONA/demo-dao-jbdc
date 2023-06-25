package application;

import java.util.Date;
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
		
		System.out.println();
		System.out.println("==TESTE2== findAll()");
		Department departmente = new Department(1,null);
		List<Seller> sellers = sellerDao.findByDepartment(departmente);
		for(Seller s : sellers) {
			System.out.println(s);
		}
		System.out.println();
		
		System.out.println("==TESTE3== findByDepartment");
		sellers = sellerDao.findAll();
		for(Seller s : sellers) {
			System.out.println(s);
		}
		System.out.println();
		
		System.out.println("==TESTE4== Insert()");
		seller = new Seller(null,"Pedro","pedruco.zandona@gmail.com",new Date(), 4000.00,departmente);
		sellerDao.insert(seller);
		System.out.println("New Seller ID:"+seller.getId());

	}

}
