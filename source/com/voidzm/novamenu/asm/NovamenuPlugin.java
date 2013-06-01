package com.voidzm.novamenu.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions(value={"com.voidzm.novamenu.asm"})
@MCVersion(value="1.5.2")
public class NovamenuPlugin implements IFMLLoadingPlugin, IFMLCallHook {

	public static File location;
	public static boolean isDevEnvironment = true;
	public static String minecraftVersion = "1.5.2";
	public static String novamenuVersion = "1.0.1";
	
	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.voidzm.novamenu.asm.NovamenuTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "com.voidzm.novamenu.asm.NovamenuModContainer";
	}

	@Override
	public String getSetupClass() {
		return "com.voidzm.novamenu.asm.NovamenuPlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if(data.containsKey("coremodLocation")) location = (File)data.get("coremodLocation");
		if(data.containsKey("runtimeDeobfuscationEnabled")) isDevEnvironment = !(Boolean)data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public Void call() throws Exception {
		return null;
	}

}

