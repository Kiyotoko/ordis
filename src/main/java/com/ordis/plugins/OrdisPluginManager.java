package com.ordis.plugins;

import org.pf4j.DefaultPluginManager;

public class OrdisPluginManager extends DefaultPluginManager {

	@Override
	protected OrdisPluginFactory createPluginFactory() {
		return new OrdisPluginFactory();
	}

}
