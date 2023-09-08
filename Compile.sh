#!/bin/bash

results_directory=./Results
mkdir -p $results_directory
cd $results_directory

for item in ./../Shaders/*
do
    glslangValidator -V ./../Shaders/$(basename $item) -o $(basename $item)
done
