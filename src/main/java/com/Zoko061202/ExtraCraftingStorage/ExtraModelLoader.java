package com.Zoko061202.ExtraCraftingStorage;

import appeng.block.crafting.BlockCraftingUnit.CraftingUnitType;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class ExtraModelLoader implements ICustomModelLoader {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		if(modelLocation.getResourceDomain() == "appliedenergistics2")
		 if(modelLocation.getResourcePath().contains("crafting_storage_ex"))
			 return true;
		return false;
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new ExtraStorageModel(CraftingUnitType.STORAGE_1K);
	}

}
