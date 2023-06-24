package model.dao;

import model.dao.impl.SellerDaoJBDC;

public class DaoFactory {
	
	//Implementando a Factory --> Classe responsavel por instacianr meus Daos, assim escondendo as Implementa��es como JBDC
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJBDC();
	}
	
	public static DepartmentDao createDepartmenteDao() {
		return new DepartmenteDaoJBDC();
	}
}
