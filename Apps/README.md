# Apps

Several Python packages are required and might be installed with these commands:
 
* pip3 install torch
* pip3 install tqdm
* pip3 install sklearn
* pip3 install ipywidgets (for Notebook)


Remember to use JVM options similar to these:

* Linux:

    * -Djna.library.path=/usr/lib/python3.8/config-3.8-x86_64-linux-gnu:/usr/lib
    * -Dscalapy.python.library=python3.8


* Mac:

    * -Djna.library.path=/Library/Frameworks/Python.framework/Versions/3.10/lib/python3.10/config-3.10-darwin:/Library/Frameworks/Python.framework/Versions/3.10/lib
    * -Dscalapy.python.library=python3.10


* Windows:

    * -Djna.library.path=D:/ProgramFiles/Python39
    * -Dscalapy.python.library=python39"