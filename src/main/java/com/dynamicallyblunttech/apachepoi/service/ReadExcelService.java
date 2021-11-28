package com.dynamicallyblunttech.apachepoi.service;

import com.dynamicallyblunttech.apachepoi.model.ExcelRowEle;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ReadExcelService {

    public String readExcel(String path){
        long start = System.currentTimeMillis();
//        System.out.println("ReadExcel:"+ path);
//        OPCPackage opc = null;
        try {
//            File file = new File(path);
//            opc = OPCPackage.open(file);
            Workbook workbook = new XSSFWorkbook(new FileInputStream(path));
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            int i=0;
            double sumTotalVP = 0.0, sumTotalV = 0.0;
            for(Row row: sheet){
                if(i==0){
                    i++;
                    continue;
                }
//                System.out.println("Physical No of Cell:"+row.getPhysicalNumberOfCells());
//                System.out.println("Last Cell No:"+ row.getLastCellNum());

                if(row.getPhysicalNumberOfCells()>=6) {
                    ExcelRowEle obj = new ExcelRowEle();
                    for (Cell cell : row) {
//                        System.out.println(cell.getColumnIndex());
                        if (cell.getColumnIndex() == 0)
                            obj.setDate(cell.getDateCellValue());

                        switch (cell.getColumnIndex()) {
                            case 1:
//                                System.out.println("1:"+cell.getColumnIndex());
                                obj.setOpen(cell.getNumericCellValue());
                                break;
                            case 2:
//                                System.out.println("2:"+cell.getColumnIndex());
                                obj.setHigh(cell.getNumericCellValue());
                                break;
                            case 3:
//                                System.out.println("3:"+cell.getColumnIndex());
                                obj.setLow(cell.getNumericCellValue());
                                break;
                            case 4:
//                                System.out.println("4:"+cell.getColumnIndex());
                                obj.setClose(cell.getNumericCellValue());
                                break;
                            case 5:
//                                System.out.println("5:"+cell.getColumnIndex());
                                obj.setVolume(cell.getNumericCellValue());
                                break;
                        }
                    }

//                    System.out.println("6:");
                    obj.setTypicalPrice(
                            obj.getDate() != null
                                    ? (obj.getHigh() + obj.getLow() + obj.getClose()) / 3
                                    : 0.0
                    );
                    row.createCell(7).setCellValue(obj.getTypicalPrice());

//                    System.out.println("7:");
                    obj.setVp(
                            obj.getDate() != null
                                    ? obj.getVolume() * obj.getTypicalPrice()
                                    : 0.0
                    );
                    row.createCell(8).setCellValue(obj.getVp());

//                    System.out.println("8:");
                    sumTotalVP += obj.getVp();
                    obj.setTotalVP(
                            obj.getDate() != null
                                    ? sumTotalVP
                                    : 0.0
                    );
                    row.createCell(9).setCellValue(obj.getTotalVP());

//                    System.out.println("9:");
                    sumTotalV += obj.getVolume();
                    obj.setTotalV(
                            obj.getDate() != null
                                    ? sumTotalV
                                    : 0.0
                    );
                    row.createCell(10).setCellValue(obj.getTotalV());

//                    System.out.println("10:");
                    obj.setVwap(
                            obj.getDate() != null
                                    ? obj.getTotalVP() / obj.getTotalV()
                                    : 0.0
                    );
                    row.createCell(11).setCellValue(obj.getVwap());
//                    System.out.println(obj);
                    obj = null;
//                    System.out.println();
                }
            }

            OutputStream os = new FileOutputStream(path);
            workbook.write(os);
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
        } finally {
//            if(null!=opc) {
//                try {
//                    opc.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        System.out.println("Time taken:"+ (System.currentTimeMillis() - start));
        return "Success";
    }
}
