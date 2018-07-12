package io.eoshos.pc.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtil {
	
	private static final Logger logger = LogManager.getLogger(QrCodeUtil.class);
    private static final int BLACK = Color.black.getRGB();
    private static final int WHITE = Color.WHITE.getRGB();
    private static final int DEFAULT_QR_SIZE = 183;
    private static final String DEFAULT_QR_FORMAT = "png";
    private static final byte[] EMPTY_BYTES = new byte[0];
    
    public static byte[] createQrCode(String content, int size, String extension) {
        return createQrCode(content, size, extension, null);
    }

    /**
     * 生成带图片的二维码
     * @param content  二维码中要包含的信息
     * @param size  大小
     * @param extension  文件格式扩展
     * @param insertImg  中间的logo图片
     * @return
     */
    public static byte[] createQrCode(String content, int size, String extension, Image insertImg) {
        if (size <= 0) {
            throw new IllegalArgumentException("size (" + size + ")  cannot be <= 0");
        }
        ByteArrayOutputStream baos = null;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

            //使用信息生成指定大小的点阵
            BitMatrix m = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
            
            //去掉白边
            m = updateBit(m, 0);
            
            int width = m.getWidth();
            int height = m.getHeight();
            
            //将BitMatrix中的信息设置到BufferdImage中，形成黑白图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    image.setRGB(i, j, m.get(i, j) ? BLACK : WHITE);
                }
            }
            if (insertImg != null) {
                // 插入中间的logo图片
                insertImage(image, insertImg, m.getWidth());
            }
            //将因为去白边而变小的图片再放大
            image = zoomInImage(image, size, size);
            baos = new ByteArrayOutputStream();
            ImageIO.write(image, extension, baos);
            
            return baos.toByteArray();
        } catch (Exception e) {
        } finally {
            if(baos != null)
                try {
                    baos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return EMPTY_BYTES;
    }
    
    /**
     * 自定义二维码白边宽度
     * @param matrix
     * @param margin
     * @return
     */
    private static BitMatrix updateBit(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
    
    // 图片放大缩小
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }
    
    private static void insertImage(BufferedImage source, Image insertImg, int size) {
        try {
            int width = insertImg.getWidth(null);
            int height = insertImg.getHeight(null);
            width = width > size / 4 ? size / 4 : width;  // logo设为二维码的四分之一大小
            height = height > size / 4 ? size / 4 : height;
            Graphics2D graph = source.createGraphics();
            int x = (size - width) / 2;
            int y = (size - height) / 2;
            graph.drawImage(insertImg, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] createQrCode(String content) {
        return createQrCode(content, DEFAULT_QR_SIZE, DEFAULT_QR_FORMAT);
    }

    public static int qrcode(String phone, String inviteCode){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String imgPath = request.getSession().getServletContext().getRealPath("qrcode") + File.separator;
        String imgFile = imgPath + "hos_icon.png"; 
        BufferedImage image = null;
        try {
			image = ImageIO.read(new FileInputStream(imgFile));
		} catch (FileNotFoundException e) {
			logger.error("打开HOS的ICON错误:" + e.getMessage());
		} catch (IOException e) {
			logger.error("读取HOS的ICON错误:" + e.getMessage());
		}
        int size = 150;//固定大小,150
        
        try {
        	// 生成pc端二维码
            FileOutputStream fos1 = new FileOutputStream(imgPath + phone + ".png");
            String content1 = "https://eoshos.io/enroll/index.html?inviteCode=" + inviteCode;
            fos1.write(QrCodeUtil.createQrCode(content1, size, "png", image));
            fos1.close();
            // 生成H5端二维码
            FileOutputStream fos2 = new FileOutputStream(imgPath + phone + "_wap.png");
            String content2 = "https://eoshos.io/enroll/index.html?inviteCode=" + inviteCode + "&devtype=wap";
            fos2.write(QrCodeUtil.createQrCode(content2, size, "png", image));
            fos2.close();
            return 1;
        } catch (Exception e) {
        	logger.error("生成二维码图片失败:" + e.getMessage());
        	return 0;
        }		
	}	
    
    public static void main(String[] args){
        try {
            FileOutputStream fos = new FileOutputStream("d:\\ab.png");
            fos.write(createQrCode("test"));
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        /*String imgPath = "D:\\36.png"; 
        BufferedImage image = null;
        try {
			image = ImageIO.read(new FileInputStream(imgPath));
			System.out.println(image.getWidth());
			System.out.println(image.getHeight());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int size = (int) (image.getWidth() * 2);
        
        try {
            FileOutputStream fos = new FileOutputStream("d:\\ff.png");
            fos.write(createQrCode("13378909876qwertyuiopdfghjk5tgb8uikm,ol.2wsxerfvboiuytrfgthyjo65", 150, "png", image));
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
    }
    
}