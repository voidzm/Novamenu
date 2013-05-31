//**
//**  GuiButtonTransparent.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiButtonTransparent extends Gui {
	
	private GuiNovamenuScreen mainMenu;
	public int x, y, width, height, alpha;
	public int id;
	public String text;
	private FontRenderer render;
	public boolean enabled = true;
	public boolean drawButton = true;

	public GuiButtonTransparent(GuiNovamenuScreen menu, int par1, int par2, int par3, int par4, int par5, String string) {
		this.mainMenu = menu;
		this.x = par1;
		this.y = par2;
		this.width = par3;
		this.height = par4;
		this.id = par5;
		this.text = string;
		this.alpha = 0;
		this.render = Minecraft.getMinecraft().fontRenderer;
	}
	
	public int getHoverState(boolean par1) {
		byte b0 = 1;
		if(!this.enabled) {
			b0 = 0;
		}
		else if(par1) {
			b0 = 2;
		}
		return b0;
	}

	public void draw(int mx, int my) {
		if(!drawButton) return;
		if(isInside(mx, my) && this.enabled) {
			alpha = 30;
		}
		else {
			alpha = 0;
		}
		Color boxColor = new Color(255, 255, 255, alpha);
		String textMod = text;
		if(isInside(mx, my) && this.enabled) {
			textMod = "> " + textMod + " <";
		}
		mainMenu.drawCenteredString(render, textMod, x + (width / 2), y + (height / 4), this.enabled ? Color.WHITE.getRGB() : 6710886);
		mainMenu.drawRect(x, y, x + width, y + height, boxColor.getRGB());
		this.draggedEvent(mx, my);
	}
	
	public boolean isInside(int mx, int my) {
		return mx >= x && my >= y && mx <= x + width && my <= y + height;
	}
	
	public boolean clickEvent(int mx, int my) {
		if((isInside(mx, my) && this.enabled) && this.drawButton) {
			alpha = 60;
			return true;
		}
		return false;
	}
	
	public void draggedEvent(int par1, int par2) {}
	
	public void releasedEvent(int par1, int par2) {}

}
