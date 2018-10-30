#!/bin/bash

imageID=scala/2.11.8

## Enter the docker
alias scaladocker='docker run --rm --tty --interactive --volume $PWD:/app ${imageID} bash'
## REPL
alias myscala='docker run --rm --tty --interactive --volume $PWD:/app ${imageID} scala'
## Compiler
alias myscalac='docker run --rm --tty --interactive --volume $PWD:/app ${imageID} scalac'
