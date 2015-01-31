package main;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class Screenshot
{	
	private final String ssDir="\\Desktop\\Screenshots\\";
	private CheckFolder cf=new CheckFolder();
	private String imgAddress = "";
	private Upload upload=new Upload();
	
	//Fullscreen SS
	public void fullscreenSS()
	{
		//check if folder exists if not it creates
		if(cf.check())
		{
			try
			{
				//take SS
				BufferedImage image=new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				//save to destination
				imgAddress=getFilePath();
				//save file
				ImageIO.write(image,"png",new File(imgAddress));
				
				
				upload.uploadToImgur(imgAddress); 
				upload.openURL(); //test upload n open link
				upload.UrlToClipboard(); //test upload n clipboard link
			} 
			catch (AWTException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//take a screenshot of current window 
	//Windows only
	public void windowSS()
	{
		if(cf.check())
		{
			try
			{
				Robot robo=new Robot();
				Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
				
				//Take Screenshots with window HK's
				robo.keyPress(KeyEvent.VK_ALT);
				robo.keyPress(KeyEvent.VK_PRINTSCREEN);
				robo.keyRelease(KeyEvent.VK_ALT);
				robo.keyRelease(KeyEvent.VK_PRINTSCREEN);
				
				try {
			            Thread.sleep(1000);
			        } 
				catch (InterruptedException e) 
				{
			            throw new RuntimeException(e);
			    }
				
				//Get SS from clipboard
				Transferable contents=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
				
				//transfer and save
				RenderedImage image=(RenderedImage)contents.getTransferData(DataFlavor.imageFlavor);
				imgAddress=getFilePath();
				ImageIO.write(image, "png", new File(imgAddress));
				
				
				upload.uploadToImgur(imgAddress); 
				upload.openURL(); //test upload n open link
				upload.UrlToClipboard(); //test upload n clipboard link
			}
			catch(AWTException | IOException | UnsupportedFlavorException e)
			{
				e.printStackTrace();
			}
		}
	}
	//generate unique name and formats directory
	public String generateImgName()
	{
		StringBuilder strBuild=new StringBuilder();
		String imgName=GenerateString.generateRandomString();
		
		//create dir string with filename
		strBuild.append(ssDir);
		strBuild.append(imgName);
		strBuild.append(".png");
		
		return strBuild.toString();
	}
	//Sets file name as timestamp 
	public String generateImgNameAsTimestamp()
	{
		DateFormat dateFormat= new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
		Date date=new Date();
		StringBuilder strBuild=new StringBuilder();
		
		//create dir string with filename
		strBuild.append(ssDir);
		strBuild.append(dateFormat.format(date));
		strBuild.append(".png");
		
		//System.out.println(strBuild);
		
		return strBuild.toString();	
	}
	//create filepath to save img
	public String getFilePath()
	{
		return System.getProperty("user.home")+generateImgNameAsTimestamp();
	}
}
