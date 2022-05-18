# Notebooks

Here are the notebook kernel configurations used:

* Linux
    
    After running `Apps/assembly`, copy `libjnidispatch.so` from a subdirectory of `./target/streams/_global/assemblyOption/_global/streams/assembly` to the `jna.boot.library` directory.

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


* Windows
