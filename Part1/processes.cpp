#include <sys/types.h> // for fork, wait
#include <unistd.h> // for fork, execlp, pipe, dup2, close
#include <sys/wait.h> // for wait
#include <iostream> // for cout, endl
#include <stdlib.h> // for exit
#include <stdio.h> // for perror


using namespace std;

int main(int argc, char *argv[]) {
    
    enum {READ, WRITE};
    int pipeFD1[2]; // pipe1 between ps and grep
    int pipeFD2[2]; // pipe2 between grep and wc
    pid_t pid;

    if (argc != 2) {
        perror("Error: please try again and provide one argument (for grep)");
        exit(EXIT_FAILURE);
    }
    pid = fork(); // created a child

    if (pipe(pipeFD1) < 0) { // created pipe1
        perror("Error in creating pipe1");
        exit(EXIT_FAILURE);
    }

   if (pipe(pipeFD2) < 0) { // created pipe2
      perror("Error in creating pipe2");
      exit(EXIT_FAILURE);
    }

    if (pid < 0) {
        perror("Error during fork");
        exit(EXIT_FAILURE);
    }

    if (pid == 0) { // Child space
        pid = fork(); // created a grand child

        if (pid < 0) {
            perror("Error during fork");
            exit(EXIT_FAILURE);
        }

        if (pid == 0) { // Grand Child space

            pid = fork(); // created a great grand child

            if (pid < 0) {
                perror("Error during fork");
                exit(EXIT_FAILURE);
            }

            if (pid == 0) { // Great Grand Child
                close(pipeFD2[READ]);
                close(pipeFD2[WRITE]);
                close(pipeFD1[READ]);
                dup2(pipeFD1[WRITE], 1); // stdout (1) is now great grand child's write pipe (pipe1)
                int rc = execlp("/bin/ps", "ps", "-A", NULL);
                if (rc == -1) {
                    cerr << "Error on execlp of ps -A" << endl;
                }
            }
            else { // Grand Child
                close(pipeFD2[READ]);
                close(pipeFD1[WRITE]);
                dup2(pipeFD1[READ], 0); // stdin (0) is now grand child's read pipe (pipe1)
                dup2(pipeFD2[WRITE], 1); // stdout(1) is now grand child's write pipe (pipe2)
                int rc = execlp("/bin/grep", "grep", argv[1], NULL);
                if (rc == -1) {
                    cerr << "Error on execlp of grep argv[1]" << endl;
                }
            }
        }

        else { // Child
            close(pipeFD1[READ]);
            close(pipeFD1[WRITE]);
            close(pipeFD2[WRITE]);
            dup2(pipeFD2[READ], 0); // stdin (0) is now child's read pipe (pipe2)
            int rc = execlp("/usr/bin/wc", "wc", "-l", NULL);
            if (rc == -1) {
                cerr << "Error on execlp of wc -l" << endl;
            }
        }
    }

    else {
        wait(NULL);
    }

    exit(EXIT_SUCCESS);

   }

   

    
   