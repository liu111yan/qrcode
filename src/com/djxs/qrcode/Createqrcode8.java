package com.djxs.qrcode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;
public class Createqrcode8 {
	/**
	 *生成qrcode
	 *@param  ver                    版本
	 *@param  qrcodeErrorCorrect     设置二维码排错率L,M,Q,H 排错率越高可存储的信息越少，但对二维码的清晰度要求越低
	 *@param  str                    二维码内容
	 *@param  logoPath               logo路径
	 *@param  logoSize               logo大小
	 *@param  qrCodeImagePath        输出二维码的路径
	 *@param  startColor             RGB格式开始颜色
	 *@param  endColor               RGB格式结束颜色
	 *@throws IOException 
	 */
    public static void Cqrcode(int ver,char qrcodeErrorCorrect,String str,String logoPath,int logoSize,String qrCodeImagePath,String startColor,String endColor) throws IOException{
    	Qrcode qrcdoe = new Qrcode();
		qrcdoe.setQrcodeVersion(ver);
		qrcdoe.setQrcodeErrorCorrect(qrcodeErrorCorrect);
		int  imageSize = 67+12*(ver-1);
		//图片缓存对象
		BufferedImage bufferedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		//创建画板
		Graphics2D gs = bufferedImage.createGraphics();
		//设置背景色
		gs.setBackground(Color.WHITE);
		//设置前景色
		gs.setColor(Color.BLACK);
		//情况画板
		gs.clearRect(0, 0, imageSize, imageSize);
		  
		int startR = Integer.parseInt(startColor.split(",")[0]);
		int startG = Integer.parseInt(startColor.split(",")[1]);
		int startB = Integer.parseInt(startColor.split(",")[2]);
		
		int endR = Integer.parseInt(endColor.split(",")[0]);
		int endG = Integer.parseInt(endColor.split(",")[0]);
		int endB = Integer.parseInt(endColor.split(",")[0]);
		
		//得到二位数组  可以确定二维码那个点有颜色
		boolean[][] calQrcode = qrcdoe.calQrcode(str.getBytes());
		//System.out.println(calQrcode.length);
		int x = 2;
		for (int i = 0; i < calQrcode.length; i++) {
			for (int j = 0; j < calQrcode.length; j++) {
				if(calQrcode[i][j]){
					int num1 = startR + (endR - startR) * ((i+j)/2)/calQrcode.length;
					int num2 = startG + (endG - startG) * ((i+j)/2)/calQrcode.length;
					int num3 = startB + (endB - startB) * ((i+j)/2)/calQrcode.length;
					
					Color color = new Color(num1, num2, num3);
					
					gs.setColor(color);
					//填充颜色
					gs.fillRect(i*3+x, j*3+x, 3, 3);
				}
			}
		}
		Image logo=ImageIO.read(new File(logoPath));
		int x1=(imageSize-logoSize)/2;
		gs.drawImage(logo, x1, x1, logoSize, logoSize,  null);
		gs.dispose();
		bufferedImage.flush();
		//输出二维码图片
		ImageIO.write(bufferedImage, "png", new File(qrCodeImagePath));
		System.out.println("成功");
    	
    } 
}
