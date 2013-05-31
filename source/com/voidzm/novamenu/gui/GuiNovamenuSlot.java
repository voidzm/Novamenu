//**
//**  GuiNovamenuSlot.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiNovamenuSlot {

	private final Minecraft mc;

	protected int width;
	protected int height;
	protected int top;
	protected int bottom;
	protected final int slotHeight;

	private int scrollUpButtonID;
	private int scrollDownButtonID;

	protected int mouseX;
	protected int mouseY;
	private float initialClickY = -2.0F;

	private float scrollMultiplier;
	private float amountScrolled;

	private int selectedElement = -1;
	private long lastClicked = 0L;

	private boolean showSelectionBox = true;
	private boolean field_77243_s;
	private int field_77242_t;
	
	public int boxshift = 2;
	
	public GuiNovamenuSlot(Minecraft minecraft, int par1, int par2, int par3, int par4, int par5) {
		this.mc = minecraft;
		this.width = par1;
		this.height = par2;
		this.top = par3;
		this.bottom = par4;
		this.slotHeight = par5;
	}
	
	public void func_77207_a(int par1, int par2, int par3, int par4) {
		this.width = par1;
		this.height = par2;
		this.top = par3;
		this.bottom = par4;
	}
	
	protected abstract int getSize();

	protected abstract void elementClicked(int par1, boolean par2);

	protected abstract boolean isSelected(int i);

	protected int getContentHeight() {
		return this.getSize() * this.slotHeight + this.field_77242_t;
	}

	protected abstract void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator);

	public void setShowSelectionBox(boolean par1) {
		this.showSelectionBox = par1;
	}

	protected void func_77223_a(boolean par1, int par2) {
		this.field_77243_s = par1;
		this.field_77242_t = par2;
		if(!par1) {
			this.field_77242_t = 0;
		}
	}

	protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator) {}

	protected void func_77224_a(int par1, int par2) {}

	protected void func_77215_b(int par1, int par2) {}

	public int func_77210_c(int par1, int par2) {
		int k = this.width / 2 - 110;
		int l = this.width / 2 + 110;
		int i1 = par2 - this.top - this.field_77242_t + (int)this.amountScrolled - 4;
		int j1 = i1 / this.slotHeight;
		return par1 >= k && par1 <= l && j1 >= 0 && i1 >= 0 && j1 < this.getSize() ? j1 : -1;
	}
	
	public void registerScrollButtons(List par1List, int par2, int par3) {
		this.scrollUpButtonID = par2;
		this.scrollDownButtonID = par3;
	}
	
	private void bindAmountScrolled() {
		int i = this.func_77209_d();
		if(i < 0) {
			i /= 2;
		}
		if(this.amountScrolled < 0.0F) {
			this.amountScrolled = 0.0F;
		}
		if(this.amountScrolled > (float)i) {
			this.amountScrolled = (float)i;
		}
	}

	public int func_77209_d() {
		return this.getContentHeight() - (this.bottom - this.top - 4);
	}

	public void func_77208_b(int par1) {
		this.amountScrolled += (float)par1;
		this.bindAmountScrolled();
		this.initialClickY = -2.0F;
	}

	public void actionPerformed(int id) {
		if(id == this.scrollUpButtonID) {
			this.amountScrolled -= (float)(this.slotHeight * 2 / 3);
			this.initialClickY = -2.0F;
			this.bindAmountScrolled();
		}
		else if(id == this.scrollDownButtonID) {
			this.amountScrolled += (float)(this.slotHeight * 2 / 3);
			this.initialClickY = -2.0F;
			this.bindAmountScrolled();
		}
	}

	public void drawScreen(int par1, int par2, float par3) {
		this.mouseX = par1;
		this.mouseY = par2;
		int k = this.getSize();
		int l = this.getScrollBarX();
		int i1 = l + 6;
		int j1;
		int k1;
		int l1;
		int i2;
		int j2;
		if(Mouse.isButtonDown(0)) {
			if(this.initialClickY == -1.0F) {
				boolean flag = true;
				if(par2 >= this.top && par2 <= this.bottom) {
					int k2 = this.width / 2 - 110;
					j1 = this.width / 2 + 110;
					k1 = par2 - this.top - this.field_77242_t + (int)this.amountScrolled - 4;
					l1 = k1 / this.slotHeight;
					if(par1 >= k2 && par1 <= j1 && l1 >= 0 && k1 >= 0 && l1 < k) {
						boolean flag1 = l1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
						this.elementClicked(l1, flag1);
						this.selectedElement = l1;
						this.lastClicked = Minecraft.getSystemTime();
					}
					else if(par1 >= k2 && par1 <= j1 && k1 < 0) {
						this.func_77224_a(par1 - k2, par2 - this.top + (int)this.amountScrolled - 4);
						flag = false;
					}
					if(par1 >= l && par1 <= i1) {
						this.scrollMultiplier = -1.0F;
						j2 = this.func_77209_d();
						if(j2 < 1) {
							j2 = 1;
						}
						i2 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
						if(i2 < 32) {
							i2 = 32;
						}
						if(i2 > this.bottom - this.top - 8) {
							i2 = this.bottom - this.top - 8;
						}
						this.scrollMultiplier /= (float)(this.bottom - this.top - i2) / (float)j2;
					}
					else {
						this.scrollMultiplier = 1.0F;
					}
					if(flag) {
						this.initialClickY = (float)par2;
					}
					else {
						this.initialClickY = -2.0F;
					}
				}
				else {
					this.initialClickY = -2.0F;
				}
			}
			else if(this.initialClickY >= 0.0F) {
				this.amountScrolled -= ((float)par2 - this.initialClickY) * this.scrollMultiplier;
				this.initialClickY = (float)par2;
			}
		}
		else {
			while(!this.mc.gameSettings.touchscreen && Mouse.next()) {
				int l2 = Mouse.getEventDWheel();
				if(l2 != 0) {
					if(l2 > 0) {
						l2 = -1;
					}
					else if(l2 < 0) {
						l2 = 1;
					}
					this.amountScrolled += (float)(l2 * this.slotHeight / 2);
				}
			}
			this.initialClickY = -1.0F;
		}
		this.bindAmountScrolled();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int f = res.getScaleFactor();
		int trueb = this.height - this.bottom;
		int trueh = this.height - this.top;
		GL11.glScissor(0, (trueb+1)*f, this.width*f, ((trueh - trueb) - 2)*f);
		
		Tessellator tessellator = Tessellator.instance;
		drawContainerBackground(tessellator);
		j1 = this.width / 2 - 92 - 16;
		k1 = this.top + 4 - (int)this.amountScrolled;
		if(this.field_77243_s) {
			this.func_77222_a(j1, k1, tessellator);
		}
		int i3;
		for(l1 = 0; l1 < k; ++l1) {
			j2 = k1 + l1 * this.slotHeight + this.field_77242_t;
			i2 = this.slotHeight - 4;
			if(j2 <= this.bottom && j2 + i2 >= this.top) {
				if(this.showSelectionBox && this.isSelected(l1)) {
					i3 = this.width / 2 - 110;
					int j3 = this.width / 2 + 110;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glDisable(GL11.GL_ALPHA_TEST);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator.startDrawingQuads();
					tessellator.setColorRGBA_I(6710886, 96);
					tessellator.addVertexWithUV((double)(i3 - boxshift), (double)((j2 + i2 + 2) + boxshift), 0.0D, 0.0D, 1.0D);
					tessellator.addVertexWithUV((double)(j3 + boxshift), (double)((j2 + i2 + 2) + boxshift), 0.0D, 1.0D, 1.0D);
					tessellator.addVertexWithUV((double)(j3 + boxshift), (double)((j2 - 2) - boxshift), 0.0D, 1.0D, 0.0D);
					tessellator.addVertexWithUV((double)(i3 - boxshift), (double)((j2 - 2) - boxshift), 0.0D, 0.0D, 0.0D);
					tessellator.setColorRGBA_I(0, 127);
					tessellator.addVertexWithUV((double)((i3 + 1) - boxshift), (double)((j2 + i2 + 1) + boxshift), 0.0D, 0.0D, 1.0D);
					tessellator.addVertexWithUV((double)((j3 - 1) + boxshift), (double)((j2 + i2 + 1) + boxshift), 0.0D, 1.0D, 1.0D);
					tessellator.addVertexWithUV((double)((j3 - 1) + boxshift), (double)((j2 - 1) - boxshift), 0.0D, 1.0D, 0.0D);
					tessellator.addVertexWithUV((double)((i3 + 1) - boxshift), (double)((j2 - 1) - boxshift), 0.0D, 0.0D, 0.0D);
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				this.drawSlot(l1, j1, j2, i2, tessellator);
			}
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.overlayBackground(0, this.top, 255, 255);
		this.overlayBackground(this.bottom, this.height, 255, 255);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		j2 = this.func_77209_d();
		if(j2 > 0) {
			i2 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
			if(i2 < 32) {
				i2 = 32;
			}
			if(i2 > this.bottom - this.top - 8) {
				i2 = this.bottom - this.top - 8;
			}
			i3 = (int)this.amountScrolled * (this.bottom - this.top - i2) / j2 + this.top;
			if(i3 < this.top) {
				i3 = this.top;
			}
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertexWithUV((double)l, (double)this.bottom, 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double)i1, (double)this.bottom, 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double)i1, (double)this.top, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double)l, (double)this.top, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(8421504, 255);
			tessellator.addVertexWithUV((double)l, (double)(i3 + i2), 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double)i1, (double)(i3 + i2), 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double)i1, (double)i3, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double)l, (double)i3, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(12632256, 255);
			tessellator.addVertexWithUV((double)l, (double)(i3 + i2 - 1), 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double)(i1 - 1), (double)(i3 + i2 - 1), 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double)(i1 - 1), (double)i3, 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double)l, (double)i3, 0.0D, 0.0D, 0.0D);
			tessellator.draw();
		}
		this.func_77215_b(par1, par2);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();
	}

	protected int getScrollBarX() {
		return this.width / 2 + 124;
	}

	protected void drawBackground() {}

	protected void overlayBackground(int par1, int par2, int par3, int par4) {}

	protected void drawContainerBackground(Tessellator tess) {}
	
}
