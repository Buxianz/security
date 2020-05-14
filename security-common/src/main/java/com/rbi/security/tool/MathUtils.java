package com.rbi.security.tool;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by llj on 2016-08-29.
 */
public class MathUtils {

    /**
     * 求给定整形数组中值的最大值
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果,如果输入值不合法，返回为-1
     */
    public static int getMax(int... inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        int max = inputData[0];
        for (int i = 0; i < len; i++) {
            if (max < inputData[i])
                max = inputData[i];
        }
        return max;
    }
    
    /**
     * 求给定双精度数组中值的最大值
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果,如果输入值不合法，返回为-1
     */
    public static double getMax(double[] inputData) {
    	if (inputData == null || inputData.length == 0)
    		return -1;
    	int len = inputData.length;
    	double max = inputData[0];
    	for (int i = 0; i < len; i++) {
    		if (max < inputData[i])
    			max = inputData[i];
    	}
    	return max;
    }

    /**
     * 求求给定双精度数组中值的最小值
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果,如果输入值不合法，返回为-1
     */
    public static double getMin(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double min = inputData[0];
        for (int i = 0; i < len; i++) {
            if (min > inputData[i])
                min = inputData[i];
        }
        return min;
    }

    /**
     * 求给定双精度数组中值的和
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static Double getSum(List<Double> inputData) {
        if (inputData == null || inputData.size() == 0)
            return null;
        int len = inputData.size();
        double sum = 0;
        for (int i = 0; i < len; i++) {
            sum += inputData.get(i);
        }
        return sum;
    }

    /**
     * 求给定双精度数组中值的数目
     *
     * @param
     * @return 运算结果
     */
    public static int getCount(List<Double> inputData) {
        if (inputData == null){
            return 0;
        }
        return inputData.size();
    }

    /**
     * 求给定双精度数组中值的平均值(12个月)---  只用于pos贷的指标计算
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static Double getAverage(List<Double> inputData) {
        if (inputData == null || inputData.size() == 0)
        { return null;}
        Double result;
        result = getSum(inputData) / 12;
        return result;
    }

    public static Double getAverageAct(List<Double> inputData) {
        if (inputData == null || inputData.size() == 0) {
            return null;
        }
        int len = inputData.size();
        Double result;
        result = getSum(inputData) / len;
        return result;
    }

    /**
     * 求给定双精度数组中值的平方和
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static Double getSquareSum(List<Double>  inputData) {
        if(inputData==null||inputData.size()==0)
            return null;
        int len=inputData.size();
        double sqrsum = 0.0;
        for (int i = 0; i <len; i++) {
            sqrsum = sqrsum + inputData.get(i) * inputData.get(i);
        }
        return sqrsum;
    }

    /**
     * 求给定双精度数组中值的方差
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static Double getVariance(List<Double>  inputData) {
        double sqrsum = getSquareSum(inputData);
        double average = getAverage(inputData);
        Double result;
        result = (sqrsum - 12 * average * average) / 12.0;
        return result;
    }

    /**
     * 求给定双精度数组中值的标准差
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static Double getStandardDiviation(List<Double>  inputData) {
        Double result;
        //绝对值化很重要
        result = Math.sqrt(Math.abs(getVariance(inputData)));
        return result;
    }
    
    /**
     * 设置double小数点
     * @param double 
     * @return 运算结果
     */
    public static double setScale(double val, int digit) {
    	BigDecimal b = new BigDecimal(val);
    	return b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}