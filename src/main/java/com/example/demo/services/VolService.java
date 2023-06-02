package com.example.demo.services;

import com.example.demo.daoImp.VolDaoImp;
import com.example.demo.entities.Vol;

import java.io.IOException;
import java.util.List;

public class VolService {

	private VolDaoImp volsdaDaoImp = new VolDaoImp();

	public void save(Vol vol){
		volsdaDaoImp.insert(vol);
	}
	
	public void update(Vol vol) {
		volsdaDaoImp.update(vol);
	}
	
	public void remove(Integer id){
		volsdaDaoImp.deleteById(id);
	}

	public List<Vol> findAll() {
			return volsdaDaoImp.findAll();
	}

	public Vol getVolByID(Integer id)
	{
		return volsdaDaoImp.findByID(id);
	}

	public void importDataTxt() throws Exception {  volsdaDaoImp.updateDataFromTxtFile();}

	public void exportTxt(List<Vol> vols) {volsdaDaoImp.exportDataToTxtFile(vols);}
	public void importXL() throws Exception {  volsdaDaoImp.updateDataFromXLFile();}
	public void exportDataXLFile() throws IOException { volsdaDaoImp.exportDataToXLFile();}

	
}
