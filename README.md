# Jabberpoint

A Java-based presentation application for creating and viewing slideshows.

## Prerequisites

- **Java 21** or later
- **Apache Maven 3.6+**

## Building the Project

### Using Maven

1. Navigate to the project directory:
   ```bash
   cd jabberpoint
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

   This command will:
   - Clean any previous build artifacts
   - Compile the Java source code
   - Package the application into a JAR file

3. The compiled JAR file will be located at:
   ```
   target/jabberpoint-1.0.0.jar
   ```

### Building Without Running Tests

If you want to skip tests during the build:
```bash
mvn clean package -DskipTests
```

## Running the Application

### Using Maven

To run the application directly from Maven:
```bash
mvn exec:java -Dexec.mainClass="app.JabberPoint"
```

### Using the JAR File

After building the project, you can run the compiled JAR file:
```bash
java -jar target/jabberpoint-1.0.0.jar
```

## Running Tests

### Execute All Tests

To run all 168 unit tests:

```bash
mvn test
```

This command will:
- Compile the test code
- Execute all unit tests in `src/test/java/`
- Display test results and any failures
- Generate coverage reports

## Project Structure

```
jabberpoint/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── app/                       # Application entry point
│   │   │   ├── controller/                # Command controllers
│   │   │   ├── model/                     # Core data models
│   │   │   ├── persistence/               # File I/O and XML handling
│   │   │   └── view/                      # UI components
│   │   └── resources/
│   │       ├── diagrams/                  # UML diagrams (Astah format)
│   │       │   ├── Jabberpoint_old.asta
│   │       │   └── Jabberpoint.asta
│   │       └── README.md                  # Resources documentation
│   └── test/
│       ├── java/
│       │   ├── app/                       # Application tests
│       │   ├── controller/                # Controller tests
│       │   ├── model/                     # Model tests
│       │   ├── persistence/               # Persistence tests
│       │   └── view/                      # View tests
│       └── resources/                     # Test resources
├── docs/                                  # Additional documentation
├── pom.xml                                # Maven configuration
└── test.xml                               # Test presentation file
```

### Key Components

- **app.JabberPoint**: Main application entry point
- **controller/**: Command pattern implementation for user actions
- **model/**: Presentation, Slide, and SlideItem classes
- **persistence/**: XML file handling and demo presentations
- **view/**: Swing UI components for slide viewing

## Usage

Launch the application and use the GUI to:
- Open existing presentations
- Create and edit slides
- Navigate through presentations
- Save presentations to XML format

## Configuration

The application uses Java 21 as the target Java version. To modify the Java version, edit the `pom.xml` file:

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>
```

## Troubleshooting

### Maven not found
Ensure Maven is installed and added to your system PATH. Verify with:
```bash
mvn --version
```

### Java version mismatch
Verify you have Java 21 installed:
```bash
java --version
```

### Build failures
Clean the project and rebuild:
```bash
mvn clean package
```

## Design Documentation

### UML Diagrams

The system architecture and design are documented using UML 2.5 diagrams, located at:

```
src/main/resources/diagrams/Jabberpoint.asta
src/main/resources/diagrams/Jabberpoint_old.asta
```

These Astah files contain:
- Class diagrams showing the layered architecture
- Sequence diagrams for key workflows
- Design pattern implementations

**To view the diagrams:**
- Download and install [Astah Professional](https://astah.net/) or Astah Community
- Open `src/main/resources/diagrams/Jabberpoint.asta` for the current redesign
- Open `src/main/resources/diagrams/Jabberpoint_old.asta` for the legacy design

### Design Patterns

The application implements the following design patterns:

| Pattern | Purpose | Location |
|---------|---------|----------|
| **Command** | Encapsulates user actions as objects | `controller/` package |
| **Observer** | Decouples model from view updates | `model/Observer`, `Presentation` |
| **Factory** | Creates slide items based on type | `persistence/SlideItemFactory` |
| **Strategy** | Interchangeable file loading strategies | `persistence/Accesor` interface |

## Additional Resources

- See `docs/CODEBASE_ADVICE.md` for codebase guidelines
- See `jabberpoint.dtd` for the XML file format definition
- See `test.xml` for an example presentation file
- See `src/main/resources/diagrams/Jabberpoint.asta` for UML diagrams
- See `src/main/resources/diagrams/Jabberpoint_old.asta` for legacy UML diagrams
