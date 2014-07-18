package com.voidzm.novamenu.gui;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.ResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepositoryEntry;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiNovamenuResourcePackSlot extends GuiNovamenuSlot {

	private GuiNovamenuResourcePacks parent;

	private final ResourcePackRepository rsrcPackRepo;
	private ResourceLocation rsrcLoc;

	public GuiNovamenuResourcePackSlot(GuiNovamenuResourcePacks par1, ResourcePackRepository par2) {
		super(GuiNovamenuResourcePacks.fetchMinecraft(par1), par1.width, par1.height, 32, par1.height - 51, 36);
		this.parent = par1;
		this.rsrcPackRepo = par2;
		this.rsrcPackRepo.updateRepositoryEntriesAll();
	}

	@Override
	protected int getSize() {
		return 1 + this.rsrcPackRepo.getRepositoryEntriesAll().size();
	}

	@Override
	protected void elementClicked(int par1, boolean par2) {
		List list = this.rsrcPackRepo.getRepositoryEntriesAll();
		try {
			if(par1 == 0) {
				throw new RuntimeException("This is so horrible ;D");
			}
			this.rsrcPackRepo.setRepositoryEntries(new ResourcePackRepositoryEntry[] {(ResourcePackRepositoryEntry)list.get(par1 - 1)});
			GuiNovamenuResourcePacks.fetchMinecraft(this.parent).refreshResources();
		}
		catch(Exception exception) {
			this.rsrcPackRepo.setRepositoryEntries(new ResourcePackRepositoryEntry[0]);
			GuiNovamenuResourcePacks.fetchMinecraft(this.parent).refreshResources();
		}

		GuiNovamenuResourcePacks.fetchMinecraft(this.parent).gameSettings.skin = this.rsrcPackRepo.getResourcePackName();
		GuiNovamenuResourcePacks.fetchMinecraft(this.parent).gameSettings.saveOptions();
	}

	@Override
	protected boolean isSelected(int i) {
		List list = this.rsrcPackRepo.getRepositoryEntries();
		return i == 0 ? list.isEmpty() : list.contains(this.rsrcPackRepo.getRepositoryEntriesAll().get(i - 1));
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 36;
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator t) {
		/*ITexturePack itexturepack = (ITexturePack)GuiNovamenuTexturePacks.fetchMinecraft(this.parent).texturePackList.availableTexturePacks().get(par1);
		itexturepack.bindThumbnailTexture(GuiNovamenuTexturePacks.fetchMinecraft(this.parent).renderEngine);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		par5Tessellator.startDrawingQuads();
		par5Tessellator.setColorOpaque_I(16777215);
		par5Tessellator.addVertexWithUV((double)par2, (double)(par3 + par4), 0.0D, 0.0D, 1.0D);
		par5Tessellator.addVertexWithUV((double)(par2 + 32), (double)(par3 + par4), 0.0D, 1.0D, 1.0D);
		par5Tessellator.addVertexWithUV((double)(par2 + 32), (double)par3, 0.0D, 1.0D, 0.0D);
		par5Tessellator.addVertexWithUV((double)par2, (double)par3, 0.0D, 0.0D, 0.0D);
		par5Tessellator.draw();
		String s = itexturepack.getTexturePackFileName();
		if(!itexturepack.isCompatible()) {
			s = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("texturePack.incompatible") + " - " + s;
		}
		if(s.length() > 32) {
			s = s.substring(0, 32).trim() + "...";
		}
		this.parent.drawString(GuiNovamenuTexturePacks.fetchFontRenderer(this.parent), s, par2 + 32 + 2, par3 + 1, 16777215);
		this.parent.drawString(GuiNovamenuTexturePacks.fetchFontRenderer(this.parent), itexturepack.getFirstDescriptionLine(), par2 + 32 + 2, par3 + 12, 8421504);
		this.parent.drawString(GuiNovamenuTexturePacks.fetchFontRenderer(this.parent), itexturepack.getSecondDescriptionLine(), par2 + 32 + 2, par3 + 12 + 10, 8421504);*/
		TextureManager texturemanager = GuiNovamenuResourcePacks.fetchMinecraft(this.parent).getTextureManager();
		if(par1 == 0) {
			try {
				ResourcePack rp = this.rsrcPackRepo.rprDefaultResourcePack;
				PackMetadataSection pmds = (PackMetadataSection)rp.getPackMetadata(this.rsrcPackRepo.rprMetadataSerializer, "pack");
				if(this.rsrcLoc == null) {
					this.rsrcLoc = texturemanager.getDynamicTextureLocation("texturepackicon", new DynamicTexture(rp.getPackImage()));
				}
				texturemanager.bindTexture(this.rsrcLoc);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				t.startDrawingQuads();
				t.setColorOpaque_I(16777215);
				t.addVertexWithUV(par2, par3 + par4, 0.0D, 0.0D, 1.0D);
				t.addVertexWithUV(par2 + 32, par3 + par4, 0.0D, 1.0D, 1.0D);
				t.addVertexWithUV(par2 + 32, par3, 0.0D, 1.0D, 0.0D);
				t.addVertexWithUV(par2, par3, 0.0D, 0.0D, 0.0D);
				t.draw();
				this.parent.drawString(GuiNovamenuResourcePacks.fetchFontRenderer(this.parent), "Default", par2 + 32 + 2, par3 + 1, 16777215);
				this.parent.drawString(GuiNovamenuResourcePacks.fetchFontRenderer(this.parent), pmds.getPackDescription(), par2 + 32 + 2, par3 + 12 + 10, 8421504);
			}
			catch(IOException ioexception) {
				ioexception.printStackTrace();
			}
		}
		else {
			ResourcePackRepositoryEntry rpre = (ResourcePackRepositoryEntry)this.rsrcPackRepo.getRepositoryEntriesAll().get(par1 - 1);
			rpre.bindTexturePackIcon(texturemanager);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			t.startDrawingQuads();
			t.setColorOpaque_I(16777215);
			t.addVertexWithUV(par2, par3 + par4, 0.0D, 0.0D, 1.0D);
			t.addVertexWithUV(par2 + 32, par3 + par4, 0.0D, 1.0D, 1.0D);
			t.addVertexWithUV(par2 + 32, par3, 0.0D, 1.0D, 0.0D);
			t.addVertexWithUV(par2, par3, 0.0D, 0.0D, 0.0D);
			t.draw();
			String s = rpre.getResourcePackName();
			if(s.length() > 32) {
				s = s.substring(0, 32).trim() + "...";
			}
			this.parent.drawString(GuiNovamenuResourcePacks.fetchFontRenderer(this.parent), s, par2 + 32 + 2, par3 + 1, 16777215);
			List list = GuiNovamenuResourcePacks.fetchFontRenderer(this.parent).listFormattedStringToWidth(rpre.getTexturePackDescription(), 183);
			for(int i1 = 0; i1 < 2 && i1 < list.size(); ++i1) {
				this.parent.drawString(GuiNovamenuResourcePacks.fetchFontRenderer(this.parent), (String)list.get(i1), par2 + 32 + 2, par3 + 12 + 10 * i1, 8421504);
			}
		}
	}

	public static ResourcePackRepository fetchResourcePackRepository(GuiNovamenuResourcePackSlot par0) {
		return par0.rsrcPackRepo;
	}

}
