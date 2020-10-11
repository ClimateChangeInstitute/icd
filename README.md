# icd

This is the original ice core dating software developed by Bashar
Abdul for the climate Change Institute, University of Maine. The latest
build was created by Mark Royer on October 8, 2020.

You can download the latest build (v1.1)
here:
[icd-1.1-20201011.jar](https://github.com/ClimateChangeInstitute/icd/releases/download/v1.1/icd-1.1-20201011.jar).

## Building

You can build the project using [http://maven.apache.org/](Maven).  From the root of the project directory structure typing

``` bash
mvn package
```

will generate two jar files in the `target` directory. For example,

``` bash
icd-1.1-SNAPSHOT.jar
icd-1.1-SNAPSHOT-jar-with-dependencies.jar
```

The first jar file requires the JFreechart dependencies, while the
second jar file is a standalone executable jar file.  The standalone
executable jar file is used for releases.


## Dependencies

This project graciously makes use of the jfreechart-1.0.17 and
jcommon-1.0.21 libraries.  For more information, please see the [JFree
website](http://www.jfree.org/jfreechart/).
