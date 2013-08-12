//**
//**  ClassMethodVisitor.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.asm;

import static org.objectweb.asm.Opcodes.ASM4;

import java.lang.reflect.Constructor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassMethodVisitor extends ClassVisitor {

	private String methodToVisit;
	private String descToVisit;
	private Class<? extends MethodVisitor> visitorClass;
	
	private boolean methodTransformed = false;
	
	public ClassMethodVisitor(ClassVisitor cv, String method, String desc, Class<? extends MethodVisitor> mvClass) {
		super(ASM4, cv);
		methodToVisit = method;
		descToVisit = desc;
		visitorClass = mvClass;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if(methodTransformed == true) {
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
		if(NovamenuTransformer.doVerboseTransformer) {
			System.out.println("Locating method " + methodToVisit + " with desc " + descToVisit + "; checking " + name + ", " + desc + ".");
		}
		if(!name.equals(this.methodToVisit) || !desc.equals(this.descToVisit)) {
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
		else {
			if(NovamenuTransformer.doVerboseTransformer) {
				System.out.println(methodToVisit + " located!");
			}
			Constructor<? extends MethodVisitor> visitorConstructor = null;
			try {
				visitorConstructor = visitorClass.getConstructor(MethodVisitor.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			MethodVisitor target = null;
			try {
				target = visitorConstructor.newInstance(super.visitMethod(access, name, desc, signature, exceptions));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			methodTransformed = true;
			return target;
		}
	}

}
