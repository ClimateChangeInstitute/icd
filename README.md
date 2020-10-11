# icd

This is the original ice core dating software developed by Bashar
Abdul for the climate Change Institute, University of Maine. The latest
build was created by Mark Royer on October 8, 2020.

You can download the latest build (v1.1)
here:
[icd-1.1-20201011.jar](https://github.com/ClimateChangeInstitute/icd/releases/download/v1.1/icd-1.1-20201011.jar).

## Usage Example

You can find an example core file here: [991test1.csv](etc/991test1.csv).  Use this file as a reference for how the program expects the data to be structured.  The top few lines are listed below.

``` pure-data
tube,top,bottom,length,Na (ppb),NH4 (ppb),K (ppb),Mg (ppb),Ca (ppb),Cl (ppb),NO3 (ppb),SO4 (ppb)
1,0,0.02,0.02,40.24,40.24,14.86,3.72,12.88,54.87,32.94,44.39
1,0.02,0.04,0.02,10.74,10.74,1.9,1.29,2.45,23.87,30.07,22.99
1,0.04,0.06,0.02,85.17,85.17,19.44,6.49,7.94,113.01,41.89,45.93
1,0.06,0.08,0.02,72,72,2.31,9.44,3.87,123.51,64.66,73.48
1,0.08,0.1,0.02,79.44,79.44,2.88,10.17,3.74,131.56,60.36,80.81
```

Data is separated using commas.  The first line contains the column
names.  The rest of the rows contain comma separated data.  Missing
data and data values of -99 are replaced with zeroes when imported by
the program.


## Building

You can build the project using [Maven](http://maven.apache.org/).  From the root of the project directory structure typing

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
