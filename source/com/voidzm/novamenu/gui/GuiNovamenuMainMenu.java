//**
//**  GuiNovamenuMainMenu.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

import com.voidzm.novamenu.asm.NovamenuPlugin;

public class GuiNovamenuMainMenu extends GuiNovamenuScreen {
	
	public GuiNovamenuMainMenu() {
		StringTranslate translator = StringTranslate.getInstance();
		createButton(translator.translateKey("menu.singleplayer"));
		createButton(translator.translateKey("menu.multiplayer"));
		createButton(translator.translateKey("menu.options"));
		createButton("Mods");
		createButton(translator.translateKey("menu.quit"));
	}
	
	private void createButton(String text) {
		if(buttons.size() != 4) buttons.add(new GuiButtonTransparent(this, 22, 48 + (buttons.size() * 22), 106, 16, buttons.size(), text));
		else buttons.add(new GuiButtonTransparent(this, 22, 64 + (buttons.size() * 22), 106, 16, buttons.size(), text));
	} 
	
	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			mc.displayGuiScreen(new GuiNovamenuSelectWorld(this));
			break;
		case 1:
			mc.displayGuiScreen(new GuiNovamenuMultiplayer(this));
			break;
		case 2:
			if(NovamenuPlugin.getConfiguration().useCustomOptionsMenu) {
				mc.displayGuiScreen(new GuiNovamenuOptions(this, this.mc.gameSettings));
			}
			else {
				mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
			}
			break;
		case 3:
			mc.displayGuiScreen(new GuiNovamenuModList(this));
			break;
		case 4:
			mc.shutdown();
			break;
		}
	}
	
	@Override
	public void drawScreenForeground(int mouseX, int mouseY, float tick) {
		this.drawRect(0, 0, 150, height, 0x88000000);
		this.drawRect(150, 0, 151, height, 0xAA000000);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/mods/novamenu/textures/gui/minecraft.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexture(22, 22, 106, 16);
		this.drawCenteredString(mc.fontRenderer, "Minecraft "+NovamenuPlugin.minecraftVersion, 75, height - 22, Color.GRAY.getRGB());
		super.drawScreenForeground(mouseX, mouseY, tick);
	}
	
}
