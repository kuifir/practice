package com.kuifir.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DefaultObjectMapper implements ObjectMapper {
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

    String decimalFormat = "#,##0.00";
    DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);

    public DefaultObjectMapper() {
    }

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.datetimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public void setDecimalFormat(String decimalFormat) {
        this.decimalFormat = decimalFormat;
        this.decimalFormatter = new DecimalFormat(decimalFormat);
    }

    @Override
    public String writeValuesAsString(Object obj) {
        if (obj instanceof Date date) return date.toString();
        String sJsonStr = "{";

        Class<?> clz = obj.getClass();

        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String sField = "";
            Object value = null;
            Class<?> type = null;
            String name = field.getName();
            String strValue = "";
            try {
                field.setAccessible(true);
                value = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            type = field.getType();

            if (value instanceof Date) {
                LocalDate localDate;
                if(value instanceof java.sql.Date sqlDate){
                    localDate = sqlDate.toLocalDate();
                }else {
                    localDate = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                }
                strValue = localDate.format(this.datetimeFormatter);
            } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                strValue = this.decimalFormatter.format(value);
            } else {
                strValue = null == value ? "" : value.toString();
            }

            if (sJsonStr.equals("{")) {
                sField = "\"" + name + "\":\"" + strValue + "\"";
            } else {
                sField = ",\"" + name + "\":\"" + strValue + "\"";
            }

            sJsonStr += sField;
        }

        sJsonStr += "}";

        return sJsonStr;
    }

}
