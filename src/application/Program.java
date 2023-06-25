package application;

import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("==TESTE1== findByID:");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);

	}

}
