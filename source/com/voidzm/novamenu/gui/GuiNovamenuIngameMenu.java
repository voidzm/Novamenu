package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;

public class GuiNovamenuIngameMenu extends GuiNovamenuScreen {

	@Override
	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, 10, this.height - 48, 180, 16, 1, StatCollector.translateToLocal("menu.returnToMenu")));
		if(!this.mc.isIntegratedServerRunning()) {
			((GuiButtonTransparent)this.buttons.get(0)).text = StatCollector.translateToLocal("menu.disconnect");
		}
		this.buttons.add(new GuiButtonTransparent(this, 10, 48, 180, 16, 4, StatCollector.translateToLocal("menu.returnToGame")));
		this.buttons.add(new GuiButtonTransparent(this, 10, 70, 180, 16, 0, StatCollector.translateToLocal("menu.options")));
		GuiButtonTransparent guibutton;
		this.buttons.add(guibutton = new GuiButtonTransparent(this, 10, 136, 180, 16, 7, StatCollector.translateToLocal("menu.shareToLan")));
		this.buttons.add(new GuiButtonTransparent(this, 10, 92, 180, 16, 5, StatCollector.translateToLocal("gui.achievements")));
		this.buttons.add(new GuiButtonTransparent(this, 10, 114, 180, 16, 6, StatCollector.translateToLocal("gui.stats")));
		guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 0:
				this.mc.displayGuiScreen(new GuiNovamenuOptions(this, this.mc.gameSettings));
				break;
			case 1:
				this.buttons.get(0).enabled = false;
				this.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
				this.mc.theWorld.sendQuittingDisconnectingPacket();
				this.mc.loadWorld((WorldClient)null);
				this.mc.displayGuiScreen(new GuiNovamenuMainMenu());
			case 2:
			case 3:
			default:
				break;
			case 4:
				this.mc.displayGuiScreen((GuiScreen)null);
				this.mc.setIngameFocus();
				this.mc.sndManager.resumeAllSounds();
				break;
			case 5:
				this.mc.displayGuiScreen(new GuiNovamenuAchievements(this.mc.statFileWriter));
				break;
			case 6:
				this.mc.displayGuiScreen(new GuiNovamenuStats(this, this.mc.statFileWriter));
				break;
			case 7:
				this.mc.displayGuiScreen(new GuiNovamenuOpenToLAN(this));
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawRect(0, 0, 200, height, 0xAA000000);
		this.drawRect(200, 0, 201, height, 0xDD000000);
		for(GuiButtonTransparent iterated : buttons) {
			iterated.draw(par1, par2);
		}
		this.drawCenteredString(this.fontRenderer, "Game menu", 100, 20, 13421772);
	}

}
