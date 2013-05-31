package com.voidzm.novamenu.gui;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Mouse;

public abstract class GuiNovamenuStatsSlot extends GuiNovamenuSlot {

	public int intG;
	public List list;
	public Comparator comparator;
	public int intJ;
	public int intK;

	private GuiNovamenuStats parent;

	protected GuiNovamenuStatsSlot(GuiNovamenuStats par1GuiStats) {
		super(par1GuiStats.getMinecraft(), par1GuiStats.width, par1GuiStats.height, 40, par1GuiStats.height - 72, 20);
		this.parent = par1GuiStats;
		this.intG = -1;
		this.intJ = -1;
		this.intK = 0;
		this.setShowSelectionBox(false);
		this.func_77223_a(true, 20);
	}

	@Override
	protected final int getSize() {
		return this.list.size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {}

	@Override
	protected boolean isSelected(int i) {
		return false;
	}

	protected void func_77222_a(int par1, int par2, Tessellator par3Tessellator) {
		if(!Mouse.isButtonDown(0)) {
			this.intG = -1;
		}
		if(this.intG == 0) {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 115 - 18, par2 + 1, 0, 0);
		}
		else {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 115 - 18, par2 + 1, 0, 18);
		}
		if(this.intG == 1) {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 165 - 18, par2 + 1, 0, 0);
		}
		else {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 165 - 18, par2 + 1, 0, 18);
		}
		if(this.intG == 2) {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 215 - 18, par2 + 1, 0, 0);
		}
		else {
			GuiNovamenuStats.drawSprite(this.parent, par1 + 215 - 18, par2 + 1, 0, 18);
		}
		if(this.intJ != -1) {
			short short1 = 79;
			byte b0 = 18;
			if(this.intJ == 1) {
				short1 = 129;
			}
			else if(this.intJ == 2) {
				short1 = 179;
			}
			if(this.intK == 1) {
				b0 = 36;
			}
			GuiNovamenuStats.drawSprite(this.parent, par1 + short1, par2 + 1, b0, 0);
		}
	}

	protected void func_77224_a(int par1, int par2) {
		this.intG = -1;
		if(par1 >= 79 && par1 < 115) {
			this.intG = 0;
		}
		else if(par1 >= 129 && par1 < 165) {
			this.intG = 1;
		}
		else if(par1 >= 179 && par1 < 215) {
			this.intG = 2;
		}
		if(this.intG >= 0) {
			this.func_77261_e(this.intG);
			this.parent.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
		}
	}

	protected final StatCrafting func_77257_d(int par1) {
		return (StatCrafting)this.list.get(par1);
	}

	protected abstract String func_77258_c(int i);

	protected void func_77260_a(StatCrafting par1StatCrafting, int par2, int par3, boolean par4) {
		String s;
		if(par1StatCrafting != null) {
			s = par1StatCrafting.func_75968_a(GuiNovamenuStats.getStatsFileWriter(this.parent).writeStat(par1StatCrafting));
			this.parent.drawString(this.parent.getFontRenderer(), s, par2 - this.parent.getFontRenderer().getStringWidth(s), par3 + 5, par4 ? 16777215 : 9474192);
		}
		else {
			s = "-";
			this.parent.drawString(this.parent.getFontRenderer(), s, par2 - this.parent.getFontRenderer().getStringWidth(s), par3 + 5, par4 ? 16777215 : 9474192);
		}
	}

	protected void func_77215_b(int par1, int par2) {
		if(par2 >= this.top && par2 <= this.bottom) {
			int k = this.func_77210_c(par1, par2);
			int l = this.parent.width / 2 - 92 - 16;
			if(k >= 0) {
				if(par1 < l + 40 || par1 > l + 40 + 20) {
					return;
				}
				StatCrafting statcrafting = this.func_77257_d(k);
				this.func_77259_a(statcrafting, par1, par2);
			}
			else {
				String s = "";
				if(par1 >= l + 115 - 18 && par1 <= l + 115) {
					s = this.func_77258_c(0);
				}
				else if(par1 >= l + 165 - 18 && par1 <= l + 165) {
					s = this.func_77258_c(1);
				}
				else {
					if(par1 < l + 215 - 18 || par1 > l + 215) {
						return;
					}
					s = this.func_77258_c(2);
				}
				s = ("" + StringTranslate.getInstance().translateKey(s)).trim();
				if(s.length() > 0) {
					int i1 = par1 + 12;
					int j1 = par2 - 12;
					int k1 = this.parent.getFontRenderer().getStringWidth(s);
					GuiNovamenuStats.drawGradientRect(this.parent, i1 - 3, j1 - 3, i1 + k1 + 3, j1 + 8 + 3, -1073741824, -1073741824);
					this.parent.getFontRenderer().drawStringWithShadow(s, i1, j1, -1);
				}
			}
		}
	}

	protected void func_77259_a(StatCrafting par1StatCrafting, int par2, int par3) {
		if(par1StatCrafting != null) {
			Item item = Item.itemsList[par1StatCrafting.getItemID()];
			String s = ("" + StringTranslate.getInstance().translateNamedKey(item.getUnlocalizedName())).trim();
			if(s.length() > 0) {
				int k = par2 + 12;
				int l = par3 - 12;
				int i1 = this.parent.getFontRenderer().getStringWidth(s);
				GuiNovamenuStats.drawGradientRect(this.parent, k - 3, l - 3, k + i1 + 3, l + 8 + 3, -1073741824, -1073741824);
				this.parent.getFontRenderer().drawStringWithShadow(s, k, l, -1);
			}
		}
	}

	protected void func_77261_e(int par1) {
		if(par1 != this.intJ) {
			this.intJ = par1;
			this.intK = -1;
		}
		else if (this.intK == -1)
		{
			this.intK = 1;
		}
		else
		{
			this.intJ = -1;
			this.intK = 0;
		}
		Collections.sort(this.list, this.comparator);
	}

}
