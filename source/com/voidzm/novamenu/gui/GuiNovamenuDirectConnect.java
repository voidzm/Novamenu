package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Keyboard;

public class GuiNovamenuDirectConnect extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	
	private ServerData serverData;
	private GuiTextField serverAddress;
	
	public GuiNovamenuDirectConnect(GuiNovamenuScreen par1GuiScreen, ServerData par2ServerData) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		this.serverData = par2ServerData;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.serverAddress.updateCursorCounter();
	}

	@Override
	public void initGui() {
		StringTranslate t = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 16, 0, t.translateKey("selectServer.select")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 16, 1, t.translateKey("gui.cancel")));
		this.serverAddress = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 116, 200, 20);
		this.serverAddress.setMaxStringLength(128);
		this.serverAddress.setFocused(true);
		this.serverAddress.setText(this.mc.gameSettings.lastServer);
		((GuiButtonTransparent)this.buttons.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		this.mc.gameSettings.lastServer = this.serverAddress.getText();
		this.mc.gameSettings.saveOptions();
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.serverData.serverIP = this.serverAddress.getText();
			this.parent.imageTick = this.imageTick;
			this.parent.confirmClicked(true, 0);
			break;
		case 1:
			this.parent.imageTick = this.imageTick;
			this.parent.confirmClicked(false, 0);
			break;
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (this.serverAddress.textboxKeyTyped(par1, par2)){
			((GuiButtonTransparent)this.buttons.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0;
		}
		else if(par2 == 28) {
			this.buttonEvent(0);
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.serverAddress.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		StringTranslate t = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, t.translateKey("selectServer.direct"), this.width / 2, this.height / 4 - 60 + 20, 16777215);
		this.drawString(this.fontRenderer, t.translateKey("addServer.enterIp"), this.width / 2 - 100, 100, 10526880);
		this.serverAddress.drawTextBox();
	}

}
