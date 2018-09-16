package com.wt.bl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author WangTao
 *         Created at 18/9/11 上午11:48.
 */
public class ImgToPdf {
    /**
     *
     * @param // imageFolderPath
     *            图片文件夹地址
     * @param // pdfPath
     *            PDF文件保存地址
     *
     */
    /*public static void toPdf(String imageFolderPath, String pdfPath) {
        try {
            // 图片文件夹地址
            // String imageFolderPath = "D:/Demo/ceshi/";
            // 图片地址
            String imagePath = null;
            // PDF文件保存地址
            // String pdfPath = "D:/Demo/ceshi/hebing.pdf";
            // 输入流
//            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            //doc.open();
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img = null;
            // 实例化图片
            Image image = null;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();
            // 循环获取图片文件夹内的图片
            for (File file1 : files) {
                if (file1.getName().endsWith(".png")
                        || file1.getName().endsWith(".jpg")
                        || file1.getName().endsWith(".gif")
                        || file1.getName().endsWith(".jpeg")
                        || file1.getName().endsWith(".tif")) {
                    // System.out.println(file1.getName());
                    imagePath = imageFolderPath + file1.getName();
                    System.out.println(file1.getName());
                    // 读取图片流
                    img = ImageIO.read(new File(imagePath));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img
                            .getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.open();
                    doc.add(image);
                }
            }
            // 关闭文档
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        toPdf("D:/Demo/ceshi/", "D:/Demo/pdf/hebing.pdf");
        long time2 = System.currentTimeMillis();
        int time = (int) ((time2 - time1)/1000);
        System.out.println("执行了："+time+"秒！");
    }*/
    public static void mutipartFileToPdf(MultipartFile[] imgs) {
        if (imgs == null || imgs.length == 0) {
            return;
        }
        Document document = new Document();
        document.open();
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
            Map<Boolean, List<MultipartFile>> collect = Arrays.stream(imgs).collect(groupingBy(img -> img.getOriginalFilename().endsWith(".png")
                    || img.getOriginalFilename().endsWith(".jpg")
                    || img.getOriginalFilename().endsWith(".gif")
                    || img.getOriginalFilename().endsWith(".jpeg")
                    || img.getOriginalFilename().endsWith(".tif")));
            List<MultipartFile> multipartFiles = collect.get(true);
            multipartFiles.stream().forEach(multipartFile -> {
                try {
                    // 读取图片流
                    BufferedImage image = ImageIO.read(multipartFile.getInputStream());
                    // 根据图片大小设置文档大小
                    document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
                    // 实例化图片
                    Image img = Image.getInstance(multipartFile.getOriginalFilename());
                    document.add(img);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException de) {

                }
            });
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        /*File file = new File(imgs[0].getOriginalFilename());
        try {
            imgs[0].transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
