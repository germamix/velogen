package ws.michalski.test.velogen;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription="CmdA")
public class CmdA {

	@Parameter(description="key", names="-k", required=true)
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
