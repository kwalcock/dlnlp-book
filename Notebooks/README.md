# Notebooks

1. ScalaPy uses `JNA` which needs a `JNI` native library usually taken care of automatically if `sbt` or `java` runs the software from a `jar`, but that doesn't seem to work for a `Jupyter` notebook.  One workaround is to track down the library yourself.  Use `sbt` to run `Apps/assembly`, search `./target/streams/_global/assemblyOption/_global/streams/assembly` for the library that matches your operating system and architecture, then copy the file to a handy location to be identified by `jna.boot.library.path` in the `kernel.json` file of `Jupyter`.
    * Linux: `libjnidispatch.so`
    * Mac: `libjnidispatch.jnilib`.
    * Windows: `jnidispatch.dll`
   

2. Use `jupyter kernelspec list` to find out where your kernels are stored and in that directory edit `kernel.json` to include the `jna.boot.library.path` as well as other parameters to let `ScalaPy` know how Python is installed.  The latter were also necessary to run the `Apps` from `IntelliJ`.  Here are sample files for different operating systems:

    * Linux
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
    ```json
    {
      "argv": [
        "java",
        "-Xmx16g",
        "-Djna.boot.library.path=C:/Users/kwa/AppData/Roaming/jupyter/kernels",
        "-Djna.library.path=D:/ProgramFiles/Python39",
        "-Dscalapy.python.library=python39",
        "-jar",
        "C:\\Users\\kwa\\AppData\\Roaming\\jupyter\\kernels\\scala212\\launcher.jar",
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
