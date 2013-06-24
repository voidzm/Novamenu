//**
//**  DisplayGuiScreenMethodVisitor.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.asm;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.INSTANCEOF;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;

import net.minecraft.client.gui.GuiDisconnected;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.voidzm.novamenu.gui.GuiNovamenuDisconnected;

public class DisplayGuiScreenMethodVisitor extends MethodVisitor {

	public DisplayGuiScreenMethodVisitor(MethodVisitor visitor) {
		super(ASM4, visitor);
	}

	@Override
	public void visitCode() {
		mv.visitVarInsn(ALOAD, 1);
		mv.visitTypeInsn(INSTANCEOF, "net/minecraft/client/gui/GuiIngameMenu");
		Label j1 = new Label();
		mv.visitJumpInsn(IFEQ, j1);
		mv.visitTypeInsn(NEW, "com/voidzm/novamenu/gui/GuiNovamenuIngameMenu");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "com/voidzm/novamenu/gui/GuiNovamenuIngameMenu", "<init>", "()V");
		mv.visitVarInsn(ASTORE, 1);
		mv.visitLabel(j1);
		
		mv.visitVarInsn(ALOAD, 1);
		mv.visitTypeInsn(INSTANCEOF, "net/minecraft/client/gui/GuiDisconnected");
		Label j2 = new Label();
		mv.visitJumpInsn(IFEQ, j2);
		mv.visitTypeInsn(NEW, "com/voidzm/novamenu/gui/GuiNovamenuDisconnected");
		mv.visitInsn(DUP);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESPECIAL, "com/voidzm/novamenu/gui/GuiNovamenuDisconnected", "<init>", "(Lnet/minecraft/client/gui/GuiScreen;)V");
		mv.visitVarInsn(ASTORE, 1);
		mv.visitLabel(j2);
	
		mv.visitCode();
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		String fieldName = NovamenuPlugin.isDevEnvironment ? "showDebugInfo" : ReobfuscationMappingHelper.getInstance().attemptRemapFieldName("net.minecraft.client.settings.GameSettings/showDebugInfo");
		if(opcode == PUTFIELD && name.equals(fieldName)) {
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(INSTANCEOF, "net/minecraft/client/gui/GuiMainMenu");
			Label j1 = new Label();
			mv.visitJumpInsn(IFEQ, j1);
			mv.visitTypeInsn(NEW, "com/voidzm/novamenu/gui/GuiNovamenuMainMenu");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "com/voidzm/novamenu/gui/GuiNovamenuMainMenu", "<init>", "()V");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitLabel(j1);
		}
		mv.visitFieldInsn(opcode, owner, name, desc);
	}
	
	
	@Override
	public void visitTypeInsn(int opcode, String type)  {
		if(opcode == NEW && type.equals("net/minecraft/client/gui/GuiGameOver")) {
			mv.visitTypeInsn(NEW, "com/voidzm/novamenu/gui/GuiNovamenuGameOver");
		}
		else {
			mv.visitTypeInsn(opcode, type);
		}
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		if(opcode == INVOKESPECIAL && owner.equals("net/minecraft/client/gui/GuiGameOver") && name.equals("<init>") && desc.equals("()V")) {
			mv.visitMethodInsn(INVOKESPECIAL, "com/voidzm/novamenu/gui/GuiNovamenuGameOver", "<init>", "()V");
		}
		else {
			mv.visitMethodInsn(opcode, owner, name, desc);
		}
	}
	
}
