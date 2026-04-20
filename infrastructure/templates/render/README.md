# Render Templates

Este directorio contiene las plantillas de Terraform
utilizadas para desplegar la infraestructura necesaria
en Render.

## Requerimientos

- Una cuenta de [Render](https://render.com/)
- Una cuenta de [Cloudflare](https://www.cloudflare.com/)
  con acceso de escritura a objetos de un bucket
  de [R2](https://www.cloudflare.com/products/r2/)
  para el manejo del estado de Terraform
- [Terraform](https://www.terraform.io/) instalado en tu máquina local

## Instrucciones

Para desplegar la infraestructura en Render, sigue estos pasos:

1. Clona este repositorio en tu máquina local
2. Configura las siguientes variables de entorno:
   - `AWS_ACCESS_KEY_ID`: La clave de acceso de AWS para R2
   - `AWS_SECRET_ACCESS_KEY`: La clave secreta de AWS para R2
   - `RENDER_API_KEY`: La clave de API de Render
   - `RENDER_OWNER_ID`: El ID del propietario de los recursos de Render
   - `TF_VAR_postgres_database_name`: El nombre de la base de datos PostgreSQL a crear en Render
   - `TF_VAR_postgres_instance_name`: El nombre de la instancia PostgreSQL a crear en Render
   - `TF_VAR_postgres_user`: El nombre del usuario PostgreSQL a crear en Render
   - `TF_VAR_web_service_name`: El nombre del servicio web a crear en Render
3. Abre una terminal y navega hasta el directorio
   del repositorio/infrastructure/templates/render
4. Ejecuta el comando `terraform init` con la opción `-backend-config`:

    ```bash
    $ terraform init \
        -backend-config="bucket=<nombre_del_bucket>" \
        -backend-config="endpoints={\"s3\": \"https://<id_de_cuenta_de_cloudflare>.r2.cloudflarestorage.com\"}"
    ```
5. Ejecuta el comando `terraform plan` para ver el plan de despliegue
6. Si el plan es correcto, ejecuta el comando `terraform apply`
   para desplegar la infraestructura
7. Una vez desplegado, puedes acceder a la aplicación
   a través de la URL proporcionada por Render
