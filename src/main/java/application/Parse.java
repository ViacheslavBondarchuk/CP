package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.Cipher;
import application.WorkDB;

public class Parse {
	private String login, password, filePath, fileName;
	private Cipher cipher;
	private WorkDB workDB;
	
	

	public Parse() throws SQLException {
		cipher = new Cipher();
		workDB = new WorkDB();
	}


	public synchronized void parseAndWriteToDB(String path) throws FileNotFoundException, NoSuchAlgorithmException, SQLException {
		File file = new File(path);
		String result, loginHash = "", passwordHash = "";
		Scanner scanner;
		for(File f : file.listFiles()) {
			if(f.isFile()) {
				if(f.getAbsolutePath().endsWith(".txt")) {
					scanner = new Scanner(f);
					filePath = f.getAbsolutePath();
					fileName = f.getName();
					while(scanner.hasNext()) {
						result = scanner.next();
						for(String s : result.split(":")){
							if(validEmail(s) || validEmailSecond(s)) {
								login = s;
								loginHash = cipher.cipherLogin(login);
							}else {
								password = s;
								passwordHash = cipher.cipherPassword(password);
							}
						}
						workDB.writeInDBfromTxt(login, loginHash, password, passwordHash, filePath, fileName, "null");
					}
				}	
			}else {
				parseAndWriteToDB(f.getAbsolutePath());
			}
		}
		System.gc();
		Thread.currentThread().stop();
	}
		
	
	public synchronized void parseAndWriteToFile(String pathGet, String colName, String colPassw, String colLiving) throws InvalidFormatException, IOException, NoSuchAlgorithmException, SQLException {
		String result,password = "", name = "",living = "",loginHash = "", passwordHash = "";
		File file = new File(pathGet);
		Workbook workbook;
		for(File f : file.listFiles()) {
			if(f.isFile()) {
				filePath = f.getAbsolutePath();
				fileName = f.getName();
				if(f.getAbsolutePath().endsWith(".xlsx") || f.getAbsolutePath().endsWith(".xls")) {
					workbook = new XSSFWorkbook(f);
					for(Row row : workbook.getSheetAt(0)) {
						for(Cell cell : row) {
							switch(cell.getCellType()) {
							case STRING: 
								result = cell.getStringCellValue();
								if(cell.getAddress().toString().startsWith(colPassw)) {
									password = cell.getStringCellValue();
									passwordHash = cipher.cipherPassword(password);
								}
								if(cell.getAddress().toString().startsWith(colName)) {
									name = cell.getStringCellValue();
								}
								if(cell.getAddress().toString().startsWith(colLiving)) {
									living = cell.getStringCellValue();
								}
								if(validEmail(result) || validEmailSecond(result)) {
									login = result;
									loginHash = cipher.cipherLogin(login);
								}
								break;
							}
							
						}
						workDB.writeInDBfromExcel(name, living, login, loginHash, password, passwordHash, filePath, fileName, "null");
					}
				}	
			}else {
				parseAndWriteToFile(f.getAbsolutePath(),colName,colPassw,colLiving);
			}
		}
		System.gc();
		Thread.currentThread().stop();
	}
	
	
	private static boolean validEmail(String email) {
		String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern emailPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPattern.matcher(email);
		return matcher.find();
		
	} 
	
	private static boolean validEmailSecond(String email) {
		String regex = "^[[']A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}[']$";
		Pattern emailPattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = emailPattern.matcher(email);
		return matcher.find();
		
	} 
	
}
