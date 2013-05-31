package com.voidzm.novamenu.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.util.StringTranslate;

public class GuiNovamenuDisconnected extends GuiNovamenuScreen {

	private String errorMessage;
	private String errorDetail;
	private Object[] messageArray;
	private List messageList;
	private final GuiNovamenuScreen parent;

	public GuiNovamenuDisconnected(GuiNovamenuScreen par1GuiScreen, String par2Str, String par3Str, int tick, Object ... par4ArrayOfObj) {
		StringTranslate t = StringTranslate.getInstance();
		this.parent = par1GuiScreen;
		this.errorMessage = t.translateKey(par2Str);
		this.errorDetail = par3Str;
		this.messageArray = par4ArrayOfObj;
		this.imageTick = tick;
	}

	@Override
	protected void keyTyped(char par1, int par2) {}

	@Override
	public void initGui() {
		StringTranslate t = StringTranslate.getInstance();
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 16, 0, t.translateKey("gui.toMenu")));
		if(this.messageArray != null) {
			this.messageList = this.fontRenderer.listFormattedStringToWidth(t.translateKeyFormat(this.errorDetail, this.messageArray), this.width - 50);
		}
		else {
			this.messageList = this.fontRenderer.listFormattedStringToWidth(t.translateKey(this.errorDetail), this.width - 50);
		}
	}
	
	@Override
	public void buttonEvent(int id) {
		switch(id) {
		case 0:
			this.parent.imageTick = this.imageTick;
			this.mc.displayGuiScreen(this.parent);
			break;
		}
	}

	@Override
	public void drawScreenForeground(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 11184810);
		int k = this.height / 2 - 30;
		if(this.messageList != null) {
			for(Iterator iterator = this.messageList.iterator(); iterator.hasNext(); k += this.fontRenderer.FONT_HEIGHT) {
				String s = (String)iterator.next();
				this.drawCenteredString(this.fontRenderer, s, this.width / 2, k, 16777215);
			}
		}
	}

}
