package com.rbi.security.web.service.util;

import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.tool.ExcelPOI;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: ImportSpecialTrainingsMethed
 * @USER: "吴松达"
 * @DATE: 2020/6/15
 * @TIME: 16:01
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 15
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 01
 * @PROJECT_NAME: security
 **/

public class ImportSpecialTrainingsMethed {
    private static final Logger logger = LoggerFactory.getLogger(ImportSpecialTrainingsMethed.class);
    private static final String columns[] = {"no", "name", "gender", "idCardNo", "degreeOfEducation", "typeOfWork", "operationItems", "yearsOfWork", "workingYears", "theoreticalAchievements", "actualResults",
            "operationCertificateNo", "dateOfIssue", "oneReviewResults", "oneReviewTime", "towReviewResults", "towReviewTime",
            "threeReviewResults", "threeReviewTime", "fourReviewResults", "fourReviewTime", "fiveReviewResults", "fiveReviewTime", "sixReviewResults", "sixReviewTime",
            "remarks"};

    public List<Map<String, String>> checkExcel(MultipartFile file) throws RuntimeException, IOException {
        Workbook wb = null;
        Sheet sheet = null;
        String cellData = null;
        HSSFRow row=null;
        List<Map<String, String>> list=null;
        try {
            wb = new HSSFWorkbook(file.getInputStream());
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
            list = new LinkedList<Map<String, String>>();
            sheet=wb.getSheetAt(0);
            if (wb != null) {
                //获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                //获取第一行
                row = (HSSFRow) sheet.getRow(0);
                //获取最大列数
                int colnum = row.getPhysicalNumberOfCells();
                sheet = wb.getSheetAt(0);
                for (int j = 5; j < rownum; j++) {// getLastRowNum，获取最后一行的行标
                    row = (HSSFRow) sheet.getRow(j);
                    Map<String,String> map = new LinkedHashMap<String,String>();
                    if (row != null) {
                        for (int k = 0; k < colnum; k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
                            Cell cell = row.getCell(k);
                            cellData = (String) ExcelPOI.getCellFormatValue(cell);
                            map.put(columns[k], cellData);
                        }
                    }else{
                        break;
                    }
                    list.add(map);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            wb.close();
        }
        return list;
    }

    public List<SafeSpecialTrainingFiles> excelImport(MultipartFile file) throws RuntimeException {
        List<SafeSpecialTrainingFiles> safeSpecialTrainingFilesList =null;
        try {
            safeSpecialTrainingFilesList = new LinkedList<>();
            List<Map<String, String>> lists = checkExcel(file);
            for (int i = 0; i < lists.size(); i++) {
                SafeSpecialTrainingFiles safeSpecialTrainingFiles = new SafeSpecialTrainingFiles();
                for (String key : lists.get(i).keySet()) {
                    String value = lists.get(i).get(key);
                    safeSpecialTrainingFiles = (SafeSpecialTrainingFiles) dynamicSet(safeSpecialTrainingFiles, key, value);
                }
                safeSpecialTrainingFilesList.add(safeSpecialTrainingFiles);
            }
            System.out.println("获取到文件内容");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return safeSpecialTrainingFilesList;
    }

    /* * @description:根据属性名调用set方法
     * @param obj 对象
     * @param propertyName 属性名
     * @param qtySum 要插入的值
     */
    private <T> Object dynamicSet(T obj, String propertyName, Object qtySum) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(obj, qtySum);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return obj;
    }
}
