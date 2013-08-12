//**
//**  GuiNovamenuScreen.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiNovamenuScreen extends GuiScreen {

	private BufferedImage background;
	protected ArrayList<GuiButtonTransparent> buttons = new ArrayList<GuiButtonTransparent>();
	private GuiButtonTransparent selectedButton = null;
	
	public int imageTick = 0;
	
	private static final float imageTime = 250.0F;
	private static final float transitionTime = 120.0F;
	
	private int imageCount = 0;
	
	public GuiNovamenuScreen() {
		try {
			background = ImageIO.read(this.getClass().getResourceAsStream("/assets/minecraft/textures/gui/bg.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageTick = new Random().nextInt(6)*(int)imageTime;
		this.setupTextureIndexes();
	}

	private void setupTextureIndexes() {
		boolean tryToFindImage = true;
		while(tryToFindImage) {
			InputStream targetStream = this.getClass().getResourceAsStream("/assets/minecraft/textures/gui/bg"+(imageCount == 0 ? "" : imageCount+1)+".png");
			if(targetStream == null) {
				tryToFindImage = false;
			}
			else {
				imageCount++;
			}
		}
	}
	
	public void buttonEvent(int id) {}

	@Override
	protected void mouseClicked(int mx, int my, int par3) {
		if(par3 == 0) {
			for(int l = 0; l < this.buttons.size(); ++l) {
				GuiButtonTransparent guibutton = (GuiButtonTransparent)this.buttons.get(l);
				if(guibutton.clickEvent(mx, my)) {
					this.selectedButton = guibutton;
					this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					this.buttonEvent(guibutton.id);
				}
			}
		}
	}

	@Override
	protected void mouseMovedOrUp(int mx, int my, int par3) {
		if(this.selectedButton != null && par3 == 0) {
			this.selectedButton.releasedEvent(mx, my);
			this.selectedButton = null;
		}
	}

	@Override
	public void updateScreen() {
		imageTick++;
	}

	public void drawScreenBackground(int mouseX, int mouseY, float tick) {
		if(imageTick >= imageTime*imageCount) imageTick = 0;
		float tickf = ((float)imageTick)+tick;
		float indexf = imageTick / imageTime;
		int index = (int)Math.floor(indexf);
		float progress = tickf - (imageTime * (float)index);
		ResourceLocation mainResourceLoc = new ResourceLocation("textures/gui/bg"+(index == 0 ? "" : index+1)+".png");
		this.mc.renderEngine.func_110577_a(mainResourceLoc);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int[] locs = this.calcPositions();
		this.drawTexture(locs[0], locs[1], locs[2], locs[3]);
		if(progress >= (imageTime-transitionTime)) {
			GL11.glPushMatrix();
			double elapsed = progress-(imageTime-transitionTime);
			elapsed /= (double)transitionTime;
			double alpha = calculateEasingFunction(elapsed);
			int indexAdj = index < imageCount - 1 ? index + 1 : 0;
			ResourceLocation secondaryResourceLoc = new ResourceLocation("textures/gui/bg"+(indexAdj == 0 ? "" : indexAdj+1)+".png");
			this.mc.renderEngine.func_110577_a(secondaryResourceLoc);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4d(1.0D, 1.0D, 1.0D, (double)alpha);
			this.drawTexture(locs[0], locs[1], locs[2], locs[3]);
			GL11.glPopMatrix();
		}
	}
	
	public void drawScreenForeground(int mouseX, int mouseY, float tick) {
		for(GuiButtonTransparent iterated : buttons) {
			iterated.draw(mouseX, mouseY);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float tick) {
		this.drawScreenBackground(mouseX, mouseY, tick);
		this.drawScreenForeground(mouseX, mouseY, tick);
	}
	
	protected int[] calcPositions() {
		int[] pos = new int[4];
		float bgRatio = (float)background.getWidth() / (float)background.getHeight();
		if((float)this.width / (float)this.height < bgRatio) {
			pos[2] = (int)(height * bgRatio);
			pos[3] = height;
		}
		else {
			pos[2] = width;
			pos[3] = (int)(width / bgRatio);
		}
		pos[0] = width / 2 - pos[2] / 2;
		pos[1] = height / 2 - pos[3] / 2;
		return pos;
	}
	
	protected void drawTexture(int x, int y, int w, int h) {
		Tessellator tes = Tessellator.instance;
		tes.startDrawingQuads();
		tes.addVertexWithUV(x, y + h, 0, 0, 1);
		tes.addVertexWithUV(x + w, y + h, 0, 1, 1);
		tes.addVertexWithUV(x + w, y, 0, 1, 0);
		tes.addVertexWithUV(x, y, 0, 0, 0);
		tes.draw();
	}
	
	public Minecraft getMinecraft() {
		return this.mc;
	}
	
	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
	
	public float getZLevel() {
		return this.zLevel;
	}
	
	public static double calculateEasingFunction(double elapsed) {
		double constant = 4.0D;
		return Math.pow(elapsed, constant) / (Math.pow(elapsed, constant) + Math.pow(1.0D - elapsed, constant));
	}

}
