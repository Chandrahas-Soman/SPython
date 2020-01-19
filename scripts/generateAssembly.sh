#!/bin/bash

# Take Python code and generate it's assembly code using bytecode disassembler (dis)

python3 -m dis intermediateFiles/python/test.py > intermediateFiles/assembly/test.txt