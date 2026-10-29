# Configuración del Prototipo en Java (Spring Boot)

Para cumplir con el requisito de **Spec-driven development** del TPE, el microservicio "Core de Viajes" no debe programar los controladores de la API manualmente. En su lugar, generaremos las interfaces a partir del contrato `openapi-viajes-v1.yaml`.

## 1. Inicializar el Proyecto

Genera un proyecto Spring Boot (usando Spring Initializr) con las siguientes dependencias:

* Spring Web

* Spring Data JPA

* PostgreSQL Driver

* Spring for RabbitMQ (opcional, para enviar/recibir eventos)

* Validation (Para validar los inputs del contrato)

## 2. Configurar el Generador de OpenAPI (Maven)

Coloca el archivo `openapi-viajes-v1.yaml` en la carpeta `src/main/resources/`.
Luego, agrega el siguiente plugin en tu archivo `pom.xml` dentro de la sección `<plugins>`:

```
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.1.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>\${project.basedir}/src/main/resources/openapi-viajes-v1.yaml</inputSpec>
                <generatorName>spring</generatorName>
                <apiPackage>com.trotamundos.viajes.api</apiPackage>
                <modelPackage>com.trotamundos.viajes.model</modelPackage>
                <supportingFilesToGenerate>ApiUtil.java</supportingFilesToGenerate>
                <configOptions>
                    <!-- ¡Crucial! Solo genera interfaces, tú escribes la lógica -->
                    <interfaceOnly>true</interfaceOnly> 
                    <useSpringBoot3>true</useSpringBoot3>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>

```

## 3. Flujo de Trabajo (Spec-Driven)

1. **Compilar:** Ejecuta `mvn clean compile`. El plugin leerá tu YAML y creará una interfaz llamada `ViajesApi` y los DTOs (ej. `ViajeInput`) en la carpeta `target/generated-sources`.

2. **Implementar:** Crea una clase en tu proyecto que implemente la interfaz autogenerada:

```
@RestController
public class ViajesController implements ViajesApi {
    
    // Inyectas tu servicio de lógica de negocio
    @Autowired
    private ViajesService viajesService;

    @Override
    public ResponseEntity<List<Viaje>> getViajes() {
        // Tu lógica aquí
        return ResponseEntity.ok(viajesService.obtenerTodos());
    }

    @Override
    public ResponseEntity<Void> addViaje(ViajeInput viajeInput) {
        // Tu lógica aquí
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

```

De esta manera, el profesor que evalúe el TPE podrá verificar que el código Java es **trazable y consistente** con la arquitectura documentada.