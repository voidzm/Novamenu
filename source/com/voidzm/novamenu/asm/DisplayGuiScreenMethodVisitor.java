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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

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
	
}
