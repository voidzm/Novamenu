package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Keyboard;

public class GuiNovamenuAddServer extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private GuiTextField serverAddress;
	private GuiTextField serverName;
	
	private ServerData newServerData;
	
	public GuiNovamenuAddServer(GuiNovamenuScreen par1GuiScreen, ServerData par2ServerData) {
		this.parent = par1GuiScreen;
		this.imageTick = par1GuiScreen.imageTick;
		this.newServerData = par2ServerData;
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.serverAddress.updateCursorCounter();
		this.serverName.updateCursorCounter();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 16, 0, I18n.func_135053_a("addServer.add")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 16, 1, I18n.func_135053_a("gui.cancel")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, 142, 200, 16, 2, I18n.func_135053_a("addServer.hideAddress") + ": " + (this.newServerData.isHidingAddress() ? I18n.func_135053_a("gui.yes") : I18n.func_135053_a("gui.no"))));
		this.serverName = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
		this.serverName.setFocused(true);
		this.serverName.setText(this.newServerData.serverName);
		this.serverAddress = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 106, 200, 20);
		this.serverAddress.setMaxStringLength(128);
		this.serverAddress.setText(this.newServerData.serverIP);
		((GuiButtonTransparent)this.buttons.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.newServerData.serverName = this.serverName.getText();
			this.newServerData.serverIP = this.serverAddress.getText();
			this.parent.imageTick = this.imageTick;
			this.parent.confirmClicked(true, 0);
			break;
		case 1:
			this.parent.imageTick = this.imageTick;
			this.parent.confirmClicked(false, 0);
			break;
		case 2:
			this.newServerData.setHideAddress(!this.newServerData.isHidingAddress());
			((GuiButtonTransparent)this.buttons.get(2)).text = I18n.func_135053_a("addServer.hideAddress") + ": " + (this.newServerData.isHidingAddress() ? I18n.func_135053_a("gui.yes") : I18n.func_135053_a("gui.no"));
			break;
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		this.serverName.textboxKeyTyped(par1, par2);
		this.serverAddress.textboxKeyTyped(par1, par2);
		if(par1 == 9) {
			if(this.serverName.isFocused()) {
				this.serverName.setFocused(false);
				this.serverAddress.setFocused(true);
			}
			else {
				this.serverName.setFocused(true);
				this.serverAddress.setFocused(false);
			}
		}
		if(par1 == 13) {
			this.buttonEvent(0);
		}
		((GuiButtonTransparent)this.buttons.get(0)).enabled = this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0;
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.serverAddress.mouseClicked(par1, par2, par3);
		this.serverName.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, I18n.func_135053_a("addServer.title"), this.width / 2, 17, 16777215);
		this.drawString(this.fontRenderer, I18n.func_135053_a("addServer.enterName"), this.width / 2 - 100, 53, 10526880);
		this.drawString(this.fontRenderer, I18n.func_135053_a("addServer.enterIp"), this.width / 2 - 100, 94, 10526880);
		this.serverName.drawTextBox();
		this.serverAddress.drawTextBox();
	}

}
