package com.wt.bl.export;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * @author WangTao
 * Created at 18/11/26 下午5:55.
 */
public class PieChart {

    private static final Paint[] COLORS = {
            Color.GREEN,
            new Color(16, 138, 198, 1),
            Color.YELLOW,
            new Color(69, 217, 232, 1),
            new Color(255, 113, 70, 1),
            Color.RED,
            Color.GRAY, Color.MAGENTA, Color.ORANGE,
            Color.DARK_GRAY, Color.CYAN, Color.PINK, Color.BLUE,
            Color.LIGHT_GRAY };

    public void setColor(PiePlot plot, CategoryDataset dataset) {
        List keys = dataset.getRowKeys();
        for (int i = 0; i < keys.size(); i++) {
            plot.setSectionPaint(keys.get(i).toString(), COLORS[i
                    % COLORS.length]);
        }
    }

    ChartPanel frame1;
    public PieChart(){
        DefaultPieDataset data = getDataSet();
        JFreeChart chart = ChartFactory.createPieChart3D("水果产量",data,true,false,false);
        //设置百分比
        PiePlot pieplot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
        pieplot.setLabelGenerator(sp1);//设置饼图显示百分比

        //没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);//设置不显示空值
        pieplot.setIgnoreZeroValues(true);//设置不显示负值
        frame1 = new ChartPanel (chart,true);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象
        piePlot.setLabelFont(new Font("宋体",Font.BOLD,10));//解决乱码
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));
    }
    public static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("基本同意",75);
        dataset.setValue("非常同意",25);
        dataset.setValue("同意",0);
        dataset.setValue("不同意",0);
        dataset.setValue("非常不同意",0);
        return dataset;
    }
    public ChartPanel getChartPanel(){
        return frame1;
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("Java数据统计图");
        frame.setLayout(new GridLayout(2,2,10,10));
        // frame.add(new BarChart().getChartPanel());           //添加柱形图
        // frame.add(new BarChart1().getChartPanel());          //添加柱形图的另一种效果
        frame.add(new PieChart().getChartPanel());           //添加饼状图
        // frame.add(new TimeSeriesChart().getChartPanel());    //添加折线图
        frame.setBounds(50, 50, 800, 600);
        frame.setVisible(true);
        frame.getIconImage();
    }

}
