#!/usr/bin/env python3

# intergration_tester.py
# This file is used to run automatically feed input into the game.
# This can make testing far faster as well as test the overall state of the game.
# To create a test file run through a game(in cl mode) with or without a seed and everytime you type an input into the game
# type the same input into a file with a newline character.
# You can specify the seed of a file to run on with 'seed <input_seed>' as the first line.

import sys
import os

if len(sys.argv) < 2:
	print("Usage: intergration_tester.py <test_file>")
	sys.exit(1)

file = sys.argv[1]
file_offset = 1

# Check for seed
seed = ''
file_header = ''
with open(file) as f:
    file_header = f.readline()

if file_header.split(' ')[0] == 'seed':
	seed = file_header.split(' ')[1]
	file_offset = 2
	print("Starting game with seed: {}".format(seed))
else:
	print("Starting game with random seed:")
print("\n")

# Currently set to the jar file however this can be switch to the class file aswell
os.system("(tail -n +{} '{}' && cat) | java -jar SpaceExplorer.jar cl {}".format(file_offset, file, seed))
