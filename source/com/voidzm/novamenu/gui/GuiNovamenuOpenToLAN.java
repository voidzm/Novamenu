package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.EnumGameType;

public class GuiNovamenuOpenToLAN extends GuiNovamenuScreen {
	
	private GuiNovamenuScreen parent;
	private GuiButtonTransparent buttonAllowCommands;
	private GuiButtonTransparent buttonGamemode;
	
	private String gamemode = "survival";
	
	private boolean allowCommands = false;
	
	public GuiNovamenuOpenToLAN(GuiNovamenuScreen screen) {
		this.parent = screen;
	}

	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height - 28, 150, 16, 0, StatCollector.translateToLocal("lanServer.start")));
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 28, 150, 16, 1, StatCollector.translateToLocal("gui.cancel")));
		this.buttons.add(this.buttonGamemode = new GuiButtonTransparent(this, this.width / 2 - 155, 100, 150, 16, 2, StatCollector.translateToLocal("selectWorld.gameMode")));
		this.buttons.add(this.buttonAllowCommands = new GuiButtonTransparent(this, this.width / 2 + 5, 100, 150, 16, 3, StatCollector.translateToLocal("selectWorld.allowCommands")));
		this.setupButtons();
	}

	private void setupButtons() {
		StringTranslate t = StringTranslate.getInstance();
		this.buttonGamemode.text = t.translateKey("selectWorld.gameMode") + " " + t.translateKey("selectWorld.gameMode." + this.gamemode);
		this.buttonAllowCommands.text = t.translateKey("selectWorld.allowCommands") + " ";
		if(this.allowCommands) {
			this.buttonAllowCommands.text = this.buttonAllowCommands.text + t.translateKey("options.on");
		}
		else {
			this.buttonAllowCommands.text = this.buttonAllowCommands.text + t.translateKey("options.off");
		}
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 0:
				this.mc.displayGuiScreen((GuiScreen)null);
				String s = this.mc.getIntegratedServer().shareToLAN(EnumGameType.getByName(this.gamemode), this.allowCommands);
				String s1 = "";
				if(s != null) {
					s1 = this.mc.thePlayer.translateString("commands.publish.started", new Object[] {s});
				}
				else {
					s1 = this.mc.thePlayer.translateString("commands.publish.failed", new Object[0]);
				}
				this.mc.ingameGUI.getChatGUI().printChatMessage(s1);
				break;
			case 1:
				this.mc.displayGuiScreen(this.parent);
				break;
			case 2:
				if(this.gamemode.equals("survival")) {
					this.gamemode = "creative";
				}
				else if(this.gamemode.equals("creative")) {
					this.gamemode = "adventure";
				}
				else {
					this.gamemode = "survival";
				}
				this.setupButtons();
				break;
			case 3:
				this.allowCommands = !this.allowCommands;
				this.setupButtons();
				break;
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("lanServer.title"), this.width / 2, 50, 16777215);
		this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("lanServer.otherPlayers"), this.width / 2, 82, 16777215);
	}

}
