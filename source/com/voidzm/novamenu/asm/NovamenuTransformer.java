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
import cpw.mods.fml.relauncher.FMLRelauncher;

public class NovamenuTransformer extends AccessTransformer {
	
	public NovamenuTransformer() throws IOException {
		super();
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		try {
			if(FMLRelauncher.side().equals("CLIENT")) {
				bytes = this.transformMinecraft(name, bytes);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return bytes;
	}
	
	private byte[] transformMinecraft(String name, byte[] bytes) {
		String className = NovamenuPlugin.isDevEnvironment ? "net.minecraft.client.Minecraft" : ReobfuscationMappingHelper.getInstance().attemptRemapClassName("net.minecraft.client.Minecraft");
		if(name.equals(className)) {
			ClassReader cr = new ClassReader(bytes);
			ClassWriter cw = new ClassWriter(0);
			String methodName = NovamenuPlugin.isDevEnvironment ? "displayGuiScreen" : ReobfuscationMappingHelper.getInstance().attemptRemapMethodName("net.minecraft.client.Minecraft/displayGuiScreen");
			String methodDesc = NovamenuPlugin.isDevEnvironment ? "(Lnet/minecraft/client/gui/GuiScreen;)V" : "(L"+ReobfuscationMappingHelper.getInstance().attemptRemapClassName("net.minecraft.client.gui.GuiScreen")+";)V";
			ClassMethodVisitor cv = new ClassMethodVisitor(cw, methodName, methodDesc, DisplayGuiScreenMethodVisitor.class);
			cr.accept(cv, 0);
			bytes = cw.toByteArray();
		}
		return bytes;
	}
	
}
