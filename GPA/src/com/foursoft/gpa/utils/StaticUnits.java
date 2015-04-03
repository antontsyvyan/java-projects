package com.foursoft.gpa.utils;

import java.util.TreeMap;

public final class StaticUnits {

	public static TreeMap<String,String> UNITS;
	public static TreeMap<String,String> ADMIN_UNITS;
	
	static{
		UNITS=new TreeMap<String,String>();
		
		UNITS.put("AL", "Liters alcohol 100%");
		UNITS.put("BT", "Bruto-registerton (2,8316 M3)");
		UNITS.put("CL", "Aantal cellen");
		UNITS.put("CT", "Aantal karaat (1 metriekkaraat=2x10-4)");
		UNITS.put("GR", "Gram");
		UNITS.put("GS", "Gram splijtbare isotopen");
		UNITS.put("K1", "Kilogram ad. 100%");
		UNITS.put("K2", "Kilogram netto uitlekgewicht");
		UNITS.put("KB", "Kilogram kaliumhydroxyde (bijtende potas)");
		UNITS.put("KC", "Kilogram choline chloride");
		UNITS.put("KD", "Kilogram drooggewicht ad 90%");
		UNITS.put("KG", "Kilogram");
		UNITS.put("KH", "Kilogram waterstofperoxyde");
		UNITS.put("KK", "Kilogram K2O");
		UNITS.put("KM", "Kilogram methylaminen");
		UNITS.put("KN", "Kilogram N");
		UNITS.put("KO", "Kilogram suiker met een opbrengst aan witte suiker van 92%");
		UNITS.put("KP", "Kilogram P2O5");
		UNITS.put("KS", "Kilogram natriumhydroxyde (bijtende soda)");
		UNITS.put("KU", "Kilogram U");
		UNITS.put("KW", "1000 kWh");
		UNITS.put("L1", "1000 Liter");
		UNITS.put("LT", "Liter");
		UNITS.put("M2", "Vierkante meter");
		UNITS.put("M3", "Kubieke meter");
		UNITS.put("M4", "1000 kubieke meter");
		UNITS.put("MB", "Kubieke meter, bij een druk van 1013 mbar en een temperatuur van 15 C");
		UNITS.put("MK", "Kilometer");
		UNITS.put("ML", "Strekkende meter");
		UNITS.put("MT", "Laadvermogen in metrieke ton");
		UNITS.put("PA", "Paar");
		UNITS.put("S0", "100 stuks");
		UNITS.put("S1", "1000 stuks");
		UNITS.put("ST", "Stuks");
		UNITS.put("TJ", "Terrajoule");
		UNITS.put("WT", "Aantal watt");		
	}
	
	static{
		ADMIN_UNITS=new TreeMap<String,String>();
		
		ADMIN_UNITS.put("CC", "Milliliters");
		ADMIN_UNITS.put("MG", "Milligrammen");
		ADMIN_UNITS.put("MML", "Strekkende millimeter");
		ADMIN_UNITS.put("CM2", "Vierkante centimeter");
		ADMIN_UNITS.put("CTNS", "Kartons of dozen");
		ADMIN_UNITS.put("KSTN", "Kisten");
		ADMIN_UNITS.put("ZKEN", "Zakken");
		ADMIN_UNITS.put("BLEN", "Balen");
		ADMIN_UNITS.put("RLEN", "Rollen");
		ADMIN_UNITS.put("HSPL", "Haspels");
		ADMIN_UNITS.put("BDLS", "Bundels");
		ADMIN_UNITS.put("STKS", "Stuks");
		ADMIN_UNITS.put("VTEN", "Vaten");
		ADMIN_UNITS.put("KRTN", "Kratten");
		ADMIN_UNITS.put("TNKS", "Tanks");
		ADMIN_UNITS.put("BLKN", "Blikken");
		ADMIN_UNITS.put("FLSN", "Flessen");
		ADMIN_UNITS.put("PLTS", "Pallets");
		ADMIN_UNITS.put("LSGS", "Los gestort");
		ADMIN_UNITS.put("CONT", "Containers");
		ADMIN_UNITS.put("COLL", "Colli");		
	}

}
