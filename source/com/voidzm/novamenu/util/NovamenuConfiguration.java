package com.voidzm.novamenu.util;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class NovamenuConfiguration {

	public static final String CATEGORY_TOP = "top-level";
	public static final String CATEGORY_SECONDARY = "secondary-level";
	
	private Configuration internalCfg;
	
	//**  TOP LEVEL  **//
	
	public boolean useCustomCreateWorldMenu;
	public boolean useCustomOptionsMenu;
	
	//**  SECONDARY LEVEL  **//
	
	public boolean useCustomVideoSettingsMenu;
	
	public NovamenuConfiguration(File file) {
		internalCfg = new Configuration(file);
		internalCfg.save();
		internalCfg.load();
		this.loadTopLevelConfig();
		this.loadSecondaryLevelConfig();
		internalCfg.save();
		System.out.println("[Novamenu] Configuration loaded from disk.");
	}
	
	private void loadTopLevelConfig() {
		useCustomCreateWorldMenu = internalCfg.get(CATEGORY_TOP, "useCustomCreateWorldMenu", "true").getBoolean(true);
		useCustomOptionsMenu = internalCfg.get(CATEGORY_TOP, "useCustomOptionsMenu", "true").getBoolean(true);
	}
	
	private void loadSecondaryLevelConfig() {
		useCustomVideoSettingsMenu = internalCfg.get(CATEGORY_SECONDARY, "useCustomVideoSettingsMenu", "true").getBoolean(true);
	}
	
}
