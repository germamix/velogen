package ws.michalski.test.velogen;

import static org.junit.Assert.*;

import org.junit.Test;

import ws.michalski.velogen.VeloGenCmd;

public class TestVeloCmd {

	@Test
	public void test01() {
		
		VeloGenCmd cmd = new VeloGenCmd();
		
		
		CmdA a = new CmdA();
		CmdB b = new CmdB();
		
	
		
//		cmd.addCommand("cmda",a);
//		cmd.addCommand("cmdb", b);
//		
////		String[] parm = {"-tp /root"};
////		String[] parm = {"-s", "-tp", "/root"};
////		cmd.parse(parm);
//		cmd.usage();
//		System.out.println("...............................");
//		cmd.usage("cmda");
//		System.out.println(cmd.silent);
//		System.out.println(cmd.templatePath);
//		
//		
//		String[] parm2 = {"-s", "-tp", "/root", "cmda", "-k", "value_for_k"};
//		try {
//			cmd.parse(parm2);
//		} catch (com.beust.jcommander.ParameterException e) {
//			System.out.println(e.getMessage());
////			e.printStackTrace();
//		}
//		
//		System.out.println(cmd.jc.getCommandDescription("cmda"));
//		System.out.println(a.getKey());
	}

}
