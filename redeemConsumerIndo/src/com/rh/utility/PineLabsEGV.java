package com.rh.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherRequest;
import org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GenerateVoucherResponse;
import org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.GeneratedVoucherDetails;
import org.datacontract.schemas._2004._07.Pinelabs_NovaWebController_Interfaces.VoucherRequest;

import com.pinelabs.novaservice.IPineGiftServiceProxy;
import com.rh.config.DBConfigHolder;
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.GiftVoucher;
import com.rh.persistence.domain.PineLabs;
import com.rh.persistence.domain.UserRedeem;

public class PineLabsEGV {

	private static Log log = LogFactory.getLog(PineLabsEGV.class);
	private UserRedeem userRedeem;
	private DBPersister dbPersister;
	private PineLabs pineLabs;
	String schemeId;
//	private DBConfigHolder configHolder;

	public PineLabsEGV(UserRedeem userRedeem, DBPersister dbPersister, DBConfigHolder configHolder2) {
		try {
			this.dbPersister = dbPersister;
			this.userRedeem = userRedeem;
//			this.configHolder = configHolder2;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR !! in PineLabsEGV: " + e);
		}
	}

	public String createEGV() {

		try {
			pineLabs = dbPersister.getPineLabs(userRedeem.getMsisdn());
			long start = System.currentTimeMillis();
			long tId = System.currentTimeMillis() + userRedeem.getEttId();
			userRedeem.setTrans_id_ett(String.valueOf(tId));
			String trans = getVouchers(tId);
			if (!trans.equals("FAILED")) {
				System.out.println("RECHARGE URL| PINELABS |RESP|" + trans + " |TIME|" + (System.currentTimeMillis() - start));
				return trans;
			}
			System.out.println("RECHARGE URL| PINELABS |RESP|" + trans + " |TIME|" + (System.currentTimeMillis() - start));
			return "FAILED";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getVouchers(long requestId) throws Exception {
		GiftVoucher giftVoucher = new GiftVoucher();
		IPineGiftServiceProxy giftServiceProxy = new IPineGiftServiceProxy();
		System.out.println(pineLabs.toString());
		VoucherRequest[] requestVoucherList = new VoucherRequest[] { new VoucherRequest(pineLabs.getPurchaseValue(), pineLabs.getFaceValue(), 1l, pineLabs.getSchemeId()+"") };
		GenerateVoucherRequest voucherRequest = new GenerateVoucherRequest("Rational@1234", "", "", "", "", "", "", requestId, requestVoucherList, new Long(116));
		GenerateVoucherResponse voucherResponse = new GenerateVoucherResponse();
		voucherResponse = giftServiceProxy.generateVoucher(voucherRequest);

		String respCode = voucherResponse.getResponseCode();

		if (respCode.equals("0")) {
			GeneratedVoucherDetails[] details = voucherResponse.getResponseVoucherList();

			for (GeneratedVoucherDetails details2 : details) {
				log.info("[ " + respCode + " | " + voucherResponse.getResponseMessage() + " | VoucherId : " + details2.getVoucherId() + ",BrandId : " + details2.getBrandId() + ",OrderDate : " + details2.getOrderDate()
						+ ",ExpiryDate : " + details2.getExpiryDate() + ",VoucherAmount : " + details2.getVoucherAmount() + ",CurrentBalance : " + details2.getCurrentBalance() + ",VoucherNumber : "
						+ details2.getVoucherNumber() + ",IsThirdParty : " + details2.getIsThirdParty() + ",Pin : " + details2.getPin() + ",SchemeId : " + details2.getSchemeId() + ",FaceValue : "
						+ details2.getFaceValue() + ",IsEvv: " + details2.getIsEvv());
				giftVoucher.setAmount(Integer.parseInt(details2.getVoucherAmount()));
				giftVoucher.setCode(details2.getVoucherNumber());
				giftVoucher.setDispatchStatus("DISPATCHED");
				giftVoucher.setEttId(userRedeem.getEttId());
				giftVoucher.setExpiryDate(details2.getExpiryDate());
				giftVoucher.setPin(details2.getPin());
				giftVoucher.setTransactionId(details2.getVoucherId() + "");
				giftVoucher.setMedium("INLINE");
				giftVoucher.setName(details2.getBrandId() + "");
				userRedeem.setTrans_id(details2.getVoucherId() + "");

				dbPersister.insertGiftVoucher(giftVoucher);
				return "SUCCESS";
			}
		}
		
		log.info("[ " + respCode + " | " + voucherResponse.getResponseMessage() + "]");
		return "FAILED";
	}

	/*
	 * private void getVoucherStatus() throws Exception { IPineGiftServiceProxy giftServiceProxy = new IPineGiftServiceProxy(); GetStatusOfGeneratedVouchersRequest generatedVouchersRequest = new
	 * GetStatusOfGeneratedVouchersRequest("Rational@1234", 1234465l, new Long(116)); GetStatusOfGeneratedVouchersResponse generatedVouchersResponse = new GetStatusOfGeneratedVouchersResponse();
	 * GeneratedVoucherDetails details = new GeneratedVoucherDetails(); System.out.println(generatedVouchersResponse.getResponseCode() + " , " + generatedVouchersResponse.getResponseMessage() + " , "
	 * + details.getPin() + " , " + details.getVoucherId()); }
	 */
}
