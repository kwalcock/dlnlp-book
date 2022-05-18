# Notebooks

Here are the notebook kernel configurations used:

* Linux
    
    After running `Apps/assembly`, copy `libjnidispatch.so` from a subdirectory of `./target/streams/_global/assemblyOption/_global/streams/assembly` to the `jna.boot.library` directory.

    This `kernel.json` is from `~/.local/share/jupyter/kernels/scala212`.


    ```json
    {
      "argv": [
        "java",
        "-Djna.boot.library.path=/home/kwa/.local/share/jupyter/kernels/scala212",
        "-Djna.library.path=/usr/lib/python3.8/config-3.8-x86_64-linux-gnu:/usr/lib",
        "-Dscalapy.python.library=python3.8",
        "-jar",
        "/home/kwa/.local/share/jupyter/kernels/scala212/launcher.jar",
        "--id",
        "scala212",
        "--display-name",
        "Scala (2.12)",
        "--extra-repository",
        "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release",
         "--connection-file",
        "{connection_file}"
      ],
      "display_name": "Scala (2.12)",
      "language": "scala"
    }
   ```
  
* Mac

    

    After running `Apps/assembly`, copy `libjnidispatch.so` from a subdirectory of `./target/streams/_global/assemblyOption/_global/streams/assembly` to the `jna.boot.library` directory.

    This `kernel.json` is from `~/Library/Jupyter/kernels/scala212`.

    ```json
    {
      "argv": [
        "java",
        "-Djna.boot.library.path=/Users/kwa/Library/Jupyter/kernels/scala212",
        "-Djna.library.path=/Library/Frameworks/Python.framework/Versions/3.10/lib/python3.10/config-3.10-darwin:/Library/Frameworks/Python.framework/Versions/3.10/lib",
        "-Dscalapy.python.library=python3.10",
        "-jar",
        "/Users/kwa/Library/Jupyter/kernels/scala212/launcher.jar",
        "--id",
        "scala212",
        "--display-name",
        "Scala (2.12)",  
        "--extra-repository",
        "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release",
        "--connection-file",
        "{connection_file}" 
      ],
      "display_name": "Scala (2.12)",
      "language": "scala"
    }
    ```




* Windows
