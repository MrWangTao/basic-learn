package com.wt.bl.export;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WangTao
 * Created at 18/11/26 下午6:04.
 */
public class ExportPng{


    public ExportPng(){

//        super.setTitle(k);
//        this.setSize(600, 300);
//        this.setLocation(200, 300);
        // this.setVisible(true);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        /*ColorEnums[] values = ColorEnums.values();
        for (int i = 0; i < 3; i++) {
            ColorEnums value = values[i];
            String name = value.getClass().getName();
            System.out.println(name);
            System.out.println(values[i]);
        }*/

        ExportPng.drow();
        // test.setVisible(false);
    }

    // 可以定义为static方法
    public static void drow(){

        //循环里面加入数据
        /*for(int i=0;i<n;i++){
            //第一个参数是拐点变化范围，第二个参数是这条折线代表什么，
            //第三个参数是横轴的刻度
            // dataset.addValue(1.0+Math.random(), series1, Integer.toString(i));
            dataset.addValue(3.0+Math.random(), series2, Integer.toString(i));
            dataset.addValue(3.5, series3, Integer.toString(i));
            //dataset.addValue(4.0, series4, Integer.toString(i));

        }*/
        JFreeChart chart = ChartFactory.createPieChart(
                "测试",
                PieChart.getDataSet(), // 饼状图数据来源
                true, true, false
        );
        // 设置图例位置
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        legend.setBackgroundPaint(Color.white);
        legend.setBorder( 0, 0, 0, 0);
        legend.setMargin(0, 0, 50, 20);
        // 设置外层图片 无边框 无背景色 背景图片透明
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(null);
        chart.setBackgroundImageAlpha(0.0f);
        PiePlot plot = (PiePlot) chart.getPlot();
        // 设置饼图: 可通过循环数据的方式进行循环生成
        List<String> strings = Arrays.asList("基本同意", "非常同意", "同意", "不同意", "非常不同意");
        for (int i = 0; i < strings.size(); i++) {
            plot.setSectionPaint(i, list.get(i));
        }
        /*plot.setSectionPaint("基本同意", Color.GREEN);
        plot.setSectionPaint("非常同意", new Color(16, 138, 198));
        plot.setSectionPaint("同意", Color.YELLOW);
        plot.setSectionPaint("不同意", new Color(69, 217, 232));
        plot.setSectionPaint("非常不同意", new Color(255, 113, 70));*/
        // 设置标签样式
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}"));
        // 设置外边框
        plot.setSectionOutlinesVisible(false);
        // 设置背景色
        plot.setLabelBackgroundPaint(Color.white);
        // 饼图的透明度
        // plot.setForegroundAlpha(0.5f);
        // 饼图的背景全透明
        plot.setBackgroundAlpha(0.0f);
        // 去除背景边框线
        plot.setOutlinePaint(null);
        try {
            ChartUtilities.saveChartAsPNG(new File("333.png"), chart, 550, 250);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 删除临时文件
            // new File("1111.png").delete();
        }
    }

    /*public static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("基本同意",75);
        dataset.setValue("非常同意",25);
        dataset.setValue("同意",0);
        dataset.setValue("不同意",0);
        dataset.setValue("非常不同意",0);
        return dataset;
    }*/


    public static java.util.List<Color> list = Arrays.asList(Color.GREEN,new Color(16, 138, 198),Color.YELLOW,new Color(69, 217, 232),new Color(255, 113, 70));
    public static Color[] colors = new Color[] {
            Color.GREEN,
        new Color(16, 138, 198)
    };

}
