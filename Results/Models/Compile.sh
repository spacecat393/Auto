#!/bin/bash

current_directory="$(dirname "$0")"
temp_directory="$current_directory"/Temp
vertex_directory=/Vertex/
folder_name_array=()

echo start

for item in "$current_directory"/*
do
    echo $(basename "$item")
    # Check if the item is a directory
    if [ -d "$item" ]; then
        # Extract the folder name from the item
        folder_name_array+=($(basename "$item"))
    fi
done

for name in "${folder_name_array[@]}"
do
    temp_vertex_directory="$temp_directory/$name/Shaders/$vertex_directory"
    mkdir -p "$temp_directory"
    mkdir -p "$temp_vertex_directory"
    cd "$temp_vertex_directory"
    glslangValidator -V ../../../../"$name"/Shaders/"$name".vert
    cd ../../../../
done

for name in "${folder_name_array[@]}"
do
    fragment_directory="$temp_directory/$name/Shaders/Fragment"
    mkdir -p "$fragment_directory"
    cd "$fragment_directory"
    glslangValidator -V ../../../../"$name"/Shaders/"$name".frag
    cd ../../../../
done
