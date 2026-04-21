# Franchising Management System

## Requerimientos

- **[Java](https://www.oracle.com/java/technologies/downloads/#java21) -** Version 21
- **[PostgreSQL](https://www.postgresql.org/download/) -** Version 18 (Para el almacenamiento de datos)
- **[Terraform](https://www.terraform.io/downloads) -** Para desplegar la infraestructura en la nube
- **Variables de entorno:**

    | Variable                 | Tipo   | Descripción                                 | Requerido | Por Defecto | Ejemplo                                       |
    |--------------------------|--------|---------------------------------------------|-----------|-------------|-----------------------------------------------|
    | `PORT`                   | Number | Puerto en el que se ejecutará la aplicación | No        | `8080`      | `<Puerto de la Aplicación>`                   |
    | `POSTGRES_DATABASE_NAME` | String | Nombre de la base de datos PostgreSQL       | Sí        | N/A         | `<Nombre de la Base de Datos PostgreSQL>`     |
    | `POSTGRES_HOST`          | String | Host de la base de datos PostgreSQL         | Sí        | N/A         | `<Host de la Base de Datos PostgreSQL>`       |
    | `POSTGRES_PASSWORD`      | String | Contraseña de la base de datos PostgreSQL   | Sí        | N/A         | `<Contraseña de la Base de Datos PostgreSQL>` |
    | `POSTGRES_PORT`          | Number | Puerto de la base de datos PostgreSQL       | Sí        | N/A         | `<Puerto de la Base de Datos PostgreSQL>`     |
    | `POSTGRES_USER`          | String | Usuario de la base de datos PostgreSQL      | Sí        | N/A         | `<Usuario de la Base de Datos PostgreSQL>`    |

## Estrucura del Proyecto

La estructura de archivos de este proyecto se basa
en la [Arquitectura Orientada al Dominio (DDD)](https://learn.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/ddd-oriented-microservice).

La estructura es la siguiente:

```bash
. # Carpeta raíz del proyecto
├── .github/ # Configuración de GitHub
│   └── workflows/ # GitHub Actions workflows
├── mvnw # Ejecutable de Maven
├── infrastructure/ # Infrastructure de la aplicación
│   ├── templates/ # Plantillas de Infrastructure-as-Code (IaC) para desplegar la infraestructura necesaria en la nube
│   │    ├── render/ # Plantillas de Infrastructure-as-Code (IaC) para desplegar la infraestructura necesaria en Render
│   │    │   ├── ... # Configuración de Render
│   │    │   └── README.md # Documentación de las plantillas de infraestructura de Render
│   │    └── README.md # Documentación de las plantillas de infraestructura
│   └── README.md # Documentación de la infraestructura
├── requests/ # Solicitudes HTTP de la aplicación
│   ├── branches # Solicitudes HTTP relacionadas a las sucursales
│   │   └── create-branch.http # Solicitud HTTP de creación de Branch
│   ├── franchises/ # Solicitudes HTTP relacionadas a las franquicias
│   │   └── create-franchise.http # Solicitud HTTP de creación de Franchise
│   └── products/ # Solicitudes HTTP relacionadas a los productos
│       ├── add-product.http # Solicitud HTTP de creación de producto
│       ├── remove-product.http # Solicitud HTTP de eliminación de producto
│       └── update-product-stock.http # Solicitud HTTP de actualización de stock de producto
├── src/ # Código fuente
│   ├── main/ # Código fuente, configuración y tests de la aplicación
│   │   ├── java/ # Código fuente en Java
│   │   │   └── com/juansecu/franchisemanagement/ # Código fuente de la aplicación
│   │   │       ├── application/ # Módulos de la capa de aplicación
│   │   │       │   └── usecases/ # Casos de uso de la aplicación (servicios)
│   │   │       ├── domain/ # Dominio de la aplicación
│   │   │       │   ├── models/ # Modelos de la aplicación
│   │   │       │   │   └── Franchise.java # Modelo de Franchise
│   │   │       │   └── repositories/ # Repositorios de la aplicación
│   │   │       │       └── IFranchiseRepository.java # Repositorio de Franchise
│   │   │       ├── infrastructure/ # Módulos de la capa de infraestructura
│   │   │       │    ├── delivery/ # Módulos de la capa de presentación
│   │   │       │    │   ├── controllers/ # Controladores de la capa de presentación
│   │   │       │    │   │   └── FranchiseController.java # Controlador de Franchise
│   │   │       │    │   └── dtos/ # DTOs de la capa de presentación
│   │   │       │    │       ├── requests/ # DTOs de solicitud de la capa de presentación
│   │   │       │    │       └── responses/ # DTOs de respuesta de la capa de presentación
│   │   │       │    └── persistence/ # Módulos de la capa de persistencia
│   │   │       │        ├── adapters/ # Adaptadores de la capa de persistencia
│   │   │       │        │   └── FranchiseRepositoryAdapter.java # Adaptador de Franchise
│   │   │       │        ├── entities/ # Entidades de la capa de persistencia
│   │   │       │        │   └── FranchiseEntity.java # Entidad de Franchise
│   │   │       │        └── repositories/ # Repositorios de la capa de persistencia
│   │   │       │            └── JpaFranchiseRepository.java # Repositorio de Franchise
│   │   │       └── FranchisemanagementApplication.java # Clase principal de la aplicación
│   │   └── resources/ # Recursos de la aplicación
│   │       ├── db/ # Scripts de base de datos
│   │       │   └── migrations/ # Migraciones de base de datos
│   │       └── application.properties # Configuración de la aplicación
│   └── tests/ # Pruebas unitarias y de integración
│       ├── java/ # Código fuente de las pruebas
│       │   └── com/juansecu/franchisemanagement/ # Código fuente de las pruebas
│       │       ├── application/ # Pruebas de la capa de aplicación
│       │       │   └── usecases/ # Pruebas de los casos de uso de la aplicación
│       │       │       └── CreateFranchiseUseCaseTest.java # Pruebas del caso de uso de creación de Franchise
│       │       ├── infrastructure
│       │       │   └── delivery/ # Pruebas de la capa de presentación
│       │       │       └── controllers/ # Pruebas de los controladores
│       │       │           └── FranchiseControllerTest.java # Pruebas del controlador de Franchise
│       │       └── FranchisemanagementApplicationTest.java # Pruebas de la aplicación principal
│       └── resources/ # Recursos de las pruebas
│           └── application-test.properties # Configuración de las pruebas
├── target/ # Archivos compilados de la aplicación
├── .editorconfig # Configuración de editores de código
├── .gitattributes # Reglas de atributos de archivos Git
├── .gitignore # Reglas de exclusión de archivos para Git
├── AGENTS.md # Instrucciones de configuración de agentes de IA
├── compose.yml # Configuración de Docker Compose
├── Dockerfile # Instrucciones de construcción de contenedores Docker
├── mvnw # Ejecutable de Maven en Linux
├── mvnw.cmd # Ejecutable de Maven en Windows
├── pom.xml # Configuración de Maven
├── qodana.yaml # Configuración de Qodana
└── README.md # Documentación del proyecto
```

Cada carpeta que representa una capa
puede contener las siguientes carpetas o archivos:

```bash
. # Carpeta raíz de la capa
└── exceptions/ # Excepciones personalizadas relacionadas a la capa
```

## Ejecutar Aplicación

### Usando Docker (Recomendado)

Para poder ejecutar la aplicación utilizando [Docker](https://www.docker.com/),
debes tener instalado Docker en tu sistema.

Para correr la aplicación utilizando Docker,
debes enlazar el puerto en el que va a correr la aplicación
a tu sistema operativo.

#### Usando Docker CLI

- **Windows**

    ```shell
    > docker run -dp 8080:8080 \
        --env-file <ruta\al\archivo\de\variables\de\entorno> \
        --name <container-name> \
        ghcr.io/juansecu/franchise-management:latest
    ```
- **MacOS/Linux**

    ```shell
    $ docker run -dp 8080:8080 \
            --env-file <ruta/al/archivo/de/variables/de/entorno> \
            --name <container-name> \
            ghcr.io/juansecu/franchise-management:latest
    ```

#### Usando Docker Compose

Para poder correr la aplicación utilizando Docker Compose,
puedes utilizar el archivo [`compose.yml`](./compose.yml).
Sin embargo, debes realizar los siguientes cambios:

1. Remover la propiedad `build`
   del servicio `franchising_management_system-http-server`
2. Reemplazar el valor de la propiedad `image`
   del servicio `franchising_management_system-http-server`
   por `ghcr.io/juansecu/franchise-management:latest`

Una vez que hayas realizado los cambios anteriores,
puedes ejecutar el siguiente comando para levantar la aplicación:

```shell
$ docker compose up -d
```

### Usando Java

Para poder ejecutar la aplicación utilizando Java,
debes tener instalado Java en tu sistema.

Para correr la aplicación utilizando Java, debes:

1. Clonar el repositorio
2. Navegar al directorio del proyecto
3. Ejecutar el siguiente comandos:

    ```shell
    # Windows
    > .\mvnw package -DskipTests # Para compilar la aplicación
    > java -jar .\target\franchise-management-1.0.0.jar # Para ejecutar la aplicación

    # MacOS/Linux
    $ ./mvnw package -DskipTests # Para compilar la aplicación
    $ java -jar ./target/franchise-management-1.0.0.jar # Para ejecutar la aplicación
    ```

## Infraestructura

Para poder desplegar la infraestructura necesaria en la nube,
puedes utilizar las plantillas de Terraform ubicadas en el directorio
[`infrastructure/templates`](./infrastructure/templates/README.md).
