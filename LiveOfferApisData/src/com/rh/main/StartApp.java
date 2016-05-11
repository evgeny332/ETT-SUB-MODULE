package com.rh.main;

import com.rh.api.ClickyApiParsing;
import com.rh.api.IODisplayApi;
import com.rh.api.ParseCrunchieMediaUrl;
import com.rh.api.ParseMiniMob;
import com.rh.api.ParseUrlClicksMob;
import com.rh.api.ParseWoobi;
import com.rh.api.PersonListApiParser;
import com.rh.api.SupersonicParsing;
import com.rh.api.mappstreetparsing;
import com.rh.utility.ReadUrl;

public class StartApp {

	public static void main(String[] args) {

		ReadUrl readUrl = new ReadUrl();
		ParseMiniMob.MiniMob(readUrl);
		ParseUrlClicksMob.clicksMob(readUrl);
		IODisplayApi.ioDisplayApi(readUrl);
		PersonListApiParser.personaList(readUrl);
		ParseCrunchieMediaUrl.crunchieMediaUrl(readUrl);
		mappstreetparsing.MappStreet(readUrl);
		SupersonicParsing.superSonic(readUrl);
		ParseWoobi.woobi(readUrl);
		ClickyApiParsing.clickyUrlParsing(readUrl);

	}
}
