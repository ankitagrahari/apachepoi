package com.dynamicallyblunttech.apachepoi.controller;

import com.dynamicallyblunttech.apachepoi.service.ReadExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    ReadExcelService excelService;

    @GetMapping("/read")
    public String readExcel(){
        System.out.println("Incoming Request");
        return excelService.readExcel("src/main/resources/poi/Calculations.xlsx");
    }

    @GetMapping("/readDummy")
    public String readDummyExcel(){
        System.out.println("Incoming Dummy Request");
        return excelService.readExcel("src/main/resources/poi/dummy.xlsx");
    }
}
