package com.dynamicallyblunttech.apachepoi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelRowEle {

    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double typicalPrice;
    private double vp;
    private double totalVP;
    private double totalV;
    private double vwap;
}
