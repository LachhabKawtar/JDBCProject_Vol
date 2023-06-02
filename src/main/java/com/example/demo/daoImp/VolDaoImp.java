package com.example.demo.daoImp;

import com.example.demo.dao.DaoVol;
import com.example.demo.entities.Vol;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;


public class VolDaoImp implements DaoVol {

	private Connection conn = DB.getConnection();

	@Override
	public void insert(Vol vol) {
		PreparedStatement ps = null;
		if (conn == null)
			System.out.println("yes");
		try {
			ps = conn.prepareStatement("INSERT INTO vol (dateA,dateD,aeroportA,aeroportD,nbrEscales)"
					+ " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1,  new Date(vol.getDateA().getTime()));
			ps.setDate(2,  new Date(vol.getDateD().getTime()));
			ps.setString(3, vol.getAeroportA());
			ps.setString(4, vol.getAeroportD());
			ps.setInt(5, vol.getNbrEscales());
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					vol.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				System.out.println("Aucune ligne renvoyée");
			}
		} catch (SQLException e) {
			System.err.println("problème d'insertion d'un vol!!!");
			;
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Vol vol) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE vol SET dateA = ?,dateD =?, aeroportA = ? , aeroportD = ?," +
					"nbrEscales=? WHERE Id = ?");
			ps.setDate(1, (java.sql.Date) new Date(vol.getDateA().getTime()));
			ps.setDate(2, (java.sql.Date) new Date(vol.getDateD().getTime()));
			ps.setString(3, vol.getAeroportA());
			ps.setString(4, vol.getAeroportD());
			ps.setInt(5, vol.getNbrEscales());
			ps.setInt(6, vol.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("problème de mise à jour d'un vol");
			;
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Vol> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM vol");
			rs = ps.executeQuery();
			List<Vol> vols = new ArrayList<>();
			while (rs.next()) {
				Vol vol = new Vol();
				vol.setId(rs.getInt("Id"));
				vol.setAeroportA(rs.getString("aeroportA"));
				vol.setAeroportD(rs.getString("aeroportD"));
				vol.setDateA(rs.getDate("dateA"));
				vol.setDateD(rs.getDate("dateD"));
				vol.setNbrEscales(rs.getInt("nbrEscales"));
				vols.add(vol);
			}

			return vols;
		} catch (SQLException e) {
			System.err.println("problème de requête pour sélectionner un département");
			;
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Vol> importDataFromTxtFile() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.txt"));

		ArrayList<Vol> list = new ArrayList<Vol>();
		String readLine = br.readLine();

		while (readLine != null) {
			String[] vol = readLine.split(",");
			Vol p = new Vol();
			System.out.println(vol[0]);
			p.setId(Integer.parseInt(vol[0].trim()));
			p.setDateA(new SimpleDateFormat("dd/MM/yyyy").parse(vol[1]));
			p.setDateD(new SimpleDateFormat("dd/MM/yyyy").parse(vol[2]));
			p.setAeroportA(vol[3].trim());
			p.setAeroportD(vol[4].trim());
			p.setNbrEscales(Integer.parseInt(vol[5].trim()));
			System.out.println(p);
			list.add(p);
			readLine = br.readLine();
		}
		return list;
	}

	@Override
	public void exportDataToTxtFile(List<Vol> list) {
		try (FileOutputStream fout = new FileOutputStream("src/main/resources/exportdata.txt")) {

			for (Vol vol : list) {
				fout.write(vol.toString().getBytes());
				fout.write('\n');
			}
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}

	@Override
	public List<Vol> importDataFromXLFile() throws FileNotFoundException, IOException, Exception {

		XSSFRow row;
		List<Vol> list = new ArrayList<Vol>();
		int ind = 0;
		String[] vols = new String[20];
		try (FileInputStream fis = new FileInputStream(new File("src/main/resources/employeeInfo.xlsx"))) {
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet spreadsheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = spreadsheet.iterator();

			while (rowIterator.hasNext()) {
				row = (XSSFRow) rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					vols[ind] = cell.toString();
					ind++;
				}
				Double id = Double.parseDouble(vols[0]);
				Double escale = Double.parseDouble(vols[5]);
				//System.out.println( );
				Vol v = new Vol(id.intValue(), new java.util.Date(),
						new java.util.Date(), vols[3], vols[4], escale.intValue());
				//  System.out.println(v);
				list.add(v);
				ind = 0;
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public void exportDataToXLFile() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Integer a =0;

		//Création d'un objet de type feuille Excel
		XSSFSheet spreadsheet = workbook.createSheet(" Eleve Info ");

		//Création d'un objet row (ligne)
		XSSFRow row;

		//Les données à inserer;
		Map<Integer, Object[]> empinfo = new TreeMap<Integer, Object[]>();
		List<Vol> vols = new ArrayList<Vol>();
		vols = findAll();
		empinfo.put(a, new Object[]{"ID", "dateD", "dateA","aeroportA","AeroportD","escales"});
		//vols.forEach(v->{
		Vol v = new Vol();
		for(int i = 0 ;i<vols.size();i++){
            v=vols.get(i);
			empinfo.put(i, new Object[]{v.getId().toString(), v.getDateD().toString(),
					v.getDateA().toString(),v.getAeroportA(),
			        v.getAeroportD(),v.getNbrEscales()});
			//System.out.println(v);

		}
		//parcourir les données pour les écrire dans le fichier Excel
		Set<Integer> keyid = empinfo.keySet();
		int rowid = 0;

		for (Integer key: keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue(String.valueOf(obj));
			}

		}
		//Excrire les données dans un FileOutputStream
		FileOutputStream out = new FileOutputStream(new File("src/main/resources/VolExport.xlsx"));
		workbook.write(out);
		out.close();
		System.out.println("Travail bien fait!!!");
	}


	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM vol WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("problème de suppression d'un vol");;
		} finally {
			DB.closeStatement(ps);
		}

	}
	
	@Override
	public Vol findByID(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM vol WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				Vol vol = new Vol();
				vol.setId(rs.getInt("Id"));
				vol.setAeroportA(rs.getString("aeroportA"));
				vol.setAeroportD(rs.getString("aeroportD"));
				vol.setDateA(rs.getDate("dateA"));
				vol.setDateD(rs.getDate("dateD"));
				vol.setNbrEscales(rs.getInt("nbrEscales"));
				return vol;
			}
			
			return null;
		} catch (SQLException e) {
			System.err.println("problème de requête pour trouver un vol");;
		return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}

	}

	@Override
	public List<Vol> updateDataFromTxtFile() throws Exception {
		     List<Vol> vols = importDataFromTxtFile();
             vols.forEach( vol -> {
				 if(findByID(vol.getId())!=null)update(vol);
				 else insert(vol);
			 });
		return vols;
	}

	@Override
	public List<Vol> updateDataFromXLFile() throws Exception {
		List<Vol> vols = importDataFromXLFile();
		vols.forEach( vol -> {
			if(findByID(vol.getId())!=null)update(vol);
			else insert(vol);
		});
		return null;
	}

}
