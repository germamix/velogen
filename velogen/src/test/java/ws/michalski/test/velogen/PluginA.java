package ws.michalski.test.velogen;

import java.util.HashMap;
import java.util.Map;

import ws.michalski.velogen.interfaces.VeloGenPlugin;

public class PluginA extends VeloGenPlugin {

	private Object param;
	
	// Im Konstruktor immer die Parameter initialisieren
	public PluginA(){
		param = new CmdA();
	}
	
	@Override
	public Object getCommandParam() {
		return param;
	}

	@Override
	public Map<String, Object> run(Object parameter) {
		Map<String, Object> intContext = new HashMap<>();
		
		CmdA cmda = (CmdA) parameter;
		
		
		intContext.put("DB_P1", "Wert 1");
		intContext.put("DB_P2", cmda.getKey());
		
		
		return intContext;
	}

}
