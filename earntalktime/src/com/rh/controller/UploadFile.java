package com.rh.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.rh.dao.DBservice;

@Path("/file")
public class UploadFile {

	@POST
	@Path("/upload")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public Response uploadFile(FormDataMultiPart multiPart/* @FormDataParam("upfile") InputStream fileInputStream, @FormDataParam("upfile") FormDataContentDisposition contentDispositionHeader */) {

		String file = "";
		String filename = "";
		try {
			Properties props = new Properties();
			props.load(DBservice.class.getClassLoader().getResourceAsStream("config.properties"));
			file = props.getProperty("IMAGE_UPLOAD_PATH");
			
			List<FormDataBodyPart> fields = multiPart.getFields("upfile");
			
			for (FormDataBodyPart field : fields) {

				FormDataContentDisposition contentDispositionHeader = field.getFormDataContentDisposition();

				InputStream fileInputStream = field.getValueAs(InputStream.class);

				filename = filename + "<p><a href='http://54.209.220.78:8888/images/"+contentDispositionHeader.getFileName()+"' target='_blank'>" + contentDispositionHeader.getFileName() + "</a> uploaded successfully !!</p>";
				System.out.println("FileName: " + filename);

				int read = 0;
				byte[] bytes = new byte[1024];

				OutputStream out = new FileOutputStream(new File(file + contentDispositionHeader.getFileName()));
				while ((read = fileInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.flush();
				out.close();

			}
		} catch (IOException e) {
			throw new WebApplicationException("Error while uploading file. Please try again !!");
		}
		return Response.ok(filename).build();
	}

	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFiles(String data) {

		System.out.println("Data : " + data);
		String file = "";
		Map<String, String> map = new HashMap<String, String>();

		try {
			Properties props = new Properties();
			props.load(DBservice.class.getClassLoader().getResourceAsStream("config.properties"));
			file = props.getProperty("IMAGE_UPLOAD_PATH");

			File folder = new File(file);
			File[] listOfFiles = folder.listFiles();
			DecimalFormat f = new DecimalFormat("##.00");

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String filename = listOfFiles[i].getName();
					String filesize = f.format((float) listOfFiles[i].length() / 1024) + " kb";
					System.out.println("File =" + filename + " " + filesize);
					map.put(filename, filesize);
				} else if (listOfFiles[i].isDirectory()) {
					String filename = listOfFiles[i].getName();
					String filesize = f.format((float) listOfFiles[i].length() / 1024) + " kb";
					System.out.println("File =" + filename + " " + filesize);
					map.put(filename, filesize);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.noContent().build();
		}

		return Response.ok(map).build();
	}
}
