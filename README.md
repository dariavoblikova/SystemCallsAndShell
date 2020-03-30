# SystemCallsAndShell
Operating Systems

1. processes.cpp is a program that searches how many processes whose name is given in argv[1] are running on the system where the program was invoked, simulating the behavior of  Linux `ps -A | grep argv[1] | wc -l` command. 

System calls used:

* fork()
* execlp() 
* pipe()
* dup2()
* wait()
* close()

2. Implemented Shell.java for ThreadOS, an operating system simulator in Java. Shell.java behaves as a shell command interpreter,  constantly checking for new commands. Every time it takes in a new command it parses it based on the ";" sign (each of such commands is sequential). Then it sends them for execution in order, one by one. Each sequential command can still contain concurrent commands so I first parse it by the “&” sign (each of such commands is concurrent). Then I execute every concurrent command and if there is more than one I join them (wait for child threads to be terminated). 
