package com.rh.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CircularQueue<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	private static int Hours;
	private static int Mins;
	private static Connection con = null;

	public static void main(String args[]) {

		int index = 0;

		List<String> list = new CircularQueue<String>();
		list.add("DownloadBooster");
		list.add("InviteBooster");
		list.add("SuperHero");
		list.add("3rdInvite");

		String promo1 = list.get(index);
		String promo2 = list.get(index - 1);
		

		int rand = 1 + (int)(Math.random() * ((4 - 1) + 1));
		
		System.out.println("rand: " +rand);

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://earntalktime.cecz6xcsdkcj.us-east-1.rds.amazonaws.com:3306/ett?autoReconnect=true&useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&allowMultiQueries=true", "ettsuser", "EttP$55w04d");
			
			while (true) {

				getTime();
				String query = "";
				String query1 = "";
				String query2 = "";
				
				if (Hours > 18 && Hours < 19) {
					if (Mins >30 && Mins < 31) {
						index++;
						promo1 = list.get(index);
						promo2 = list.get(index - 1);
						
						query = "update Challenges set status=0 where id in (7,6,2,3)";
						UpdateData(query);
						
						switch (promo1) {
						case "3rdInvite":
							System.out.println("Promo1 : " + promo1);
							query2 = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/Invite_Icon_1."+index+".png' where id=7";
							UpdateData(query2);
							
							Thread.sleep(10000);
							break;
						case "SuperHero":
							System.out.println("Promo1 : " + promo1);
							query2 = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/Leap_year_Banners/sh_"+index+".png' where id=6";
							UpdateData(query2);
							
							Thread.sleep(10000);
							break;
						case "InviteBooster":
							System.out.println("Promo1 : " + promo1);
							query2 = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/00"+index+"_icon.png' where id=2";
							UpdateData(query2);
							
							Thread.sleep(10000);
							break;
						case "DownloadBooster":
							System.out.println("Promo1 : " + promo1);
							query2 = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/DL_icon_1."+index+".png' where id=3";
							UpdateData(query2);
							
							Thread.sleep(10000);
							break;
						}
						
						switch (promo2) {
						case "3rdInvite":
							System.out.println("Promo2 : " + promo2);
							query = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/Invite_Icon_1."+index+".png' where id=7";
							UpdateData(query);
							
							Thread.sleep(10000);
							break;
						case "SuperHero":
							System.out.println("Promo2 : " + promo2);
							query = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/Leap_year_Banners/sh_"+index+".png' where id=6";
							UpdateData(query);
							
							Thread.sleep(10000);
							break;
						case "InviteBooster":
							System.out.println("Promo2 : " + promo2);
							query = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/00"+index+"_icon.png' where id=2";
							UpdateData(query);
							
							Thread.sleep(10000);
							break;
						case "DownloadBooster":
							System.out.println("Promo2 : " + promo2);
							query = "update Challenges set status=1,imageUrl='http://54.209.220.78:8888/images/DL_icon_1."+index+".png' where id=3";
							UpdateData(query);
							
							Thread.sleep(10000);
							break;
						}
						
					}
				}

				
				switch (promo1) {
				case "3rdInvite":
					System.out.println("Promo1 : " + promo1);
					query = "update BreakingAlert set imageUrl='http://54.209.220.78:8888/images/Invite_1."+index+".png',onClickType='TAB', menuName='contests' where id='1'";
					query1 = "update DynamicTabDetails set tabActionUrl='http://54.209.220.78:8888/earntalktime/challenge.html?type=2&temp=' where id=19";
					UpdateData(query);
					UpdateData(query1);
					
					Thread.sleep(10000);
					break;
				case "SuperHero":
					System.out.println("Promo1 : " + promo1);
					query = "update BreakingAlert set imageUrl='http://54.209.220.78:8888/images/Leap_year_Banners/shb_"+index+".png',onClickType='TAB', menuName='contests'  where id=1";
					query1 = "update DynamicTabDetails set tabActionUrl='http://54.209.220.78:8888/earntalktime/challenge.html?ettId=#ETTID#&type=2temp=' where id=19"; 
					UpdateData(query);
					UpdateData(query1);
					
					Thread.sleep(10000);
					break;
				case "InviteBooster":
					System.out.println("Promo1 : " + promo1);
					query = "update BreakingAlert set imageUrl='http://54.209.220.78:8888/images/000"+index+".png',onClickType='TAB', menuName='contests' where id='1'"; 
					query1 = "update DynamicTabDetails set tabActionUrl='http://54.209.220.78:8888/earntalktime/challenge.html?type=2&temp=' where id=19"; 
					UpdateData(query);
					UpdateData(query1);
					
					Thread.sleep(10000);
					break;
				case "DownloadBooster":
					System.out.println("Promo1 : " + promo1);
					query = "update BreakingAlert set imageUrl='http://54.209.220.78:8888/images/DL_1."+index+".png',onClickType='TAB', menuName='contests' where id='1'";
					query1 = "update DynamicTabDetails set tabActionUrl='http://54.209.220.78:8888/earntalktime/challenge.html?type=2&temp=' where id=19";
					UpdateData(query);
					UpdateData(query1);
					
					Thread.sleep(10000);
					break;
				}
				
				query = "update BreakingAlert set imageUrl='http://54.209.220.78:8888/images/omg-banner.png',onClickType='TAB', menuName='contests' where id=1";
				query1 = "update DynamicTabDetails set tabActionUrl='http://54.209.220.78:8888/earntalktime/challenge.html?ettId=#ETTID#&type=3&temp=' where id=19;";
				UpdateData(query);
				UpdateData(query1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public E get(int index) {

		if (index < 0) {
			return super.get(index + size());
		}
		return super.get(index % size());
	}

	public static int UpdateData(String DMquery) {
		try {
			Statement st = con.createStatement();
			int ur = st.executeUpdate(DMquery);
			return ur;
		} catch (Exception e) {
			System.out.println("update error" + e.getMessage());
			return -1;
		}
	}

	private static void getTime() {

		Calendar calendar = Calendar.getInstance();
		Hours = calendar.getTime().getHours();
		Mins = calendar.getTime().getMinutes();
		System.out.println("Time " + Hours + ":" + Mins);
	}
}
