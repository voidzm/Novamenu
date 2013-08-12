//**
//**  NovamenuTransformer.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.asm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;
import cpw.mods.fml.relauncher.FMLLaunchHandler;

public class NovamenuTransformer extends AccessTransformer {
	
	protected static boolean doVerboseTransformer;
	
	private boolean minecraftTransformed = false;
	
	public NovamenuTransformer() throws IOException {
		super();
		System.out.println("[Novamenu] ASM transformer initializing.");
		doVerboseTransformer = Boolean.parseBoolean(System.getProperty("novamenu.doVerboseTransformer", "false"));
		if(doVerboseTransformer) {
			System.out.println("[Novamenu] Verbose ASM transformer enabled as a JVM parameter.");
		}
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		try {
			bytes = this.transformMinecraft(name, bytes);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return bytes;
	}
	
	private byte[] transformMinecraft(String name, byte[] bytes) {
		if(this.minecraftTransformed == true) return bytes;
		String className = NovamenuPlugin.isDevEnvironment ? "net.minecraft.client.Minecraft" : ReobfuscationMappingHelper.getInstance().attemptRemapClassName("net.minecraft.client.Minecraft");
		if(doVerboseTransformer) {
			System.out.println("Locating Minecraft.class; comparing " + name + " to target " + className + ".");
		}
		if(name.equals(className)) {
			if(doVerboseTransformer) {
				System.out.println("Minecraft.class located under " + name);
			}
			ClassReader cr = new ClassReader(bytes);
			ClassWriter cw = new ClassWriter(0);
			String methodName = NovamenuPlugin.isDevEnvironment ? "displayGuiScreen" : ReobfuscationMappingHelper.getInstance().attemptRemapMethodName("net.minecraft.client.Minecraft/displayGuiScreen");
			String methodDesc = NovamenuPlugin.isDevEnvironment ? "(Lnet/minecraft/client/gui/GuiScreen;)V" : "(L"+ReobfuscationMappingHelper.getInstance().attemptRemapClassName("net.minecraft.client.gui.GuiScreen")+";)V";
			ClassMethodVisitor cv = new ClassMethodVisitor(cw, methodName, methodDesc, DisplayGuiScreenMethodVisitor.class);
			cr.accept(cv, 0);
			bytes = cw.toByteArray();
			this.minecraftTransformed = true;
		}
		return bytes;
	}
	
}
