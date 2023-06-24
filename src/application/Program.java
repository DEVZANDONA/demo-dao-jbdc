package application;

import java.time.LocalDate;

import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(5,"BOOKS");
		Seller seller = new Seller(5,"Pedro","pedruco.zandona@gmail.com",LocalDate.now(),3000.00,obj);
		
		System.out.println(seller);

	}

}
