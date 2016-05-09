package org.tempuri;

import org.apache.axis.client.Service;

public class WebServicesClient {
	
	public void reload(String msisdn, int amount) {
		try {
			
			java.net.URL url = new java.net.URL("http://175.139.151.69:8099/IAT/EPin_Reload.asmx");
			Service service = new Service();
			EPin_ReloadSoap_BindingStub stub = new EPin_ReloadSoap_BindingStub(url, service);
			String resp = stub.request_Reload(msisdn, msisdn, "", amount, "rationalheads", "185784", "1073424");

			System.out.println("[response msg][" + resp + "]");
			//ResponseFormat =[response msg][001];
		} catch (Exception ex) {
			System.out.println("[Error][" + ex + "]");
			ex.printStackTrace();
		}
	}
}
