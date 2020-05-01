
## Getting Started Guide

The GraalVM dashboard is a tool that visualizes and helps analyze GraalVM compilations.

### Generating Report Files
The GraalVM dashboard visualizes data taken from report files generated during SVM compilation.
To generate such report files, you need to pass certain flags when running an SVM compilation.
Currently, the following flags are available:
- **-H:+PrintMethodHistogram:** Export a method histogram, which contains the machine code size
    of every method in the compiled image. The output of this command is printed to `stdout`.
    Redirect it into a file, to then use as an `SVM Method Histogram` in the dashboard.
- **-H:+ExportPointstoGraph:** Export the compilation's points-to analysis information into a
    file, located in the `reports` directory in `substratevm-enterprise`. In the dashboard,
    this can be used as an `SVM Pointsto Analysis` file, to explore the reachability of a given
    method and find out why it was included in the image.

### Opening Report Files In The Dashboard
To open a report file in the dashboard, click the "+"-icon on the left, which will open a dialog
box. Here, you can select the file you want to open and specify its type depending on which flag
you used during compilation. The file type `SVM Method Histogram` automatically creates a new
so-called "data source" and can't be added to an existing one in the corresponding dropdown
menu. Files of type `SVM Pointsto Analysis`, however, can be added to data sources created from
a file of type `SVM Method Histogram`, to enable exploring a method's reachability information.