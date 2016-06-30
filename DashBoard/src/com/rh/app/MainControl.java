package com.rh.app;

import com.rh.utility.GetStatics;

public class MainControl{

	public static void main(String[] args){
		
		try {
			GetStatics gs = new GetStatics();
			Thread t3 = new Thread(gs);
			t3.start();
			
			Thread.sleep(1000);
			
			DashBMin db = new DashBMin();
			Thread t1 = new Thread(db);
			t1.start();
			
			Thread.sleep(1000);
			
			DashBminMin dbm = new DashBminMin();
			Thread t2 = new Thread(dbm);
			t2.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
