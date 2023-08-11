# JavaNoiseGen

JavaNoiseGen is a WIP project aimed at generating noise. It provides a core module, `noise.core`, that can generate noise based on specified frequencies. There is also a planned UI to provide a graphical interface for users, but it's still in the works.

This project has been developed with assistance from the GPT-4 model by OpenAI.

## Prerequisites

- Java JDK 20 (or the version you've specified in your `pom.xml`)
- Maven

## Getting Started

### Cloning the Repository

To get a local copy of the JavaNoiseGen project, run:

    git clone https://github.com/aicheung/java-noise-gen.git
    cd java-noise-gen

### Compiling the Project

Navigate to the root directory of the project and execute:

    mvn compile

### Packaging the Application

To package the `noise.core` module into a runnable JAR, execute:

    mvn package


This will produce a `noise.core-1.0.jar` file inside the `noise.core/target` directory.

### Running the Application

After packaging, you can run the application using:

    mvn exec:java

## Note

This is a work-in-progress and might have features or functionalities that are not yet complete or polished. Feedback and contributions are welcome!

## Acknowledgments

- OpenAI's GPT-4 for assistance in coding and debugging.
