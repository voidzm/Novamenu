package com.voidzm.novamenu.asm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ReobfuscationMappingHelper {

	private static ReobfuscationMappingHelper instance;
	
	private HashMap<String, String> classes = new HashMap<String, String>();
	private HashMap<String, String> methods = new HashMap<String, String>();
	private HashMap<String, String> fields = new HashMap<String, String>();
	
	public static ReobfuscationMappingHelper getInstance() {
		if(instance == null) instance = new ReobfuscationMappingHelper();
		return instance;
	}
	
	public ReobfuscationMappingHelper() {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			InputStream istream = this.getClass().getResourceAsStream("/remap.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
			String current = null;
			while((current = reader.readLine()) != null) {
				lines.add(current);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String versionIdentifier = lines.remove(0);
		if(!versionIdentifier.equals(NovamenuPlugin.minecraftVersion)) {
			throw new RuntimeException("The mappings in remap.csv DO NOT match the current Minecraft version! This version of Novamenu is likely out of date.");
		}
		for(String line : lines) {
			String[] values = line.split(",");
			if(values[0].equals("class")) {
				classes.put(values[1], values[2]);
			}
			else if(values[0].equals("method")) {
				methods.put(values[1], values[2]);
			}
			else if(values[0].equals("field")) {
				fields.put(values[1], values[2]);
			}
		}
	}
	
	/**
	 * The argument should be formatted as: "java.lang.String"
	 */
	public String attemptRemapClassName(String deobfName) {
		if(classes.containsKey(deobfName)) return classes.get(deobfName);
		else return deobfName;
	}
	
	/**
	 * The argument should be formatted as: "java.lang.String/someMethodName"
	 */
	public String attemptRemapMethodName(String deobfName) {
		if(methods.containsKey(deobfName)) return methods.get(deobfName);
		else return deobfName;
	}
	
	/**
	 * The argument should be formatted as: "java.lang.String/someFieldName"
	 */
	public String attemptRemapFieldName(String deobfName) {
		if(fields.containsKey(deobfName)) return fields.get(deobfName);
		else return deobfName;
	}
	
}
