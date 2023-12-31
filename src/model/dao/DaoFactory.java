package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJBDC;

public class DaoFactory {
	
	//Implementando a Factory --> Classe responsavel por instacianr meus Daos, assim escondendo as Implementações como JBDC
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJBDC(DB.getConnection());
	}
	
	public static DepartmenteDao createDepartmenteDao() {
		return null;
	}
	
	
}
