package com.voidzm.novamenu.gui;

import java.util.ArrayList;

import net.minecraft.client.resources.I18n;

import com.google.common.base.Strings;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class GuiNovamenuModList extends GuiNovamenuScreen {

	private GuiNovamenuScreen parent;
	private GuiNovamenuModSlot modSlot;
	private int selected = -1;
	private ModContainer selectedMod;
	private ArrayList<ModContainer> mods;

	private GuiButtonTransparent buttonSelect;

	public GuiNovamenuModList(GuiNovamenuScreen screen) {
		this.parent = screen;
		this.imageTick = screen.imageTick;
		this.mods = new ArrayList<ModContainer>();
		FMLClientHandler.instance().addSpecialModEntries(mods);
		for(ModContainer mod : Loader.instance().getModList()) {
			if(mod.getMetadata() != null && mod.getMetadata().parentMod == null && !Strings.isNullOrEmpty(mod.getMetadata().parent)) {
				String parentMod = mod.getMetadata().parent;
				ModContainer parentContainer = Loader.instance().getIndexedModList().get(parentMod);
				if(parentContainer != null) {
					mod.getMetadata().parentMod = parentContainer;
					parentContainer.getMetadata().childMods.add(mod);
					continue;
				}
			}
			else if(mod.getMetadata() != null && mod.getMetadata().parentMod != null) {
				continue;
			}
			mods.add(mod);
		}
	}

	@Override
	public void initGui() {
		this.buttons.clear();
		this.buttons.add(new GuiButtonTransparent(this, this.width / 2 - 155, this.height - 33, 150, 16, 0, I18n.getString("gui.done")));
		this.buttons.add(buttonSelect = new GuiButtonTransparent(this, this.width / 2 + 5, this.height - 33, 150, 16, 1, "Select Mod"));
		this.buttonSelect.enabled = false;
		this.modSlot = new GuiNovamenuModSlot(this, mods);
		this.modSlot.boxshift = 0;
		this.modSlot.registerScrollButtons(this.buttons, 2, 3);
	}

	@Override
	public void buttonEvent(int id) {
		switch(id) {
			case 0:
				this.parent.imageTick = this.imageTick;
				this.mc.displayGuiScreen(this.parent);
				break;
			case 1:
				if(this.selectedMod != null) {
					this.mc.displayGuiScreen(new GuiModDetail(this, this.selectedMod));
				}
				break;
		}
	}

	public int drawLine(String line, int offset, int shifty) {
		int r = this.fontRenderer.drawString(line, offset, shifty, 0xd7edea);
		return shifty + 10;
	}

	@Override
	public void drawScreenForeground(int mx, int my, float tick) {
		this.drawRect(0, 0, width, 40, 0xBB000000);
		this.drawRect(0, 40, width, 41, 0xDD000000);
		this.drawRect(0, 41, width, height - 51, 0x88000000);
		this.drawRect(0, height - 51, width, height - 50, 0xDD000000);
		this.drawRect(0, height - 50, width, height, 0xBB000000);
		super.drawScreenForeground(mx, my, tick);
		this.modSlot.drawScreen(mx, my, tick);
		int size = this.modSlot.getSize();
		this.drawCenteredString(this.fontRenderer, size + " Mods", this.width / 2, 16, 0xFFFFFF);
	}

	public void selectModIndex(int var1) {
		this.selected = var1;
		if(var1 >= 0 && var1 <= mods.size()) {
			this.selectedMod = mods.get(selected);
			this.buttonSelect.enabled = true;
		}
		else {
			this.selectedMod = null;
			this.buttonSelect.enabled = false;
		}
	}

	public boolean modIndexSelected(int var1) {
		return var1 == selected;
	}

	public static GuiButtonTransparent getButtonSelect(GuiNovamenuModList list) {
		return list.buttonSelect;
	}

}
