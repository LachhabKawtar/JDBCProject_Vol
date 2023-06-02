 package com.example.demo.dao;

 import com.example.demo.entities.Vol;

 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.util.List;

 public interface DaoVol {
  void insert(Vol vol);
  void update(Vol vol);
  Vol findByID(Integer id);
  List<Vol> updateDataFromTxtFile() throws Exception;
  List<Vol> updateDataFromXLFile() throws Exception;
  void deleteById(Integer id);
  List<Vol> findAll();
  List<Vol> importDataFromTxtFile() throws FileNotFoundException, IOException, Exception;
  void exportDataToTxtFile(List<Vol> list);
  List<Vol> importDataFromXLFile() throws FileNotFoundException, IOException, Exception;
  void exportDataToXLFile() throws IOException;
}
