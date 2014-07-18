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

	private boolean showDebugInfoFieldTransformed = false;
	private boolean guiGameOverTypeTransformed = false;
	private boolean guiGameOverMethodTransformed = false;
	
	public DisplayGuiScreenMethodVisitor(MethodVisitor visitor) {
		super(ASM4, visitor);
	}

	@Override
	public void visitCode() {
		if(NovamenuTransformer.doVerboseTransformer) {
			System.out.println("Transforming Minecraft.displayGuiScreen method head.");
		}

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
		if(this.showDebugInfoFieldTransformed == true) {
			mv.visitFieldInsn(opcode, owner, name, desc);
			return;
		}
		String fieldName = NovamenuPlugin.isDevEnvironment ? "showDebugInfo" : ReobfuscationMappingHelper.getInstance().attemptRemapFieldName("net.minecraft.client.settings.GameSettings/showDebugInfo");
		if(NovamenuTransformer.doVerboseTransformer) {
			System.out.println("Locating GameSettings.showDebugInfo, comparing " + name + " to target " + fieldName + ".");
		}
		if(opcode == PUTFIELD && name.equals(fieldName)) {
			if(NovamenuTransformer.doVerboseTransformer) {
				System.out.println("GameSettings.showDebugInfo located under " + name);
			}
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(INSTANCEOF, "net/minecraft/client/gui/GuiMainMenu");
			Label j1 = new Label();
			mv.visitJumpInsn(IFEQ, j1);
			mv.visitTypeInsn(NEW, "com/voidzm/novamenu/gui/GuiNovamenuMainMenu");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "com/voidzm/novamenu/gui/GuiNovamenuMainMenu", "<init>", "()V");
			mv.visitVarInsn(ASTORE, 1);
			mv.visitLabel(j1);
			this.showDebugInfoFieldTransformed = true;
		}
		mv.visitFieldInsn(opcode, owner, name, desc);
	}
	
	
	@Override
	public void visitTypeInsn(int opcode, String type)  {
		if(this.guiGameOverTypeTransformed == true) {
			mv.visitTypeInsn(opcode, type);
			return;
		}
		if(opcode == NEW && type.equals("net/minecraft/client/gui/GuiGameOver")) {
			if(NovamenuTransformer.doVerboseTransformer) {
				System.out.println("Transforming GuiGameOver type INSN.");
			}
			mv.visitTypeInsn(NEW, "com/voidzm/novamenu/gui/GuiNovamenuGameOver");
			this.guiGameOverTypeTransformed = true;
		}
		else {
			mv.visitTypeInsn(opcode, type);
		}
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		if(this.guiGameOverMethodTransformed == true) {
			mv.visitMethodInsn(opcode, owner, name, desc);
			return;
		}
		if(opcode == INVOKESPECIAL && owner.equals("net/minecraft/client/gui/GuiGameOver") && name.equals("<init>") && desc.equals("()V")) {
			if(NovamenuTransformer.doVerboseTransformer) {
				System.out.println("Transforming GuiGameOver method INSN.");
			}
			mv.visitMethodInsn(INVOKESPECIAL, "com/voidzm/novamenu/gui/GuiNovamenuGameOver", "<init>", "()V");
			this.guiGameOverMethodTransformed = true;
		}
		else {
			mv.visitMethodInsn(opcode, owner, name, desc);
		}
	}
	
}
