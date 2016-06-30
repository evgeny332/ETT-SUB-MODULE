package com.rh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.rh.dao.DBservice;

/**
 * Servlet implementation class Winner
 */
//@WebServlet("/Winner")
public class Winner extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println(getServletInfo());
		response.setContentType("application/json");

		PrintWriter print = response.getWriter();

		String type = request.getParameter("type");
		String date = request.getParameter("date");

		System.out.println("type: " + type);
		System.out.println("Date: " + date);
		
		String tableName = "";
		if(type.equals("download")){	
			tableName = "DecWinners";
		}else if(type.equals("invite")){
			tableName = "DecInviteWinners";
		}else if (type.equals("t20")){
			tableName = "WT20Winners";
		}
		
		ResultSet rs = null;
		try {

			boolean valid = false;
			String query = "";
			Map map = new HashMap();
			List<String> msisdn = new ArrayList<String>();
			if(tableName.equals("WT20Winners")){
				query = "select * from "+tableName+" where matchId='Match"+date+"'";
			}else {
				query = "select * from "+tableName+" where date='" + date + "'";
			}
			
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				msisdn.add(rs.getString("msisdn"));
			}

			map.put("dto", msisdn);

			if (valid) {
				map.put("valid", "true");
			} else {
				map.put("valid", "false");
			}
			Gson gson = new Gson();
			String json = gson.toJson(map);
			print.println(json);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
}
