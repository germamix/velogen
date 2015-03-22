package ws.michalski.test.velogen;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription="Plugin for database generate")
public class CmdB {

	@Parameter(description="logic B", names="-log")
	private boolean logB;

	public boolean isLogB() {
		return logB;
	}

	public void setLogB(boolean logB) {
		this.logB = logB;
	}
	
}
