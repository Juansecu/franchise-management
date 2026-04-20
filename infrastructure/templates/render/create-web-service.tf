resource "render_web_service" "web" {
  name = var.web_service_name
  plan = "free"
  region = "ohio"

  runtime_source = {
    image = {
      image_url = "ghcr.io/juansecu/franchise-management"
      tag = "latest"
    }
  }

  env_vars = {
    "POSTGRES_DATABASE_NAME" = { value = var.postgres_database_name },
    "spring.datasource.url" = { value = "jdbc:${render_postgres.postgres_instance.connection_info.internal_connection_string}?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true" },
    "POSTGRES_PASSWORD" = { value = render_postgres.postgres_instance.connection_info.password },
    "POSTGRES_USER" = { value = var.postgres_user }
  }

  notification_override = {
    notifications_to_send = "failure"
    preview_notifications_enabled = "false"
  }

  log_stream_override = {
    setting = "drop"
  }
}
