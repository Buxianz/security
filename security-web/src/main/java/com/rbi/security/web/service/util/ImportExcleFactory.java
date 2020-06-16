package com.rbi.security.web.service.util;

import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.tool.ExcelPOI;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.wsd.importExcel
 * @NAME: ImportExcleFactory
 * @USER: "吴松达"
 * @DATE: 2020/6/16
 * @TIME: 15:57
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 16
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 15
 * @MINUTE: 57
 * @PROJECT_NAME: TestItems
 **/
public class ImportExcleFactory {
    private static final Logger logger = LoggerFactory.getLogger(ImportExcleFactory.class);

    /*public static void main(String[] args) {
        File file = new File("C:\\Users\\wsd\\Desktop\\2019电解铝厂特种作业台账(1).xls");
        FileInputStream fis = null;
        Workbook workBook = null;
        List<SafeSpecialTrainingFiles> safeSpecialTrainingFilesList=null;
        if (file.exists()) {
            try {
                safeSpecialTrainingFilesList=new LinkedList<SafeSpecialTrainingFiles>();
                fis = new FileInputStream(file);
                safeSpecialTrainingFilesList= getDate(fis,safeSpecialTrainingFilesList,SafeSpecialTrainingFiles.class,columns,5,0);
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
    }*/
    /**
     *inputStream文件输入流
     * objs 需要的List结果集
     * objClass 结果对象对应的class
     * columns 文件对应的列对应的参数名称
     * r 从第几行开始
     * c 从第几列开始
     * @param objs
     * @param columns excel表对应的列
     * @param <T>
     * @return
     */
    public static  <T> List<T> getDate(InputStream inputStream, List<T> objs, Class<T> objClass,String columns[],int r,int c){
            try{
                T obj = null;
                List<Map<String, String>> listMap=getMapDate(inputStream,columns,r,c);
                for (int i = 0; i < listMap.size(); i++) {
                    obj= objClass.newInstance();
                    for (String key : listMap.get(i).keySet()) {
                        String value = listMap.get(i).get(key);
                        obj = (T) dynamicSet(obj, key, value);
                    }
                    objs.add(obj);
                }
            }catch (Exception e){
                logger.debug(e.getMessage());
            }

           return objs;
    }

    /**
     *inputStream文件输入流
     * columns 文件对应的列对应的参数名称
     * r 从第几行开始
     * c 从第几列开始
     * @return
     */
    public static  List<Map<String, String>> getMapDate(InputStream inputStream,String columns[],int r,int c)throws RuntimeException, IOException {
        Workbook wb = null;
        Sheet sheet = null;
        String cellData = null;
        HSSFRow row=null;
        List<Map<String, String>> list=null;
        try {
            wb = new HSSFWorkbook(inputStream);
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
                for (int j = r; j < rownum; j++) {// getLastRowNum，获取最后一行的行标
                    row = (HSSFRow) sheet.getRow(j);
                    Map<String,String> map = new LinkedHashMap<String,String>();
                    if (row != null) {
                        for (int k = c; k < colnum; k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
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
    /* * @description:根据属性名调用set方法
     * @param obj 对象
     * @param propertyName 属性名
     * @param qtySum 要插入的值
     */
    private static  <T> Object dynamicSet(T obj, String propertyName, Object qtySum) {
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
