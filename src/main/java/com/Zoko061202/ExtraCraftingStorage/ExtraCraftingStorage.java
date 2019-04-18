package com.Zoko061202.ExtraCraftingStorage;

import static appeng.block.crafting.BlockCraftingUnit.CraftingUnitType.STORAGE_16K;
import static appeng.block.crafting.BlockCraftingUnit.CraftingUnitType.STORAGE_1K;
import static appeng.block.crafting.BlockCraftingUnit.CraftingUnitType.STORAGE_4K;
import static appeng.block.crafting.BlockCraftingUnit.CraftingUnitType.STORAGE_64K;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import appeng.api.definitions.ITileDefinition;
import appeng.block.crafting.ItemCraftingStorage;
import appeng.bootstrap.FeatureFactory;
import appeng.bootstrap.definitions.TileEntityDefinition;
import appeng.client.render.crafting.CraftingCubeRendering;
import appeng.core.AppEng;
import appeng.core.features.AEFeature;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExtraCraftingStorage.ModID, name=ExtraCraftingStorage.ModName, version=ExtraCraftingStorage.Version, dependencies=ExtraCraftingStorage.Dependencies)
@EventBusSubscriber(modid=ExtraCraftingStorage.ModID)
public class ExtraCraftingStorage {

	public static final String ModID = "extracraftingstorage";
	public static final String ModName = "Extra Crafting Storage";
	public static final String Version = "2.0";
	public static final String Dependencies = "required-after:appliedenergistics2";


	public static BlockEXCraftingStorage craftingStorage256k;
	public static BlockEXCraftingStorage craftingStorage1024k;
	public static BlockEXCraftingStorage craftingStorage4096k;
	public static BlockEXCraftingStorage craftingStorage16384k;

	  @EventHandler
	  public static void preInit(FMLPreInitializationEvent e) {
		 FeatureFactory crafting = new FeatureFactory().features(AEFeature.CRAFTING_CPU);

		 changeAEModID(ModID);
			craftingStorage256k = (BlockEXCraftingStorage) crafting.block("crafting_storage_ex_256k", () -> new BlockEXCraftingStorage(STORAGE_1K))
					.item(ItemCraftingStorage::new)
					.tileEntity(new TileEntityDefinition(TileExtraCraftingStorage.class))
					.rendering(new CraftingCubeRendering("crafting_storage_256k",STORAGE_1K))
					.useCustomItemModel()
					.build()
					.maybeBlock()
					.get();
			craftingStorage1024k = (BlockEXCraftingStorage) crafting.block("crafting_storage_ex_1024k", () -> new BlockEXCraftingStorage(STORAGE_4K))
					.item(ItemCraftingStorage::new)
					.tileEntity(new TileEntityDefinition(TileExtraCraftingStorage.class))
					.rendering( new CraftingCubeRendering("crafting_storage_1024k", STORAGE_4K))
					.useCustomItemModel()
					.build()
					.maybeBlock()
					.get();

			craftingStorage4096k = (BlockEXCraftingStorage) crafting.block("crafting_storage_ex_4096k", () -> new BlockEXCraftingStorage(STORAGE_16K))
					.item(ItemCraftingStorage::new )
					.tileEntity(new TileEntityDefinition(TileExtraCraftingStorage.class))
					.rendering( new CraftingCubeRendering("crafting_storage_4096k", STORAGE_16K))
					.useCustomItemModel()
					.build()
					.maybeBlock()
					.get();
			craftingStorage16384k = (BlockEXCraftingStorage) crafting.block("crafting_storage_ex_16384k", () -> new BlockEXCraftingStorage(STORAGE_64K))
					.item(ItemCraftingStorage::new )
					.tileEntity(new TileEntityDefinition(TileExtraCraftingStorage.class))
					.rendering( new CraftingCubeRendering("crafting_storage_16384k", STORAGE_64K))
					.useCustomItemModel()
					.build()
					.maybeBlock()
					.get();
			changeAEModID("appliedenergistics2");

	   GameRegistry.registerTileEntity(TileExtraCraftingStorage.class, new ResourceLocation(ModID, "TileExtraCraftingStorage"));
	   ModelLoaderRegistry.registerLoader(new ExtraModelLoader());
	  }

	  @SubscribeEvent
	  public static void registerBlocks(RegistryEvent.Register<Block> e){
       registerBlock(e, craftingStorage256k);
       registerBlock(e, craftingStorage1024k);
       registerBlock(e, craftingStorage4096k);
       registerBlock(e, craftingStorage16384k);
	  }

	  @SubscribeEvent
	  public static void registerItems(RegistryEvent.Register<Item> e){
	   registerItemBlock(e, craftingStorage256k);
	   registerItemBlock(e, craftingStorage1024k);
	   registerItemBlock(e, craftingStorage4096k);
	   registerItemBlock(e, craftingStorage16384k);
	  }

	  @SubscribeEvent
	  public static void registerRecipes(RegistryEvent.Register<IRecipe> e){

	  }

	  @SubscribeEvent
	  public static void registerModels(ModelRegistryEvent e){
		  registerRender(craftingStorage256k);
		  registerRender(craftingStorage1024k);
		  registerRender(craftingStorage4096k);
		  registerRender(craftingStorage16384k);
	  }

	  private static void registerBlock(RegistryEvent.Register<Block> e, Block b){
	   e.getRegistry().register(b);
	  }

	  private static void registerItemBlock(RegistryEvent.Register<Item> e, Block b){
		e.getRegistry().register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
	  }

	  private static void registerRender(Block b) {
	   ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation( Item.getItemFromBlock(b).getRegistryName(), "inventory"));
	  }

	  private static void changeAEModID(String name){
		  try {
			  Field f = AppEng.class.getDeclaredField("MOD_ID");
			  f.setAccessible(true);
	          Field modifiersField = Field.class.getDeclaredField("modifiers");
	                modifiersField.setAccessible( true );
	                modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			  f.set(null, ModID);
			  modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
		  }
		  catch(Exception ex) {
            ex.printStackTrace(System.out);
		  }
	  }

}
