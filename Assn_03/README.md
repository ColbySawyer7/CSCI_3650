# ====================================================================================
#      Assignment 03 - Kosaraju's Algorithm
# ====================================================================================
# by: Colby Sawyer- sawyerc17@students.ecu.edu
# B01204512
# ====================================================================================
# ====================================================================================

# Running program in xlogin system:
python2 assn03.py

# Assumptions:
ASSUMES THAT VERTICES ARE NAMED 0 to n-1 (OTHER STRING NAMES FOR VERTICES WONT WORK)
Input from stdin is as follows:
    number of vertices
    number of edges
    vert1 vert2
    vert2 vert3
    vert4 vert3

ex.
    4
    4
    0 1
    0 2
    0 3
    2 3

    Yields Graph
    0 --> 1
    0 --> 2
    0 --> 3
    2 --> 3
