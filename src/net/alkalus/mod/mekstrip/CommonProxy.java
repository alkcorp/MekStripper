package net.alkalus.mod.mekstrip;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
	
	public static class ClientProxy extends CommonProxy {
		
	}

	public CommonProxy() {
	}

	public void preInit(final FMLPreInitializationEvent e) {

	}

	public void init(final FMLInitializationEvent e) {
	}

	public void postInit(final FMLPostInitializationEvent e) {
	}

	public void serverStarting(final FMLServerStartingEvent e) {
	}

	public void onLoadComplete(FMLLoadCompleteEvent event) {
	}

	public void registerNetworkStuff() {
	}

	public void registerRenderThings() {

	}

}
