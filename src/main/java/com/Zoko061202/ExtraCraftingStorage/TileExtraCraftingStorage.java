package com.Zoko061202.ExtraCraftingStorage;

import appeng.block.crafting.BlockCraftingUnit;
import appeng.tile.crafting.TileCraftingStorageTile;

public class TileExtraCraftingStorage extends TileCraftingStorageTile {

	@Override
	public int getStorageBytes() {
		if(world == null || notLoaded() || isInvalid()) return 0;

		BlockCraftingUnit unit = (BlockCraftingUnit) world.getBlockState(pos).getBlock();
		switch(unit.type){
			default:
			case STORAGE_1K:
				return 256*1024;
			case STORAGE_4K:
				return 1024*1024;
			case STORAGE_16K:
				return 4096* 1024;
			case STORAGE_64K:
				return 16384* 1024;
		}
	}

}
