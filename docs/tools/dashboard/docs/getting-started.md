# GraalVM Dashboard

GraalVM Dashboard is a web-based dashboard for visualizing arbitrary aspects of
compilation in GraalVM, and in particular, in the [GraalVM Native Image](https://www.graalvm.org/docs/reference-manual/native-image). It can be
found at the following URL: [https://www.graalvm.org/docs/tools/dashboard](https://www.graalvm.org/docs/tools/dashboard).

Some examples of visualizations in the GraalVM dashboard are:
- Code size histogram -- presents a visual summary of the sizes of the different packages,
  classes and methods that were included into a native image.
- Heap size histogram -- presents a visual summary of the sizes of the objects
  of the different classes, which were included into the heap of a native image.
- Points-to exploration -- this component is used to, starting from a particular method
  that is included in the native image, illustrate why this particular method of the program
  was included in the native image.

Here you can find information about the basic usage instructions.

## Generating Report Files
To generate report files for the GraalVM Dashboard, you need to pass certain
flags when building a native image.

Example usage for compiling a `org.graal.Hello` project:
```
mx native-image -H:DashboardDump=dashboard.dump -H:+DashboardAll org.graal.Hello
```

## Opening Report Files In The Dashboard
To open the report file in the GraalVM Dashboard, click on the "Add data" button
on the left, which will open a dialog box. Here you can select the dumped file,
obtained during a native-image build.
