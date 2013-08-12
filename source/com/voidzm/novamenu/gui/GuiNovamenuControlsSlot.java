package com.voidzm.novamenu.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNovamenuControlsSlot extends GuiNovamenuSlot {

	private GuiNovamenuControls parent;
	private GameSettings settings;
	private String[] message;
	private int mx, my;
	private int selected = -1;
	
	public GuiNovamenuControlsSlot(GuiNovamenuControls controls, GameSettings gameSettings) {
		super(Minecraft.getMinecraft(), controls.width, controls.height - 88, 40, controls.height - 48, 25);
		this.parent = controls;
		this.settings = gameSettings;
	}

	@Override
	protected int getSize() {
		return settings.keyBindings.length;
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		if(!flag) {
			if(selected == -1) {
				selected = i;
			}
			else {
				settings.setKeyBinding(selected, -100);
				selected = -1;
				KeyBinding.resetKeyBindingArrayAndHash();
			}
		}
	}

	@Override
	protected boolean isSelected(int i) {
		return false;
	}
	
	@Override
	protected void drawBackground() {}

	@Override
	public void drawScreen(int mX, int mY, float f) {
		this.mx = mX;
		this.my = mY;
		if(selected != -1 && !Mouse.isButtonDown(0) && Mouse.getDWheel() == 0) {
			if(Mouse.next() && Mouse.getEventButtonState()) {
				System.out.println(Mouse.getEventButton());
				settings.setKeyBinding(selected, -100 + Mouse.getEventButton());
				selected = -1;
				KeyBinding.resetKeyBindingArrayAndHash();
			}
		}
		super.drawScreen(mX, mY, f);
	}

	@Override
	protected void drawSlot(int index, int xPosition, int yPosition, int l, Tessellator tessellator) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		ScaledResolution res = new ScaledResolution(settings, parent.getMinecraft().displayWidth, parent.getMinecraft().displayHeight);
		int f = res.getScaleFactor();
		GL11.glScissor(0, 49*f, parent.width*f, (parent.height-90)*f);
		int width = 70;
		int height = 20;
		xPosition -= 20;
		boolean flag = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		int k = (flag ? 2 : 1);
		ResourceLocation rsrcLoc = new ResourceLocation("textures/gui/widgets.png");
		Minecraft.getMinecraft().func_110434_K().func_110577_a(rsrcLoc);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		parent.drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
		parent.drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
		parent.drawString(parent.getFontRenderer(), settings.getKeyBindingDescription(index), xPosition + width + 4, yPosition + 6, 0xFFFFFFFF);
		boolean conflict = false;
		for(int x = 0; x < settings.keyBindings.length; x++) {
			if(x != index && settings.keyBindings[x].keyCode == settings.keyBindings[index].keyCode) {
				conflict = true;
				break;
			}
		}
		String str = (conflict ? EnumChatFormatting.RED : "") + settings.getOptionDisplayString(index);
		str = (index == selected ? EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<" : str);
		parent.drawCenteredString(Minecraft.getMinecraft().fontRenderer, str, xPosition + (width / 2), yPosition + (height - 8) / 2, 0xFFFFFFFF);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();
	}

	public boolean keyTyped(char c, int i) {
		if(selected != -1) {
			settings.setKeyBinding(selected, i);
			selected = -1;
			KeyBinding.resetKeyBindingArrayAndHash();
			return false;
		}
		return true;
	}

}
