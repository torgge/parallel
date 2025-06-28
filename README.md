# Parallel List Comparison in Kotlin

This project is a Spring Boot application that demonstrates a high-performance method for finding the difference between two lists of objects in Kotlin, using coroutines for parallel processing.

## Description

The main goal of this project is to showcase an efficient way to compare large lists. It includes a `ParallelListComparator` class that is optimized for performance, especially with large datasets. The application starts, generates two lists of `ProductDto` objects, and then compares them using both a standard and a high-performance method, logging the time taken for each.

## Features

- **Standard List Comparison:** A straightforward implementation of list comparison.
- **High-Performance List Comparison:** A highly optimized version using Kotlin coroutines to parallelize the comparison process.
- **Configurable List Sizes:** The sizes of the lists to be compared can be configured in the `application.yaml` file.
- **Startup Runner:** The comparison logic is executed on application startup.

## Technologies Used

- [Kotlin](https://kotlinlang.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/parallel-list-comparison.git
    ```
2.  **Build the project:**
    ```bash
    ./gradlew build
    ```
3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```

## Configuration

The sizes of the two lists can be configured in the `src/main/resources/application.yaml` file:

```yaml
app:
  config:
    list-one-size: 100000
    list-two-size: 90000
```
