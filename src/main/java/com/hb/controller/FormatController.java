package com.hb.controller;


import java.math.RoundingMode;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hb.models.FormatModel;

@Controller
@RequestMapping(value = "/format")
public class FormatController {
    
    @RequestMapping(value="/test", method = {RequestMethod.GET})
    public String test(Model model) throws NoSuchFieldException, SecurityException{

        if(!model.containsAttribute("contentModel")){
            
            FormatModel formatModel=new FormatModel();

            CurrencyFormatter currencyFormatter = new CurrencyFormatter();  
            currencyFormatter.setFractionDigits(2);//保留2位小数
            currencyFormatter.setRoundingMode(RoundingMode.HALF_UP);//向（距离）最近的一边舍入，如果两边（的距离）是相等的则向上舍入（四舍五入）
            
            DateFormatter dateFormatter=new DateFormatter();
            dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
            
            Locale locale=LocaleContextHolder.getLocale();
            
            formatModel.setMoneyStr(currencyFormatter.print(12345.678, locale));
            formatModel.setDateStr(dateFormatter.print(new Date(), locale));  
            
            //基于注解的格式化
            formatModel.setMoney(12345.678);
            formatModel.setDate(new Date());
            
            model.addAttribute("contentModel", formatModel);
         
            
        }
        return "test/formattest";
    }
    
    
}
