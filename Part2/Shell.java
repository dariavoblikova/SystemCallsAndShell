import java.util.HashSet;

class Shell extends Thread{
	
	public Shell() {}
	public Shell(String[] args) {}
	
	public void run() {
        int command_number = 1;
        String commands = "";
		while(commands.compareTo("exit") != 0) { // check for exit command to quit shell
            SysLib.cout("Shell[" + command_number + "]% ");
            StringBuffer buffer = new StringBuffer();
			SysLib.cin(buffer);
			commands = new String(buffer);
			if (commands.length() != 0) {
                command_number++;
                String[] split_commands = commands.split(";"); // parse commands to sequential execution commands
				for(String command : split_commands) {
                    concurrent_execution(command);
                }
			}
        }
        SysLib.exit();
	}
	
	public void concurrent_execution(String command) { // command could be one or several concurrent(& separated)
		HashSet<Integer> tids = new HashSet<Integer>();
        int tid;
        String[] split_command = command.split("&");
		for (String cmd_line: split_command) {
			String[] args = SysLib.stringToArgs(cmd_line);
			if (args.length == 0) { // empty command line
                continue;
            }
            SysLib.cout(args[0]); // to match the output in initial Shell.class
            SysLib.cout( "\n" );
			tid = SysLib.exec(args); // returns tid if successful
			if (tid != -1) {
                tids.add(tid);
            }
		}
		while(tids.size() != 0) {
            tid = SysLib.join(); // returns tid of the child thread that has woken up the calling thread
            if (tid != -1) {
                if (tids.contains(tid)) {
                    tids.remove(tid);
                }
            }
		}
	}
}