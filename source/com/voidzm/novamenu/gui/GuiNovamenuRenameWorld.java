//**
//**  GuiNovamenuRenameWorld.java
//**  Novamenu
//**  (c) voidzm 2013 **//

package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.input.Keyboard;

public class GuiNovamenuRenameWorld extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private GuiTextField textWorldName;
	private final String worldName;

	public GuiNovamenuRenameWorld(GuiNovamenuScreen par1GuiScreen, String par2Str) {
		this.parent = par1GuiScreen;
		this.worldName = par2Str;
		this.imageTick = par1GuiScreen.imageTick;
	}

	@Override
	public void updateScreen() {
		this.textWorldName.updateCursorCounter();
		super.updateScreen();
	}

	@Override
	public void initGui() {
		StringTranslate t = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 16, 0, t.translateKey("selectWorld.renameButton")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 16, 1, t.translateKey("gui.cancel")));
		ISaveFormat isaveformat = this.mc.getSaveLoader();
		WorldInfo worldinfo = isaveformat.getWorldInfo(this.worldName);
		String s = worldinfo.getWorldName();
		this.textWorldName = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 80, 200, 20);
		this.textWorldName.setFocused(true);
		this.textWorldName.setText(s);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			isaveformat.renameWorld(this.worldName, this.textWorldName.getText().trim());
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		case 1:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		}
	}

	@Override
	protected void keyTyped(char par1, int par2){
		this.textWorldName.textboxKeyTyped(par1, par2);
		((GuiButtonTransparent)this.buttons.get(0)).enabled = this.textWorldName.getText().trim().length() > 0;
		if(par1 == 13) {
			this.buttonEvent(0);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.textWorldName.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		StringTranslate t = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, t.translateKey("selectWorld.renameTitle"), this.width / 2, 40, 16777215);
		this.drawString(this.fontRenderer, t.translateKey("selectWorld.enterName"), this.width / 2 - 100, 67, 10526880);
		this.textWorldName.drawTextBox();
	}

}
