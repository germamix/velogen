package ws.michalski.test.velogen;

import static org.junit.Assert.*;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import ws.michalski.velogen.VeloGenConfig;
import ws.michalski.velogen.config.Configuration;

public class TestVeloConfig {

	@Test
	public void test01() {
		
		Configuration config = VeloGenConfig.getInstance().loadConfig();
		
		VeloGenConfig vgc = VeloGenConfig.getInstance();
		
			
			System.out.println(SystemUtils.USER_DIR);
			System.out.println(SystemUtils.JAVA_CLASS_PATH);
			
		
	}

}
