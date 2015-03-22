package ws.michalski.test.velogen;

import java.util.Map;

import org.junit.Test;

import ws.michalski.velogen.VeloGenEngine;
import ws.michalski.velogen.VeloGenManager;
import ws.michalski.velogen.exceptions.VeloGenCommandException;

public class TestVelo {

	@Test
	public void test() {
		
		String[] parameter = {"-tp", "/root", "cmda", "-k", "value_for_k"};
		
		VeloGenManager manager = new VeloGenManager();
		
		try {
			manager.getCommands(parameter);
		} catch (VeloGenCommandException e1) {
			manager.getUsage();
		}
		Map<String, Object> ctx2 = manager.run();
		
//		System.out.println(ctx2.size());
		
		try {
			
			VeloGenEngine engine = manager.getVelogenEngine();
			
			engine.mergeContext(ctx2);
			
			
			String erg = engine.generate("src/test/resources/test.vm");
			
			System.out.println(erg);
			
			manager.getUsage();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
