# Despliegue de registro-citas en Railway

## 1) Verificar que compila localmente

```bash
./mvnw -DskipTests package
```

En este proyecto ya se validó `BUILD SUCCESS`.

## 2) Subir cambios a GitHub

Hay cambios locales adicionales en tu repo. Si quieres subir TODO lo actual:

```bash
git add .
git commit -m "Prepare app for Railway deployment"
git push origin main
```

Si quieres subir SOLO lo relacionado al deploy, agrega archivos de forma explícita.

## 3) Crear proyecto en Railway

1. Entra a Railway.
2. Click en **New Project**.
3. Selecciona **Deploy from GitHub Repo**.
4. Elige tu repo `ProyectoBackend`.

## 4) Agregar PostgreSQL en Railway

1. Dentro del proyecto, agrega un servicio de **PostgreSQL**.
2. Abre PostgreSQL y copia sus variables/conexión.

## 5) Variables de entorno en el servicio backend

En tu servicio Java (no en la DB), agrega:

- `SPRING_DATASOURCE_URL` = `jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>`
- `SPRING_DATASOURCE_USERNAME` = `<DB_USER>`
- `SPRING_DATASOURCE_PASSWORD` = `<DB_PASSWORD>`
- `JWT_SECRET` = `<TU_CLAVE_LARGA_Y_SEGURA>`
- `APP_CORS_ALLOWED_ORIGINS` = `https://tu-frontend.com`

Opcionales:

- `SPRING_JPA_HIBERNATE_DDL_AUTO` = `update`
- `SPRING_JPA_SHOW_SQL` = `false`
- `JWT_EXPIRATION_MS` = `3600000`

Nota: `PORT` lo inyecta Railway automáticamente y ya está soportado en `application.properties`.

## 6) Build/Start en Railway

Railway detecta Maven automáticamente.

Si te pide comandos manuales:

- Build Command: `./mvnw -DskipTests package`
- Start Command: `java -jar target/registro-citas-0.0.1-SNAPSHOT.jar`

## 7) Probar API desplegada

Con la URL pública de Railway (`https://...up.railway.app`):

- Login: `POST https://<tu-servicio>/api/auth/login`
- Registro: `POST https://<tu-servicio>/api/auth/register`

## 8) CORS para frontend

Si tu frontend está en más de un dominio, usa coma separada:

```text
APP_CORS_ALLOWED_ORIGINS=https://app1.com,https://app2.com
```

## 9) Checklist rápido

- Repo actualizado en GitHub.
- Servicio backend creado desde GitHub en Railway.
- PostgreSQL agregado.
- Variables de entorno configuradas.
- Deploy en estado `SUCCESS`.
- Endpoints `/api/auth/*` respondiendo.
