package com.voidzm.novamenu.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

import com.voidzm.novamenu.util.NovamenuFontUtils;

import scala.Array;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.ModContainer;

public class GuiModDetail extends GuiNovamenuScreen {

	private GuiNovamenuModList parent;
	private ModContainer mod;
	
	public GuiModDetail(GuiNovamenuModList screen, ModContainer detailedMod) {
		this.parent = screen;
		this.imageTick = screen.imageTick;
		this.mod = detailedMod;
	}
	
	@Override
	public void initGui() {
		StringTranslate t = StringTranslate.getInstance();
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 100, this.height - 33, 200, 16, 0, t.translateKey("gui.done")));
	}
	
	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 0:
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				GuiNovamenuModList.getButtonSelect(this.parent).enabled = true;
				break;
		}
	}
	
	@Override
	public void drawScreenForeground(int mx, int my, float tick) {
		this.drawRect(0, 0, width, height, 0xBB000000);
		super.drawScreenForeground(mx, my, tick);
		String logoFile = this.mod.getMetadata().logoFile;
		if(!logoFile.isEmpty()) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.renderEngine.bindTexture(logoFile);
			Dimension dim = TextureFXManager.instance().getTextureDimensions(logoFile);
			double scaleX = dim.width / 200.0;
			double scaleY = dim.height / 65.0;
			double scale = 1.0;
			if(scaleX > 1 || scaleY > 1) {
				scale = 1.0 / Math.max(scaleX, scaleY);
			}
			dim.width *= scale;
			dim.height *= scale;
			int top = 15;
			Tessellator t = Tessellator.instance;
			t.startDrawingQuads();
			t.addVertexWithUV((this.width / 2) - 100.0, top + dim.height, zLevel, 0, 1);
			t.addVertexWithUV((this.width / 2) - 100.0 + dim.width, top + dim.height, zLevel, 1, 1);
			t.addVertexWithUV((this.width / 2) - 100.0 + dim.width, top, zLevel, 1, 0);
			t.addVertexWithUV((this.width / 2) - 100.0, top, zLevel, 0, 0);
			t.draw();
			GL11.glDisable(GL11.GL_BLEND);
		}
		int offset = 5;
		this.drawCenteredString(this.fontRenderer, this.mod.getName(), this.width / 2, 82 + offset, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, String.format("Version: %s (%s)", this.mod.getDisplayVersion(), this.mod.getVersion()), this.width / 2, 97 + offset, 0xAAAAAA);
		this.drawCenteredString(this.fontRenderer, String.format("Credits: %s", this.mod.getMetadata().credits), this.width / 2, 112 + offset, 0xAAAAAA);
		this.drawCenteredString(this.fontRenderer, String.format("Authors: %s", this.mod.getMetadata().getAuthorList()), this.width / 2, 127 + offset, 0xAAAAAA);
		this.drawCenteredString(this.fontRenderer, String.format("Website: %s", this.mod.getMetadata().url), this.width / 2, 142 + offset, 0xAAAAAA);
		String desc = this.mod.getMetadata().description;
		String[] lines = NovamenuFontUtils.injectWrapping(desc, 400).split("\n");
		int i = 0;
		for(String string : lines) {
			this.getFontRenderer().drawString(string, this.width / 2 - 200, 157 + offset + i, 0xCCCCCC, true);
			i += 11;
		}
	}

}
