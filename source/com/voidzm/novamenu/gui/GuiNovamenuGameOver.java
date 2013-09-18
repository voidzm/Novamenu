package com.voidzm.novamenu.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiNovamenuGameOver extends GuiNovamenuScreen {

	public GuiNovamenuGameOver() {
		super();
		System.out.println("Created.");
	}

	@Override
	public void initGui() {
		this.buttons.clear();
		if(this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
			if(this.mc.isIntegratedServerRunning()) {
				this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 205, this.height - 24, 200, 16, 1, StatCollector.translateToLocal("deathScreen.deleteWorld")));
			}
			else {
				this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 24, 200, 16, 1, StatCollector.translateToLocal("deathScreen.leaveServer")));
			}
		}
		else {
			this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 205, this.height - 40, 200, 16, 1, StatCollector.translateToLocal("deathScreen.respawn")));
			this.buttons.add(new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 40, 200, 16, 2, StatCollector.translateToLocal("deathScreen.titleScreen")));
			if(this.mc.getSession() == null) {
				this.buttons.get(1).enabled = false;
			}
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 1:
				this.mc.thePlayer.respawnPlayer();
				this.mc.displayGuiScreen((GuiScreen)null);
				break;
			case 2:
				this.mc.theWorld.sendQuittingDisconnectingPacket();
				this.mc.loadWorld((WorldClient)null);
				this.mc.displayGuiScreen(new GuiNovamenuMainMenu());
				break;
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawRect(0, 0, width, 64, 0xBB080000);
		this.drawRect(0, 64, width, 65, 0xDD080000);
		this.drawRect(0, 65, width, height - 65, 0x66080000);
		this.drawRect(0, height - 65, width, height - 64, 0xDD080000);
		this.drawRect(0, height - 64, width, height, 0xBB080000);
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		boolean flag = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
		String s = flag ? StatCollector.translateToLocal("deathScreen.title.hardcore") : StatCollector.translateToLocal("deathScreen.title");
		this.drawCenteredString(this.fontRenderer, s, this.width / 2 / 2, 13, 16777215);
		GL11.glPopMatrix();
		if(flag) {
			this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("deathScreen.hardcoreInfo"), this.width / 2, this.height / 2 - 24, 16777215);
		}
		this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("deathScreen.score") + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2, this.height / 2 - 8, 16777215);
		for(GuiButtonTransparent iterated : buttons) {
			iterated.draw(par1, par2);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if(this.mc.thePlayer.getHealth() > 0.0F) {
			this.mc.displayGuiScreen((GuiScreen)null);
		}
	}

}
