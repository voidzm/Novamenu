package com.voidzm.novamenu.asm;

import java.io.File;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.InvalidVersionSpecificationException;
import cpw.mods.fml.common.versioning.VersionRange;

public class NovamenuModContainer extends DummyModContainer {
	
	private ArtifactVersion processedVersion;
	
	@Override
	public String getModId() {
		return "Novamenu";
	}

	@Override
	public String getName() {
		return "Novamenu";
	}

	@Override
	public String getVersion() {
		return NovamenuPlugin.novamenuVersion;
	}

	@Override
	public File getSource() {
		return NovamenuPlugin.location;
	}

	@Override
	public ModMetadata getMetadata() {
		ModMetadata meta = new ModMetadata();
		meta.modId = "Novamenu";
		meta.name = "Novamenu";
		meta.version = NovamenuPlugin.novamenuVersion;
		meta.authorList = Arrays.asList("voidzm");
		meta.description = "A GUI mod that overhauls the Minecraft default GUI for a cleaner, more transparent look.";
		meta.credits = "Designed and coded by voidzm.";
		meta.url = "https://github.com/voidzm/Novamenu/";
		meta.logoFile = "/mods/novamenu/textures/gui/logo.png";
		return meta;
	}

	@Override
	public void bindMetadata(MetadataCollection mc) {}

	@Override
	public void setEnabledState(boolean enabled) {}

	@Override
	public Set<ArtifactVersion> getRequirements() {
		return Collections.emptySet();
	}

	@Override
	public List<ArtifactVersion> getDependencies() {
		return Collections.emptyList();
	}

	@Override
	public List<ArtifactVersion> getDependants() {
		return Collections.emptyList();
	}

	@Override
	public String getSortingRules() {
		return "";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Override
	public boolean matches(Object mod) {
		return false;
	}

	@Override
	public Object getMod() {
		return null;
	}

	@Override
	public ArtifactVersion getProcessedVersion() {
		if(processedVersion == null) {
			processedVersion = new DefaultArtifactVersion(getModId(), getVersion());
		}
		return processedVersion;
	}

	@Override
	public boolean isImmutable() {
		return false;
	}

	@Override
	public boolean isNetworkMod() {
		return false;
	}

	@Override
	public String getDisplayVersion() {
		return NovamenuPlugin.novamenuVersion;
	}

	@Override
	public VersionRange acceptableMinecraftVersionRange() {
		try {
			return VersionRange.createFromVersionSpec(NovamenuPlugin.minecraftVersion);
		} catch (InvalidVersionSpecificationException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Certificate getSigningCertificate() {
		return null;
	}

}
