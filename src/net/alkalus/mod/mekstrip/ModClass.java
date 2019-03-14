package net.alkalus.mod.mekstrip;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.7.10")
@Mod(modid = CORE.MODID, name = CORE.NAME, version = CORE.VERSION, dependencies = "required-after:Forge; after:Mekanism;")
public class ModClass {

	public boolean Mekanism = false;	

	public static enum INIT_PHASE {
		SUPER(null), PRE_INIT(SUPER), INIT(PRE_INIT), POST_INIT(INIT), SERVER_START(POST_INIT), STARTED(SERVER_START);
		protected boolean mIsPhaseActive = false;
		private final INIT_PHASE mPrev;

		private INIT_PHASE(INIT_PHASE aPreviousPhase) {
			mPrev = aPreviousPhase;
		}

		public synchronized final boolean isPhaseActive() {
			return mIsPhaseActive;
		}

		public synchronized final void setPhaseActive(boolean aIsPhaseActive) {
			if (mPrev != null && mPrev.isPhaseActive()) {
				mPrev.setPhaseActive(false);
			}
			mIsPhaseActive = aIsPhaseActive;
			if (CURRENT_LOAD_PHASE != this) {
				CURRENT_LOAD_PHASE = this;
			}
		}
	}

	public static INIT_PHASE CURRENT_LOAD_PHASE = INIT_PHASE.SUPER;

	// Mod Instance
	@Mod.Instance(CORE.MODID)
	public static ModClass instance;

	// GT++ Proxy Instances
	@SidedProxy(clientSide = "net.alkalus.mod.mekstrip.CommonProxy.ClientProxy", serverSide = "net.alkalus.mod.mekstrip.CommonProxy")
	public static CommonProxy proxy;

	public ModClass() {
		super();
		INIT_PHASE.SUPER.setPhaseActive(true);
	}

	// Pre-Init
	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		INIT_PHASE.PRE_INIT.setPhaseActive(true);		

		if (Loader.isModLoaded("Mekanism")){
			Mekanism  = true;
			System.out.println("Components enabled for: Mekanism - This feature is not configurable.");			
		}
		
		System.out.println("Loading " + CORE.NAME + " " + CORE.VERSION + ".");
		proxy.preInit(event);
	}

	// Init
	@Mod.EventHandler
	public void init(final FMLInitializationEvent event) {
		INIT_PHASE.INIT.setPhaseActive(true);
		proxy.init(event);
		proxy.registerNetworkStuff();
	}

	// Post-Init
	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent event) {
		INIT_PHASE.POST_INIT.setPhaseActive(true);
		proxy.postInit(event);
		System.out.println("Finally, we are finished. Have some cripsy bacon as a reward.");
	}

	@EventHandler
	public synchronized void serverStarting(final FMLServerStartingEvent event) {
		INIT_PHASE.SERVER_START.setPhaseActive(true);
	}

	@Mod.EventHandler
	public synchronized void serverStopping(final FMLServerStoppingEvent event) {

	}

	/**
	 * This {@link EventHandler} is called after the
	 * {@link FMLPostInitializationEvent} stages of all loaded mods executes
	 * successfully. {@link #onLoadComplete(FMLLoadCompleteEvent)} exists to inject
	 * recipe generation after Gregtech and all other mods are entirely loaded and
	 * initialized.
	 * 
	 * @param event - The {@link EventHandler} object passed through from FML to
	 *              {@link #ModClass()}'s {@link #instance}.
	 */
	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		INIT_PHASE.STARTED.setPhaseActive(true);
		proxy.onLoadComplete(event);
	}

}
