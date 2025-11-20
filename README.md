# Proyecto de Microservicios - BankApplication

Este proyecto implementa una arquitectura de microservicios para una aplicación bancaria, gestionando cuentas y clientes con comunicación asíncrona y patrones de desacoplamiento.

## 📂 Ubicación de la Colección de Postman

**Nota Importante:**
El archivo de la colección de Postman llamado `collection_bank_postman.json` se ha colocado en el **directorio raíz** de este repositorio (fuera del ZIP del código fuente o subcarpetas).

* **Ubicación:** `./collection_bank_postman.json`
* **Motivo:** Esta ubicación estratégica asegura que el archivo sea fácilmente accesible para su evaluación y evita problemas de rutas durante la revisión. Puede importar este archivo directamente en Postman para probar todos los endpoints, los cuales se encuentran funcionando correctamente.

---

## 🏗️ Arquitectura y Estrategia de Comunicación

El sistema está dividido en dos microservicios principales que operan de manera conjunta:

1.  **Client Microservice:** Encargado de la gestión de la información personal de los clientes.
2.  **Account Microservice:** Gestiona las Cuentas (`accounts`) y Movimientos (`transactions`).

### Comunicación Asíncrona y Patrón Snapshot

Para mantener el desacoplamiento entre servicios y garantizar la integridad de los datos sin realizar llamadas síncronas bloqueantes, se implementó una estrategia basada en **Eventos** usando **RabbitMQ** y una tabla de **Snapshot**.

* **Mecanismo:** Cuando se registra un nuevo cliente en el **Client Microservice**, se publica un evento de dominio. El **Account Microservice** escucha este evento y persiste la información necesaria en una tabla local llamada `client_snapshot`.
* **Estructura del Snapshot:** La tabla `client_snapshot` (ubicada dentro del microservicio de Accounts) almacena únicamente los datos esenciales para la referencia:
    * `id`: Identificador original del cliente.
    * `nombre`: Nombre del cliente para visualización en reportes.
* **Escalabilidad:** Actualmente, la lógica maneja el evento de creación (`create`). Sin embargo, esta arquitectura está diseñada para ser fácilmente extensible a eventos de actualización (`update`) y eliminación (`delete`), manteniendo la consistencia eventual entre ambos sistemas.

---

## ⚙️ Infraestructura y Configuración

### Configuración de RabbitMQ (Requerido)

El sistema requiere **RabbitMQ** para el manejo de la mensajería asíncrona descrita anteriormente.

**Pre-requisitos:**
Debe tener RabbitMQ instalado y ejecutándose en su entorno (Windows, Ubuntu o Docker).

#### ⚠️ Configuración Manual Crítica en caso de que las colas no se levanten automaticamente

Antes de ejecutar la aplicación, es necesario crear manualmente una cola específica para evitar errores de tipo `Queue Not Found`. Por favor, siga estos pasos:

1.  **Acceder a la Consola de Administración de RabbitMQ:**
    * Abra su navegador y vaya a: [http://localhost:15672](http://localhost:15672)
    * **Usuario:** `guest`
    * **Contraseña:** `guest` (o sus credenciales personalizadas).

2.  **Ir a la pestaña de Colas:**
    * Haga clic en la pestaña **"Queues"** en el menú superior.

3.  **Crear la Cola:**
    * Busque la sección **"Add a new queue"**.
    * Complete los campos exactamente así:
        * **Type:** `Classic`
        * **Name:** `client.created.queue` (Debe ser exacto).
        * **Durability:** `Durable`
            * *Nota: Es crucial seleccionar "Durable" para evitar errores de `PRECONDITION_FAILED`.*

4.  **Finalizar:**
    * Haga clic en el botón **"Add queue"**.

Una vez creada la cola, los servicios funcionarán correctamente y la replicación de datos (Snapshot) estará activa.

---

## 🚀 Ejecución del Proyecto

1.  Asegúrese de que RabbitMQ esté corriendo y la cola configurada.
2.  Construya el proyecto usando Maven:
    ```bash
    mvn clean install
    ```
3.  Ejecute los servicios.