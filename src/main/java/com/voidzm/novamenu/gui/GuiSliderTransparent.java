package com.voidzm.novamenu.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.EnumOptions;

public class GuiSliderTransparent extends GuiButtonTransparent {

	public float sliderValue = 1.0F;
	public boolean dragging = false;
	private EnumOptions idFloat = null;
	private GuiNovamenuScreen parent;
	
	public GuiSliderTransparent(GuiNovamenuScreen screen, int par1, int par2, int par3, EnumOptions par4EnumOptions, String par5Str, float par6) {
		super(screen, par2, par3, 150, 16, par1, par5Str);
		this.idFloat = par4EnumOptions;
		this.sliderValue = par6;
		this.parent = screen;
	}
	
	@Override
	public int getHoverState(boolean par1) {
		return 0;
	}

	public void draw(int mx, int my) {
		if(!drawButton) return;
		if((isInside(mx, my) || dragging) && this.enabled) {
			alpha = 30;
		}
		else {
			alpha = 0;
		}
		Color boxColor = new Color(255, 255, 255, alpha);
		Color sliderColor = new Color(255, 255, 255, 50);
		String textMod = text;
		if(isInside(mx, my) && this.enabled) {
			textMod = "> " + textMod + " <";
		}
		parent.drawCenteredString(parent.getFontRenderer(), textMod, x + (width / 2), y + (height / 4), this.enabled ? Color.WHITE.getRGB() : 6710886);
		parent.drawRect(x, y, x + width, y + height, boxColor.getRGB());
		parent.drawRect(x + (int)((float)(width - 8) * this.sliderValue), y, x + (int)((float)(width - 8) * this.sliderValue) + 8, y + height, sliderColor.getRGB());
		this.draggedEvent(mx, my);
	}
	
	@Override
	public void draggedEvent(int mx, int my) {
		if(this.drawButton) {
			if(this.dragging) {
				this.sliderValue = (float)(mx - (this.x + 4)) / (float)(this.width - 8);
				if(this.sliderValue < 0.0F) {
					this.sliderValue = 0.0F;
				}
				if(this.sliderValue > 1.0F) {
					this.sliderValue = 1.0F;
				}
				Minecraft.getMinecraft().gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
				this.text = Minecraft.getMinecraft().gameSettings.getKeyBinding(this.idFloat);
			}
		}
	}

	@Override
	public boolean clickEvent(int mx, int my) {
		if(super.clickEvent(mx, my)) {
			this.sliderValue = (float)(mx - (this.x + 4)) / (float)(this.width - 8);
			if(this.sliderValue < 0.0F) {
				this.sliderValue = 0.0F;
			}
			if(this.sliderValue > 1.0F) {
				this.sliderValue = 1.0F;
			}
			Minecraft.getMinecraft().gameSettings.setOptionFloatValue(this.idFloat, this.sliderValue);
			this.text = Minecraft.getMinecraft().gameSettings.getKeyBinding(this.idFloat);
			this.dragging = true;
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void releasedEvent(int par1, int par2) {
		this.dragging = false;
	}
	
}
