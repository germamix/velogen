package ws.michalski.test.velogen;

import java.util.Map;

import ws.michalski.velogen.interfaces.VeloGenPlugin;

public class PluginB extends VeloGenPlugin {

	@Override
	public Object getCommandParam() {
		return new CmdB();
	}

	@Override
	public Map<String, Object> run(Object parameter) {
		return null;
	}

}
